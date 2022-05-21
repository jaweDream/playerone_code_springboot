package com.jawe.system.controller;


import com.google.common.util.concurrent.RateLimiter;
import com.jawe.response.Result;
import com.jawe.system.service.StockOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author jawe
 * @since 2021-10-04
 */
@RestController
@RequestMapping("/stockOrder")
@Api(value = "秒杀库存系统")
public class StockOrderController {

    @Autowired
    private StockOrderService stockOrderService;

    private RateLimiter rateLimiter = RateLimiter.create(40);


    @ApiOperation(value="秒杀操作")
    @GetMapping("/secKill")
    public Result secKill(Integer id) {

        if (!rateLimiter.tryAcquire(4, TimeUnit.SECONDS)){
            System.out.println("购买失败");
            return Result.error().message("购买失败");
        }
        try {
            int orderId = stockOrderService.kill(id);
            return Result.ok().message("购买成功,订单id: " + orderId);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error().message("x");

        }
    }

    @ApiOperation("隐藏秒杀接口,获取秒杀权限")
    @RequestMapping(path = "/getSecRole",method = RequestMethod.GET)
    public Result getSecRole(){


        return Result.error().message("x");
    }
}

