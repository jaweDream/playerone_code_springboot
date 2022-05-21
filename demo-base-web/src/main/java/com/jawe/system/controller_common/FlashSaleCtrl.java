package com.jawe.system.controller_common;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jawe.response.Result;
import com.jawe.system.entity.AliOssEntity;
import com.jawe.system.entity.FlashSale;
import com.jawe.system.entity.Goods;
import com.jawe.system.entity.Inset;
import com.jawe.system.service.FlashSaleService;
import com.jawe.system.service.GoodsService;
import com.jawe.system.service.InsetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author jawe
 * @since 2022-05-10
 */
@RestController
@RequestMapping("/api/common/flash_sale")
@Api(value = "前台-活动商品-api", tags = "前台-活动商品")

public class FlashSaleCtrl {
    @Autowired
    FlashSaleService flashSaleService;
    @Autowired
    GoodsService goodsService;
    @Resource
    private AliOssEntity aliOssEntity;
    @Autowired
    private InsetService insetService;



    @GetMapping("")
    @ApiOperation(value = "获取活动商品列表", notes = "活动商品列表")
    public Result flashGoodsList(
    ) {
        //判断活动状态：未开始、进行中、结束
        QueryWrapper<FlashSale> wrapper = new QueryWrapper<>();
        //结束时间大于现在，说明未结束
        wrapper.gt("end_time", LocalDateTime.now()).orderByAsc("end_time");
        List<FlashSale> list = flashSaleService.list(wrapper);

        for (FlashSale flashSale :
                list) {
            flashSale.setState(flashSale.getStartTime().isBefore(LocalDateTime.now()) ? 1 : 0);
            flashSale.setGoods(goodsService.getById(flashSale.getGoodsId()));

            flashSale.setTime((int) (flashSale.getState() == 1
                    ?
                    Duration.between(LocalDateTime.now(),flashSale.getEndTime()).getSeconds()
                    :
                    Duration.between(LocalDateTime.now(),flashSale.getStartTime()).getSeconds())
            );
            flashSale.getGoods().setCoverUrl(aliOssEntity.getSrc()+flashSale.getGoods().getCoverUrl());
        }

        return Result.ok().data("records", list.size()>0?list.get(0):null);
    }


    @GetMapping("{id}")
    @ApiOperation(value = "获取活动商品信息", notes = "活动商品信息")
    public Result flashInfo(
            @PathVariable("id") Integer flashId
    ) {
        FlashSale flashSale = flashSaleService.getById(flashId);
        flashSale.setGoods(goodsService.getById(flashSale.getGoodsId()));
        flashSale.getGoods().setCoverUrl(aliOssEntity.getSrc()+flashSale.getGoods().getCoverUrl());
        QueryWrapper<Inset> insetQueryWrapper = new QueryWrapper<>();
        insetQueryWrapper.eq("goods_id",flashSale.getGoodsId());
        flashSale.getGoods().setInsets(insetService.list(insetQueryWrapper));

        flashSale.setState(flashSale.getStartTime().isBefore(LocalDateTime.now()) ? 1 : 0);
        flashSale.setTime((int) (flashSale.getState() == 1
                        ?
                        Duration.between(LocalDateTime.now(),flashSale.getEndTime()).getSeconds()
                        :
                       Duration.between(LocalDateTime.now(),flashSale.getStartTime()).getSeconds())
        );

        return Result.ok().data("records",flashSale);
    }




}

