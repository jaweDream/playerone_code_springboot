package com.jawe.system.controller;


import com.jawe.response.Result;
import com.jawe.system.service.MenuService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author jawe
 * @since 2021-09-12
 */
@RestController
@RequestMapping("/menu")
@Api(value = "菜单管理模块",tags = "菜单信息")
public class MenuController {
    @Autowired
    private MenuService menuService;

    @GetMapping("/getMenuList")
    public Result getMenuList() {
        HashMap<Object,Object> menuList = new HashMap<>();
        menuList.put("id",1);
        menuList.put("parentId",0);
        menuList.put("menuName","系统管理");

        return Result.ok().data("menuList",menuService.queryMenuTree());

    }


    @PostMapping("/getMenuList")
    public Result PostMenuList() {
        HashMap<Object,Object> menuList = new HashMap<>();
        menuList.put("id",1);
        menuList.put("parentId",0);
        menuList.put("menuName","系统管理");

        return Result.ok().data("menuList",menuService.queryMenuTree());

    }

}

