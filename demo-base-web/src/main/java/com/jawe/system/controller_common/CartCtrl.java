package com.jawe.system.controller_common;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jawe.conf.jwt.JwtTokenUtil;
import com.jawe.handler.BusinessException;
import com.jawe.handler.GlobalExceptionHandler;
import com.jawe.response.Result;
import com.jawe.response.ResultCode;
import com.jawe.system.entity.Cart;
import com.jawe.system.entity.Goods;
import com.jawe.system.entity.GoodsCategory;
import com.jawe.system.entity.User;
import com.jawe.system.mapper.CartMapper;
import com.jawe.system.mapper.GoodsCategoryMapper;
import com.jawe.system.service.CartService;
import com.jawe.system.service.GoodsCategoryService;
import com.jawe.system.service.GoodsService;
import com.jawe.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author jawe
 * @since 2021-11-18
 */
@RestController
@RequestMapping("/api/common/cart")
@Api(value = "前台-购物车-api", tags = "前台-购物车")
public class CartCtrl {

    @Resource
    private CartService cartService;

    @Resource
    private CartMapper cartMapper;

    @Resource
    private JwtTokenUtil jwtTokenUtil;

    @Resource
    private GoodsService goodsService;

    @Resource
    private UserService userService;

    @Resource
    private GoodsCategoryMapper categoryMapper;


    @PostMapping("{goodsId}")
    @ApiOperation(value = "添加商品到购物车", notes = "添加商品到购物车")
    public Result cartAdd(@PathVariable("goodsId") Integer goodsId,
                          @RequestHeader(name = "${jwt.header}") String token) {


        int userId = userService.currentUser(jwtTokenUtil.getUsernameFromToken(token)).getId();
        QueryWrapper<Cart> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.eq("goods_id", goodsId);
        Cart cart = cartService.getOne(wrapper);
        if (cart == null) {
            cart = new Cart();
            cart.setUserId(userId);
            cart.setGoodsId(goodsId);
            cart.setNum(1);
            cart.setCategoryId(goodsService.getById(goodsId).getCategoryId());
            cart.setPrice(goodsService.getById(goodsId).getPrice());
            try {
                cartService.save(cart);
            } catch (Exception e) {
                throw new BusinessException(999, "登陆后才能加入购物车");
            }
        } else {
            cartMapper.addNum(cart.getId());
        }


        return Result.ok();
    }

    @GetMapping("")
    @ApiOperation(value = "获取购物车列表", notes = "获取购物车列表")
    public Result cartList(@RequestParam String username,
                           @RequestParam(defaultValue = "false") Boolean includeGoodsInfo
    ) {
        int userId = userService.currentUser(username).getId();
        QueryWrapper<Cart> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId).orderByAsc("category_id");
        List<Cart> cartList = cartService.list(wrapper);
        //没有数据

        if (cartList.size()==0||cartList==null){
            return Result.ok().data("records", new ArrayList<Cart>());
        }

        List<GoodsCategory> records = new ArrayList<>();
        int lastId = -1;
        List<Cart> record = new ArrayList<>();


        if (includeGoodsInfo) {
            for (Cart cart :
                    cartList) {

                Goods goods = goodsService.getById(cart.getGoodsId());
                goods.setCoverUrl("https://player-one.oss-cn-beijing.aliyuncs.com"+goods.getCoverUrl());
                cart.setGoods(goods);
                cart.setCategoryId(goods.getCategoryId());
                cart.setCategoryName(categoryMapper.getName(goods.getCategoryId()));

                int categoryId = cart.getCategoryId();
                if (lastId != categoryId && lastId!=-1) {
                    GoodsCategory goodsCategory = categoryMapper.selectById(lastId);
                    goodsCategory.setCarts(record);
                    // 判断这些商品是否全选
                    boolean isActiveAll = true;
                    for (Cart item:
                         record) {
                        if (item.getIsActive()==0) {
                            isActiveAll=false;
                        }
                    }
                    goodsCategory.setIsActiveAll(isActiveAll?1:0);
                    records.add(goodsCategory);
                    record = new ArrayList<>();
                    record.add(cart);
                }else{
                    record.add(cart);
                }
                lastId = categoryId;
            }
            // 判断这些商品是否全选
            boolean isActiveAll = true;
            for (Cart item:
                    record) {
                if (item.getIsActive()==0) {
                    isActiveAll=false;
                }
            }
            GoodsCategory goodsCategory = categoryMapper.selectById(lastId);
            goodsCategory.setCarts(record);
            goodsCategory.setIsActiveAll(isActiveAll?1:0);
            records.add(goodsCategory);
        }

        return Result.ok().data("records", records);
    }


    @GetMapping("num")
    @ApiOperation(value = "查询购物车数量", notes = "查询购物车数量")
    public Result cartCount(@RequestParam String username) {

        int userId = userService.currentUser(username).getId();
        QueryWrapper<Cart> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);

        return Result.ok().data("count", cartService.count(wrapper));
    }

    @PutMapping("num/{id}/{num}")
    @ApiOperation(value = "改变商品数量", notes = "改变商品数量")
    public Result cartChangeNum(@PathVariable("id") Integer id,
                                @PathVariable("num") Integer num) {
        if (num<1||num>30){
            return Result.error().message("商品数量异常");
        }
        UpdateWrapper<Cart> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", id).set("num", num);
        cartService.update(wrapper);
        return Result.ok().message("商品数量改变成功");
    }

    @PutMapping("active/{id}")
    @ApiOperation(value = "选中商品", notes = "选中商品")
    public Result cartActive(@PathVariable("id") Integer id) {
//        int userId = userService.currentUser(username).getId();
//        cartMapper.onActive(userId, goodsId);
        cartMapper.onActive(id);
        return Result.ok().message("选中商品");
    }

    @DeleteMapping("{username}/{cartId}")
    @ApiOperation(value = "删除购物车商品", notes = "删除购物车商品")
    public Result cartDel(@PathVariable("username") String username,
                          @PathVariable("cartId") Integer cartId) {

//        System.out.println(username);
//        System.out.println(cartId);
        Integer userId = userService.currentUser(username).getId();
        QueryWrapper<Cart> cartWrapper = new QueryWrapper<>();
        cartWrapper.eq("user_id",userId).eq("id",cartId);
        cartMapper.delete(cartWrapper);
        return Result.ok().message("删除购物车商品");
    }

}

