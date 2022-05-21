package com.jawe.system.controller_common;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jawe.conf.jwt.JwtTokenUtil;
import com.jawe.response.Result;
import com.jawe.system.entity.AliOssEntity;
import com.jawe.system.entity.Comment;
import com.jawe.system.entity.GoodsOrderGoods;
import com.jawe.system.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/common/comment")
@Api(value = "前台-评论-api", tags = "前台-评论")
public class CommentCtrl {

    @Resource
    private GoodsService goodsService;
    @Resource
    private AliOssEntity aliOssEntity;
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
    @Autowired
    private OrderService orderService;
    @Autowired
    private GoodsOrderGoodsService goodsOrderGoodsService;
    @Autowired
    private CartService cartService;
    @Autowired
    private FlashSaleService flashSaleService;
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping("star")
    @ApiOperation(value = "数据概论", notes = "数据概论")
    public Result star(@RequestHeader(name = "${jwt.header}", required = false) String token,
                       @RequestParam("order_id") Long orderId,
                       @RequestParam("star") int star) {
        Integer userId = jwtTokenUtil.getId(token);
        //找出所有商品
        QueryWrapper<GoodsOrderGoods> wrapper = new QueryWrapper<>();
        wrapper.eq("order_id",orderId);
        List<GoodsOrderGoods> snapshots = goodsOrderGoodsService.list(wrapper);
        for (GoodsOrderGoods snapshot :
                snapshots) {
            //为每个商品加评论
            Comment comment = new Comment();
            comment.setContent("无");
            comment.setUserId(userId);
            comment.setGoodsId(snapshot.getGoodsId());
            comment.setStar(star);
            commentService.save(comment);
        }
        orderService.comment(orderId);
        return Result.ok().message("评论成功");
    }

    @GetMapping("star/{goods_id}")
    @ApiOperation(value = "数据概论", notes = "数据概论")
    public Result getStar(@PathVariable("goods_id") Long goodsId) {
        //获取全部star
        QueryWrapper<Comment> commentQueryWrapper = new QueryWrapper<>();
        commentQueryWrapper.eq("goods_id",goodsId);
        List<Comment> list = commentService.list(commentQueryWrapper);
        double result = 0;
        for (Comment comment :
                list) {
            result+=comment.getStar();
        }
        result/=list.size();
        result*=20;
        if (result>0){
            DecimalFormat df = new DecimalFormat("#0.00");
            return Result.ok().data("star",df.format(result));
        }else{
            return Result.ok().data("star",0);

        }
    }

}
