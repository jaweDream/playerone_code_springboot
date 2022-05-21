package com.jawe.system.controller;

import com.jawe.cache.IGlobalCache;
import com.jawe.response.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
public class TestController {

//
//    @GetMapping("getAll")
//    public Object getAll() {
//        globalCache.set("key2", "value3");
//        globalCache.lSetAll("list", Arrays.asList("hello", "redis"));
//        List<Object> list = globalCache.lGet("list", 0, -1);
//        return globalCache.get("key2");
//    }

    // 日志管理
    @GetMapping("/syslog")
    public String showOrder() {
        return "syslog";
    }

    // 用户管理
    @GetMapping("/sysuser")
    public String addOrder() {
        return "sysuser";
    }

    @GetMapping("/sysshop")
    public String addShop() {
        return "sysshop";
    }

    @GetMapping("/syscommodity")
    public String syscommunity() {
        return "commodity";
    }

    // 具体业务一
    @GetMapping("/biz1")
    public String updateOrder() {
        return "biz1";
    }

    // 具体业务二
    @GetMapping("/biz2")
    public String deleteOrder() {
        return "biz2";
    }

    @RequestMapping(value = "helloCSRF",method = RequestMethod.POST)
    public String helloCSRF(){
        return "helloCSRF";
    }

    @PostMapping("/api/testapi")
    public Result testApi(){
        return Result.ok().message("123123");
    }

}
