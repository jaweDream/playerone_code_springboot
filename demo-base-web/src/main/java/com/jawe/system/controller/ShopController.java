package com.jawe.system.controller;


import com.jawe.response.Result;
import io.swagger.models.auth.In;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping("/api/shop")
public class ShopController {

    @GetMapping("/fetchDashBoard")
    public Result getDashBoard(){
        HashMap<String, Object> data = new HashMap<>();
        data.put("user_count",12);
        data.put("goods_count",44);
        data.put("order_count",11);

        return Result.ok().data(data);
    }


    @GetMapping("/getStock")
    public Result getStock(){

        HashMap<String, Object> data = new HashMap<>();
        data.put("sdf",12);
        data.put("www",44);
        data.put("aaa",11);
        data.put("sss",23);


        return Result.ok().data(data);
    }

}
