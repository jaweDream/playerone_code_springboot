package com.jawe.system.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jawe.conf.redis.RedisAlias;
import com.jawe.handler.BusinessException;
import com.jawe.response.Result;
import com.jawe.response.ResultCode;
import com.jawe.system.entity.AliOssEntity;
import com.jawe.system.entity.FlashSale;
import com.jawe.system.entity.Goods;
import com.jawe.system.service.FlashSaleService;
import com.jawe.system.service.GoodsService;
import com.jawe.system.service.InsetService;
import com.jawe.vo.GoodsVo;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author jawe
 * @since 2022-05-10
 */
@RestController
@RequestMapping("/api/flash_sale")
public class FlashSaleController {

    @Autowired
    FlashSaleService flashSaleService;
    @Autowired
    GoodsService goodsService;
    @Resource
    private AliOssEntity aliOssEntity;
    @Autowired
    private InsetService insetService;
    @Resource
    private RedisTemplate redisTemplate;

    @PostMapping("flashGoodsList")
    @ApiOperation(value = "获取活动商品列表", notes = "活动商品列表")
    public Result flashGoodsList(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "7") Integer pageSize,
            @RequestBody(required = false) GoodsVo goodsVo
    ) {
        List<FlashSale> list = flashSaleService.list();
        //判断活动状态：未开始、进行中、结束
        for (FlashSale flashSale :
                list) {
            if (flashSale.getStartTime().isAfter(LocalDateTime.now())) {
                //未开始
                flashSale.setState(0);
            } else if (flashSale.getEndTime().isAfter(LocalDateTime.now())) {
                //进行中
                flashSale.setState(1);
            } else {
                //已结束
                flashSale.setState(2);
            }
            flashSale.setGoods(goodsService.getById(flashSale.getGoodsId()));
            flashSale.getGoods().setCoverUrl(flashSale.getGoods().getCoverUrl());
            flashSale.setPrice(flashSale.getGoods().getPrice());
            flashSale.setGoodsName(flashSale.getGoods().getGoodsName());
            flashSale.setCoverUrl(flashSale.getGoods().getCoverUrl());
            flashSale.setStartTime(flashSale.getStartTime().minusMonths(1));
            flashSale.setEndTime(flashSale.getEndTime().minusMonths(1));
            flashSale.setCreateTime(flashSale.getCreateTime().minusMonths(1));
        }
        return Result.ok().data("records", list);
    }

    @GetMapping("{id}")
    @ApiOperation(value = "根据id获取秒杀商品信息", notes = "根据id获取秒杀商品信息")
    public Result getById(@PathVariable("id") Integer id) {
        FlashSale flashSale = flashSaleService.getById(id);
        System.out.println(flashSale.getStartTime().toString().replace("T", " "));
        flashSale.setStartTimeStr(flashSale.getStartTime().toString().replace("T", " "));
        flashSale.setTime((int) Duration.between(flashSale.getStartTime(), flashSale.getEndTime()).getSeconds() / 60);
        return Result.ok().data("records", flashSale);
    }


    @PostMapping("add")
    @ApiOperation(value = "添加秒杀商品", notes = "添加秒杀商品")
    public Result addUser(@RequestBody FlashSale flashSale) {

        flashSale.setSale(0);
        flashSale.setStartTime(toLocalDateTime(flashSale.getStartTimeStr(), "yyyy-MM-dd HH:mm:ss"));
        flashSale.setCreateTime(LocalDateTime.now());
        flashSale.setEndTime(flashSale.getStartTime().plusMinutes(flashSale.getTime()));
        System.out.println(flashSale);

        /*判断时间是否重叠*/
        /*判断该商品是否已有活动*/
        List<FlashSale> flashSaleList = flashSaleService.list(new QueryWrapper<FlashSale>().gt("end_time", flashSale.getStartTime()));
        for (FlashSale temp :
                flashSaleList) {
            LocalDateTime maxStartTime = temp.getStartTime().compareTo(flashSale.getStartTime()) > 0 ? temp.getStartTime() : flashSale.getStartTime();
            LocalDateTime minEndTime = temp.getEndTime().compareTo(flashSale.getEndTime()) > 0 ? temp.getEndTime() : flashSale.getEndTime();
            if (maxStartTime.compareTo(minEndTime) <= 0) {
                throw new BusinessException(1001,"活动时间存在重叠");
            }
            if (temp.getGoodsId().equals(flashSale.getGoodsId())){
                throw new BusinessException(1002,"该商品有未结束活动");
            }
        }

        if (flashSaleService.save(flashSale)) {
            //添加到Redis中
            long countdown = Duration.between(LocalDateTime.now(),flashSale.getEndTime()).toMillis();
            redisTemplate.opsForValue().set(RedisAlias.FLASH_SALE +flashSale.getGoodsId(),flashSale.getStock(),countdown, TimeUnit.MILLISECONDS);
            return Result.ok().data("flashSale", flashSale);
        } else {
            throw new BusinessException(ResultCode.GOODS_ALREADY_EXIST.getCode(), ResultCode.GOODS_ALREADY_EXIST.getMessage());
        }
    }


    @PostMapping("update")
    @ApiOperation(value = "修改秒杀商品", notes = "修改秒杀商品")
    public Result updateUser(@RequestBody FlashSale flashSale) {

        flashSale.setStartTime(toLocalDateTime(flashSale.getStartTimeStr(), "yyyy-MM-dd HH:mm:ss"));
        flashSale.setEndTime(flashSale.getStartTime().plusMinutes(flashSale.getTime()));
        System.out.println(flashSale.getTime());
        System.out.println(flashSale.getEndTime());
        if (flashSaleService.updateById(flashSale)) {
            return Result.ok().message("修改成功");
        } else {
            throw new BusinessException(ResultCode.GOODS_ALREADY_EXIST.getCode(), ResultCode.GOODS_ALREADY_EXIST.getMessage());
        }
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "删除秒杀商品", notes = "删除秒杀商品")
    public Result delFlash(@PathVariable Integer id) {
        FlashSale flashSale = flashSaleService.getById(id);
        if (flashSale.getEndTime().isBefore(LocalDateTime.now())) {
            throw new BusinessException(ResultCode.FLASH_ALREADY_OVER.getCode(), ResultCode.FLASH_ALREADY_OVER.getMessage());
        }
        if (flashSaleService.removeById(id)) {
            redisTemplate.delete(RedisAlias.FLASH_SALE+flashSale.getGoodsId());
            return Result.ok().message("删除");
        } else {
            return Result.error().message("删除失败");
        }
    }

    public static LocalDateTime toLocalDateTime(String dateTime, String format) {
        if (StringUtils.isEmpty(dateTime)) {
            return null;
        }
        if (StringUtils.isEmpty(format)) {
            format = "yyyy-MM-dd HH:mm:ss";
        }

        DateTimeFormatter df = DateTimeFormatter.ofPattern(format);
        LocalDateTime ldt = LocalDateTime.parse(dateTime, df);
        return ldt;
    }
}

