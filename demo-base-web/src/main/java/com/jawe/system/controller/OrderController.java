package com.jawe.system.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jawe.conf.jwt.JwtTokenUtil;
import com.jawe.response.Result;
import com.jawe.system.entity.AliOssEntity;
import com.jawe.system.entity.Goods;
import com.jawe.system.entity.GoodsOrder;
import com.jawe.system.entity.GoodsOrderGoods;
import com.jawe.system.service.*;
import com.jawe.vo.GoodsVo;
import com.jawe.vo.OrderListVO;
import com.jawe.vo.OrderVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author jawe
 * @since 2021-12-11
 */
@RestController
@RequestMapping("/api/order")
@Api(value = "前台-订单-api", tags = "前台-订单")
public class OrderController {

    @Resource
    private OrderService orderService;
    @Resource
    private UserService userService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private GoodsOrderGoodsService goodsOrderGoodsService;
    @Autowired
    private CartService cartService;
    @Resource
    private AliOssEntity aliOssEntity;
    @Resource
    JwtTokenUtil jwtTokenUtil;

    @PostMapping("orderList")
    @ApiOperation(value = "获取订单列表", notes = "获取订单列表")
    public Result select(@RequestParam(defaultValue = "1") Integer current,
                         @RequestParam(defaultValue = "8") Integer pageSize,
                         @RequestBody(required = false) OrderVo orderVo
    ) {
        Page<GoodsOrder> page = new Page<>(current, pageSize);
        QueryWrapper<GoodsOrder> queryWrapper = getMyQueryWrapper(orderVo);

        IPage<GoodsOrder> goodsIPage = orderService.findOrderPage(page, queryWrapper);
        long total = goodsIPage.getTotal();
        List<GoodsOrder> records = goodsIPage.getRecords();
        for (GoodsOrder order :
                records) {
            order.setUserName(userService.getById(order.getUserId()).getUsername());
        }
        return Result.ok().data("total", total).data("records", records);
    }


    @PutMapping("deliver/{id}")
    @ApiOperation(value = "发货", notes = "发货")
    public Result deliver(@PathVariable("id") Long id) {
        orderService.deliver(id);
        return Result.ok();
    }

    @PutMapping("receive/{id}")
    @ApiOperation(value = "收货(退货)", notes = "收货(退货)")
    public Result receive(@PathVariable("id") Long id) {
//        //更新库存
//        GoodsOrder order = orderService.getById(id);
//        Goods goods = goodsService.getById(order.getUserId());
//        QueryWrapper<GoodsOrderGoods> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("order_id",id);
//        int count = goodsOrderGoodsService.count(queryWrapper);
//        UpdateWrapper<Goods> updateWrapper = new UpdateWrapper<>();
//        updateWrapper.eq("id",goods.getId()).set("stock",goods.getStock()+count);
//        goodsService.update(updateWrapper);
        //更新库存
        QueryWrapper<GoodsOrderGoods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_id",id);
        List<GoodsOrderGoods> goodsOrderGoodsList = goodsOrderGoodsService.list(queryWrapper);
        for (GoodsOrderGoods snapshop :
                goodsOrderGoodsList) {
            int goodsId = snapshop.getGoodsId();
            int num = snapshop.getNum();
            Goods newGoods = goodsService.getById(goodsId);
            newGoods.setStock(newGoods.getStock()+num);
            newGoods.setSales(newGoods.getSales()-num);
            goodsService.updateById(newGoods);
        }
        //更新订单状态
        orderService.returnFinish(id);
        return Result.ok();
    }



    private QueryWrapper<GoodsOrder> getMyQueryWrapper(OrderVo orderVo) {
        QueryWrapper<GoodsOrder> queryWrapper = new QueryWrapper<>();
        if (orderVo != null) {

            if (orderVo.getState()!=null) {
                queryWrapper.eq("state", orderVo.getState());
            }
            if (orderVo.getId()!=null) {
                queryWrapper.eq("id", orderVo.getId());
            }
            if (orderVo.getStartTime()!=null) {
                queryWrapper.gt("create_time", orderVo.getState());
            }
            if (orderVo.getEndTime()!=null){
                queryWrapper.lt("create_time", orderVo.getState());

            }
        }
        return queryWrapper;
    }
}

