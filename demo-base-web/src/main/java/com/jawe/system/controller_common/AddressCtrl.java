package com.jawe.system.controller_common;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jawe.conf.jwt.JwtTokenUtil;
import com.jawe.response.Result;
import com.jawe.system.entity.*;
import com.jawe.system.service.AddressService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jawe
 * @since 2021-11-20
 */
@RestController
@RequestMapping("/api/common/address")
public class AddressCtrl {

    @Resource
    AddressService addressService;

    @Resource
    JwtTokenUtil jwtTokenUtil;


    @GetMapping("getAddressList")
    @ApiOperation(value = "获取地址列表", notes = "获取地址列表")
    public Result getAddressList(@RequestHeader("${jwt.header}") String token) {
        Integer userId = jwtTokenUtil.getId(token);
        QueryWrapper<Address> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        wrapper.orderByDesc("is_default");
        List<Address> list = addressService.list(wrapper);
        return Result.ok().data("addressList",list);
    }

    @GetMapping("{user_id}")
    @ApiOperation(value = "获取地址列表", notes = "获取地址列表")
    public Result addressList(@PathVariable("user_id") Integer userId) {
        QueryWrapper<Address> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        wrapper.orderByDesc("is_default");
        List<Address> list = addressService.list(wrapper);
        return Result.ok().data("addressList",list);
    }

    @GetMapping("/info/{user_id}/{address_id}")
    @ApiOperation(value = "获取地址信息", notes = "获取地址信息")
    public Result addressInfo(@PathVariable(value = "user_id") Integer userId,
                            @PathVariable(value = "address_id") Integer id) {
        QueryWrapper<Address> wrapper = new QueryWrapper<>();
        wrapper.eq("id",id).eq("user_id",userId);
        return Result.ok().data("address",addressService.getOne(wrapper));
    }




    @PostMapping("")
    @ApiOperation(value = "添加地址", notes = "添加地址")
    public Result add(@RequestBody Address address) {
        onlyDefault(address.getUserId());
        addressService.save(address);
        return Result.ok();
    }


    @PutMapping("/default/{user_id}/{address_id}")
    @ApiOperation(value = "设为默认地址", notes = "设为默认地址")
    public Result onDefault(@PathVariable(value = "user_id") Integer userId,
                            @PathVariable(value = "address_id") Integer id) {
        onlyDefault(userId);
        UpdateWrapper<Address> wrapper = new UpdateWrapper<>();
        wrapper.eq("id",id).set("is_default",1);
        addressService.update(wrapper);
        return Result.ok();
    }

    @PutMapping("{address_id}")
    @ApiOperation(value = "修改地址", notes = "修改地址")
    public Result update( @PathVariable(value = "address_id") Integer id) {

        return Result.ok();
    }


    @DeleteMapping("{address_id}")
    @ApiOperation(value = "删除地址", notes = "删除地址")
    public Result del(@PathVariable(value = "address_id") Integer addressId) {

        if(addressService.removeById(addressId)){
            return Result.ok();
        }else{
            return Result.error();
        }
    }


    private void onlyDefault(int userId){
        QueryWrapper<Address> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId).eq("is_default",1);
        Address one = addressService.getOne(queryWrapper);
        if (one!=null){
            UpdateWrapper<Address> wrapper = new UpdateWrapper<>();
            wrapper.eq("id",one.getId()).set("is_default",0);
            addressService.update(wrapper);
        }
    }


}

