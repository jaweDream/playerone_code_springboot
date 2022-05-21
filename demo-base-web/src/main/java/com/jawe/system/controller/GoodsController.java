package com.jawe.system.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jawe.handler.BusinessException;
import com.jawe.response.Result;
import com.jawe.response.ResultCode;
import com.jawe.system.entity.Goods;
import com.jawe.system.service.GoodsCategoryService;
import com.jawe.system.service.GoodsService;
import com.jawe.vo.GoodsVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jawe
 * @since 2021-10-24
 */
@RestController
@RequestMapping("/api/goods")
@Api(value = "系统商品模块", tags = "商品信息")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private GoodsCategoryService goodsCategoryService;

    @PostMapping("/findGoodsList")
    @ApiOperation(value = "分页商品信息 by GoodsVo", notes = "分页查询商品")
    public Result findUserList(@RequestParam(defaultValue = "1") Integer current,
                               @RequestParam(defaultValue = "7") Integer pageSize,
                               @RequestBody(required = false) GoodsVo goodsVo

    ) {
        Page<Goods> page = new Page<>(current, pageSize);

        QueryWrapper<Goods> queryWrapper = getMyQueryWrapper(goodsVo);

        IPage<Goods> goodsIPage = goodsService.findGoodsPage(page, queryWrapper);
        long total = goodsIPage.getTotal();
        List<Goods> records = goodsIPage.getRecords();

        return Result.ok().data("total", total).data("records", records);

    }


    @ApiOperation(value = "修改商品是否上架", notes = "修改商品是否上架/api/goods/{goodsId}")
    @RequestMapping(value = "/{goodsId}", method = RequestMethod.PUT)
    public Result isOnGoods(@PathVariable("goodsId") Integer goodsId) {
        int res = this.goodsService.isOnGoods(goodsId);
        System.out.println(res);
        if (res != 0) {
            return Result.ok();
        } else {
            return Result.error();
        }
    }

    @PostMapping("")
    @ApiOperation(value = "添加商品", notes = "添加商品/api/goods")
    public Result addUser(@RequestBody Goods goods) {
        if (goodsService.save(goods)) {
            return Result.ok().data("goods", goods);
        } else {
            throw new BusinessException(ResultCode.GOODS_ALREADY_EXIST.getCode(), ResultCode.GOODS_ALREADY_EXIST.getMessage());
        }
    }


    @RequestMapping(value = "", method = RequestMethod.PUT)
    @ApiOperation(value = "修改商品", notes = "修改商品/api/goods")
    public Result updateUser(@RequestBody Goods goods) {
        if (goodsService.updateById(goods)) {
            return Result.ok().message("修改成功");
        } else {
            throw new BusinessException(ResultCode.GOODS_ALREADY_EXIST.getCode(), ResultCode.GOODS_ALREADY_EXIST.getMessage());
        }
    }

    @ApiOperation(value = "获取商品信息 by goodsId", notes = "获取商品信息/api/goods/{goodsId}")
    @RequestMapping(value = "/{goodsId}", method = RequestMethod.GET)
    public Result getGoodsInfo(@PathVariable("goodsId") Integer goodsId,
                               @RequestParam(value = "include",required = false) String include ) {

        Goods goods = goodsService.getById(goodsId);
        goods.setCategory(goodsCategoryService.getById(goods.getCategoryId()));

        if (goods != null) {
            return Result.ok().data("goods", goods);
        } else {
            throw new BusinessException(ResultCode.USER_ACCOUNT_NOT_EXIST.getCode(), ResultCode.USER_ACCOUNT_NOT_EXIST.getMessage());
        }
    }





    private QueryWrapper<Goods> getMyQueryWrapper(GoodsVo goodsVo) {
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        if (goodsVo != null) {
//            if (!StringUtils.isEmpty(goodsVo.getUsername())) {
//                queryWrapper.like("username", goodsVo.getUsername());
//            }
//            if (!StringUtils.isEmpty(goodsVo.getGender())) {
//                queryWrapper.eq("gender", goodsVo.getGender());
//            }
//            if (!StringUtils.isEmpty(goodsVo.getEmail())) {
//                queryWrapper.like("email", goodsVo.getEmail());
//            }
//            if (!StringUtils.isEmpty(goodsVo.getCreateTime())) {
//                queryWrapper.orderBy(true, goodsVo.getCreateTime().equals("descend")?false:true,"create_time");
//            }
        }
        return queryWrapper;
    }
}

