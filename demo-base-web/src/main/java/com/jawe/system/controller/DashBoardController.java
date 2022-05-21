package com.jawe.system.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jawe.response.Result;
import com.jawe.system.entity.FlashSale;
import com.jawe.system.entity.GoodsOrder;
import com.jawe.system.entity.GoodsOrderGoods;
import com.jawe.system.service.GoodsOrderGoodsService;
import com.jawe.system.service.OrderService;
import com.jawe.vo.DashBoard1;
import com.jawe.vo.DashBoard2;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/dashBoard")
public class DashBoardController {


    @Resource
    private GoodsOrderGoodsService snapshotService;
    @Resource
    private OrderService orderService;

    //昨日销量、本周销量
    //商品销售排行
    //分类商品数量统计
    @GetMapping("")
    @ApiOperation(value = "数据概论", notes = "数据概论")
    public Result dashBoard() {
        QueryWrapper<GoodsOrder> wrapper = new QueryWrapper<>();
        wrapper.gt("create_time", LocalDateTime.of(LocalDate.now(), LocalTime.MIN));
        List<GoodsOrder> goodsOrderList = orderService.list(wrapper);
        BigDecimal amountTotal = BigDecimal.ZERO;
        for (GoodsOrder order :
                goodsOrderList) {
            amountTotal = amountTotal.add(order.getPrice());
        }

        return Result.ok().data("orderTotal",goodsOrderList.size()).data("amountTotal",amountTotal);
    }






}
