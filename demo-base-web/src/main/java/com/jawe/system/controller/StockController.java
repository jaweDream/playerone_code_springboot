package com.jawe.system.controller;


import com.jawe.response.Result;
import com.jawe.system.entity.Stock;
import com.jawe.system.entity.StockOrder;
import com.jawe.system.service.StockOrderService;
import com.jawe.system.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jawe
 * @since 2021-10-04
 */
@RestController
@RequestMapping("/stock")
public class StockController {

    @Autowired
    StockService stockService;


    @GetMapping("/getStock")
    public Result getStock(){

        List<Stock> stockList = stockService.list();


        return Result.ok().data("stockList",stockList);
    }
}

