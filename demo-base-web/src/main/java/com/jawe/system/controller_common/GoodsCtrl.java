package com.jawe.system.controller_common;


import cn.hutool.jwt.JWTUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jawe.conf.jwt.JwtTokenUtil;
import com.jawe.conf.redis.RedisAlias;
import com.jawe.response.Result;
import com.jawe.system.entity.*;
import com.jawe.system.service.*;
import com.jawe.vo.GoodsVo;
import com.mysql.cj.util.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author jawe
 * @since 2021-10-24
 */
@RestController
@RequestMapping("/api/common/goods")
@Api(value = "前台-商品-api", tags = "前台-商品")
public class GoodsCtrl {
    @Resource
    private GoodsService goodsService;
    @Resource
    private AliOssEntity aliOssEntity;
    @Resource
    private GoodsCategoryService categoryService;
    @Resource
    private InsetService insetService;
    @Resource
    private CommentService commentService;
    @Resource
    private UserService userService;
    @Resource
    private GoodsCollectionService collectionService;
    @Resource
    private JwtTokenUtil jwtTokenUtil;
    @Resource
    private RedisTemplate redisTemplate;


    /* 首页 */
    @GetMapping("")
    @ApiOperation(value = "获取商品列表", notes = "前台商品首页")
    public Result goodsList(@RequestHeader(name = "${jwt.header}",required = false) String token,
                            @RequestParam(defaultValue = "1") Integer current,
                            @RequestParam(defaultValue = "8") Integer pageSize,
                            //首页有哪些排序方式：默认0、销量1、上新2、活动3
                            @RequestParam(defaultValue = "0",required = false) Integer order,
                            @RequestParam(defaultValue = "",required = false) String title,
                            @RequestParam (value = "category_id",required = false) String categoryId


    ) {
        Page<Goods> page = new Page<>(current, pageSize);
        /*排序方式*/
        if (order==0){
            page.addOrder(OrderItem.desc("sales"));
        }else {
            if (order == 1) page.addOrder(OrderItem.desc("sales"));
            else if (order == 2) page.addOrder(OrderItem.desc("create_time"));
        }
        /*标题查询,分类查询*/
        QueryWrapper<Goods> wrapper = new QueryWrapper<>();
        wrapper.ne("is_on",0);
        if (!StringUtils.isNullOrEmpty(title)){
            wrapper.like("goods_name",title);
            if (!StringUtils.isNullOrEmpty(token)){
                redisTemplate.opsForValue().set(RedisAlias.USER_COMMENT+jwtTokenUtil.getId(token),title);
            }
        }
        if (categoryId!=null&&categoryId!=""){
            wrapper.eq("category_id",categoryId);
        }
        /*sql逻辑*/
        Page<Goods> goodsPage = goodsService.page(page,wrapper);
        long total = goodsPage.getTotal();
        List<Goods> records = goodsPage.getRecords();
        for (Goods goods : records
        ) {
            goods.setCoverUrl(aliOssEntity.getSrc() + goods.getCoverUrl());
        }
        return Result.ok().data("total", total).data("records", records);

    }

    @GetMapping("{goodsId}")
    @ApiOperation(value = "获取商品详情", notes = "前台商品详情页")
    public Result goodsInfo(@PathVariable(value = "goodsId") Integer goodsId,
                            @RequestHeader(name = "${jwt.header}", required = false) String token
    ) {
        // 主图、插图、详情
        Goods goods = goodsService.getById(goodsId);
        QueryWrapper<Inset> insetWrapper = new QueryWrapper<>();
        insetWrapper.eq("goods_id", goodsId);
        List<Inset> insets = insetService.list(insetWrapper);
        Inset inset = new Inset(0, goodsId, goods.getCoverUrl(), 0);
        insets.add(0, inset);
        goods.setInsets(insets);



        // 是否收藏该商品
//        if (token != null && token != "" && jwtTokenUtil.validateToken(token)) {
//            User user = userService.currentUser(jwtTokenUtil.getUsernameFromToken(token));
//            Integer userId = user.getId();
//            QueryWrapper<GoodsCollection> wrapper = new QueryWrapper<>();
//            wrapper.eq("user_id", userId);
//            wrapper.eq("goods_id", goodsId);
//            if (collectionService.count(wrapper) > 0) {
//                goods.setIsCollect(true);
//            } else {
//                goods.setIsCollect(false);
//            }
//        } else {
//            goods.setIsCollect(false);
//        }


        return Result.ok().data("goodsInfo", goods);
    }

    @PostMapping("")
    @ApiOperation(value = "通过idList获取商品信息", notes = "idList获取商品信息")
    public Result findGoodsByIdList(@RequestBody List<Integer> goodsIds
    ) {

        List<Goods> goodsList = goodsService.listByIds(goodsIds);
        return Result.ok().data("goodsList",goodsList);
    }

    /* 分类页 */
    @GetMapping("categoryList")
    @ApiOperation(value = "获取分类列表", notes = "获取分类列表")
    public Result goodsCategoryList() {


        return Result.ok().data("categoryList",categoryService.getTreeList(true));
    }

//    @PostMapping("")
//    @ApiOperation(value = "获取商品列表", notes = "前台商品首页")
//    public Result goodsListByCategory(@RequestParam(defaultValue = "1") Integer current,
//                                      @RequestParam(defaultValue = "8") Integer pageSize
//    ) {
//
//
//    }

    /* 收藏页 */
    @GetMapping("collection")
    @ApiOperation(value = "获取收藏列表", notes = "获取收藏列表")
    public Result collectionList() {
        return Result.ok();
    }

    @PutMapping("collection/{user_id}/{goods_id}")
    @ApiOperation(value = "收藏商品", notes = "收藏商品")
    public Result collection(@PathVariable(value = "user_id") Integer userId,
                             @PathVariable(value = "goods_id") Integer goodsId) {

        QueryWrapper<GoodsCollection> collectionWrapper = new QueryWrapper<>();
        collectionWrapper.eq("user_id", userId);
        collectionWrapper.eq("goods_id", goodsId);
        if (collectionService.count(collectionWrapper) == 0) {
            GoodsCollection collection = new GoodsCollection();
            collection.setGoodsId(goodsId);
            collection.setUserId(userId);
            collectionService.save(collection);
        } else {
            collectionService.remove(collectionWrapper);
        }
        return Result.ok();
    }


    private QueryWrapper<Goods> getMyQueryWrapper(GoodsVo goodsVo) {
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        if (goodsVo != null) {
//            if (!StringUtils.isEmpty(goodsVo.getUsername())) {
//                queryWrapper.like("username", goodsVo.getUsername());
//            }
//            if (!StringUtils.isEmpty(goodsVo.getGender())) {
//                queryWrapper.eq("gender", goodsVo.getGender());
//            }
//            if (!StringUtils.isEmpty(goodsVo.getEmail())) {
//                queryWrapper.like("email", goodsVo.getEmail());
//            }
//            if (!StringUtils.isEmpty(goodsVo.getCreateTime())) {
//                queryWrapper.orderBy(true, goodsVo.getCreateTime().equals("descend")?false:true,"create_time");
//            }
        }
        return queryWrapper;
    }
}

