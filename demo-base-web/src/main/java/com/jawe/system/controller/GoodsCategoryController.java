package com.jawe.system.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jawe.handler.BusinessException;
import com.jawe.response.Result;
import com.jawe.response.ResultCode;
import com.jawe.system.entity.Goods;
import com.jawe.system.entity.GoodsCategory;
import com.jawe.system.service.GoodsCategoryService;
import com.jawe.system.service.GoodsService;
import com.jawe.system.service.impl.GoodsCategoryServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author jawe
 * @since 2021-10-28
 */
@RestController
@RequestMapping(value = "/api/goods-category")
@Api(value = "商品分类模块", tags = "商品分类信息")
public class GoodsCategoryController {
    @Autowired
    private GoodsCategoryService goodsCategoryService;

    @GetMapping("tree")
    @ApiOperation(value = "获取分类信息-树结构", notes = "获取商品分类信息树")
    public Result getCategoryTree(@RequestParam(defaultValue = "false") boolean isEnable) {
        List<GoodsCategory> categoryTree = this.goodsCategoryService.getTreeList(isEnable);
        return Result.ok().data("categoryTree", categoryTree);
    }

    @GetMapping("parentTree")
    @ApiOperation(value = "获取分类父级信息树", notes = "用来添加下级分类")
    public Result getCategoryPidTree(@RequestParam(defaultValue = "false") boolean isEnable) {
        QueryWrapper<GoodsCategory> wrapper = new QueryWrapper<>();
        wrapper.eq("pid", 0);
        List<GoodsCategory> categoryTree = this.goodsCategoryService.getBaseMapper().selectList(wrapper);
        GoodsCategory goodsCategory = new GoodsCategory();
        goodsCategory.setId(0);
        goodsCategory.setName("===一级目录===");
        categoryTree.add(0, goodsCategory);
        return Result.ok().data("records", categoryTree);
    }

    @GetMapping("list")
    @ApiOperation(value = "获取分类列表，可选层级", notes = "获取分类列表，可选层级")
    public Result getCategoryList(
            @RequestParam(defaultValue = "false") boolean isEnable,
            @RequestParam(defaultValue = "0") Integer pid
    ) {
        QueryWrapper<GoodsCategory> wrapper = new QueryWrapper<>();
        if (isEnable) {
            wrapper.eq("enable", 1);
        }
        wrapper.eq("pid", pid);
        List<GoodsCategory> goodsCategoriesList = goodsCategoryService.getBaseMapper().selectList(wrapper);
        return Result.ok().data("records", goodsCategoriesList);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "获取单个分类信息", notes = "通过id获取分类信息/api/goods-category/{id}")
    public Result getCategoryById(@PathVariable("id") Integer id) {
        GoodsCategory category = this.goodsCategoryService.getById(id);
        if (category != null) {
            return Result.ok().data("records", category);
        } else {
            throw new BusinessException(ResultCode.USER_ACCOUNT_NOT_EXIST.getCode(), ResultCode.USER_ACCOUNT_NOT_EXIST.getMessage());
        }
    }

    @PostMapping("")
    @ApiOperation(value = "添加分类")
    public Result addCategory(@RequestBody GoodsCategory category) {
        if (goodsCategoryService.save(category)) {
            return Result.ok().data("category", category);
        } else {
            throw new BusinessException(ResultCode.GOODS_ALREADY_EXIST.getCode(), ResultCode.GOODS_ALREADY_EXIST.getMessage());
        }
    }

    @PutMapping("")
    @ApiOperation(value = "修改分类信息")
    public Result updateCategory(@RequestBody GoodsCategory category) {
        if (goodsCategoryService.updateById(category)) {
            return Result.ok().message("修改成功");
        } else {
            throw new BusinessException(ResultCode.GOODS_ALREADY_EXIST.getCode(), ResultCode.GOODS_ALREADY_EXIST.getMessage());
        }
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "分类是否可用", notes = "修改分类是否可用/api/goods-category/{id}")
    public Result isOnGoods(@PathVariable("id") Integer id) {
        int res = this.goodsCategoryService.isEnable(id);
        System.out.println(res);
        if (res != 0) {
            return Result.ok();
        } else {
            return Result.error();
        }
    }


}

