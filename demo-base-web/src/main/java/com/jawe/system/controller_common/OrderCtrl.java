package com.jawe.system.controller_common;

import cn.hutool.db.sql.Order;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.util.concurrent.RateLimiter;
import com.jawe.conf.SnowFlake;
import com.jawe.conf.jwt.JwtTokenUtil;
import com.jawe.conf.redis.RedisAlias;
import com.jawe.handler.BusinessException;
import com.jawe.response.Result;
import com.jawe.response.ResultCode;
import com.jawe.system.entity.*;
import com.jawe.system.service.*;
import com.jawe.vo.OrderGoodsVo;
import com.jawe.vo.OrderListVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.apache.ibatis.builder.BuilderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/common/order")
@Api(value = "前台-订单-api", tags = "前台-订单")
public class OrderCtrl {

    @Autowired
    private OrderService orderService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private GoodsOrderGoodsService goodsOrderGoodsService;
    @Autowired
    private CartService cartService;
    @Autowired
    private FlashSaleService flashSaleService;
    @Resource
    private AliOssEntity aliOssEntity;
    @Resource
    JwtTokenUtil jwtTokenUtil;
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private StringRedisTemplate stringRedisTemplate;



    // 创建令牌桶实例
    private RateLimiter rateLimiter = RateLimiter.create(40);//每秒产生多少个token



    @GetMapping("orderList")
    @ApiOperation(value = "获取个人订单列表", notes = "获取个人订单列表")
    public Result select(@RequestHeader("${jwt.header}") String token,
                         @RequestParam(defaultValue = "1") Integer current,
                         @RequestParam(defaultValue = "8") Integer pageSize
    ) {
        Page<GoodsOrder> page = new Page<>(current, pageSize);
        QueryWrapper<GoodsOrder> wrapper = new QueryWrapper<>();
        int userId = jwtTokenUtil.getId(token);
        /*查找订单（条件）*/
        wrapper.eq("user_id",userId).orderByDesc("id");
        //不显示已取消.ne("state",3)
        Page<GoodsOrder> goodsPage = orderService.page(page,wrapper);
        List<GoodsOrder> records = goodsPage.getRecords();
        /*按订单查找相关商品快照*/
        ArrayList<OrderListVO> orderListVOs = new ArrayList<>();
        for (GoodsOrder goodsOrder:records
             ) {
            Long orderId = goodsOrder.getId();
            List<GoodsOrderGoods> tempList;
            QueryWrapper<GoodsOrderGoods> wrapper2 = new QueryWrapper<>();
            wrapper2.eq("order_id",orderId);
            tempList = goodsOrderGoodsService.list(wrapper2);
            for (GoodsOrderGoods tempGoods :
                    tempList) {
                tempGoods.setPicUrl(aliOssEntity.getSrc()+tempGoods.getPicUrl());
            }
            OrderListVO orderListVO = new OrderListVO(orderId.toString(),goodsOrder.getState().getCode(),tempList);
            orderListVOs.add(orderListVO);
        }

        return Result.ok().data("orderGoodsList",orderListVOs );
    }

    @GetMapping("orderListByState")
    @ApiOperation(value = "获取个人订单列表（按不同状态）", notes = "获取个人订单列表（按不同状态）")
    public Result selectByState(@RequestHeader("${jwt.header}") String token,
                                @RequestParam Integer state
    ) {
        if (state==4){state=5;}
        if (state==3){state=4;}
        QueryWrapper<GoodsOrder> wrapper = new QueryWrapper<>();
        int userId = jwtTokenUtil.getId(token);
        /*查找订单（条件）*/
        wrapper.eq("user_id",userId).eq("state",state);
        List<GoodsOrder> records = orderService.list(wrapper);
        /*按订单查找相关商品快照*/
        ArrayList<OrderListVO> orderListVOs = new ArrayList<>();
        for (GoodsOrder goodsOrder:records
        ) {
            Long orderId = goodsOrder.getId();
            List<GoodsOrderGoods> tempList;
            QueryWrapper<GoodsOrderGoods> wrapper2 = new QueryWrapper<>();
            wrapper2.eq("order_id",orderId);
            tempList = goodsOrderGoodsService.list(wrapper2);
            for (GoodsOrderGoods tempGoods :
                    tempList) {
                tempGoods.setPicUrl(aliOssEntity.getSrc()+tempGoods.getPicUrl());
            }
            OrderListVO orderListVO = new OrderListVO(orderId.toString(),goodsOrder.getState().getCode(),tempList);
            orderListVOs.add(orderListVO);
        }

        return Result.ok().data("orderGoodsList",orderListVOs);
    }

    @Transactional
    @PostMapping("")
    @ApiOperation(value = "创建普通订单", notes = "创建普通订单")
    public Result create(
            @RequestHeader("${jwt.header}") String token
            , @RequestBody OrderGoodsVo goodsMap
    ) {

        GoodsOrder order = new GoodsOrder();
        ArrayList<Integer> ids = goodsMap.getIds();
        ArrayList<Integer> numList = goodsMap.getNumList();
        //1.获取选择商品
        List<Goods> goodsList = goodsService.listByIds(ids);
        //2.扣库存
        for (int i = 0; i < ids.size(); i++) {
            if(goodsService.desStock(ids.get(i), numList.get(i))!=1){
                throw new BusinessException(ResultCode.GOODS_NOT_STOCK.getCode(), ResultCode.GOODS_NOT_STOCK.getMessage());
            }
        }
        //3.删除对应购物车
        cartService.delGoods(jwtTokenUtil.getId(token),ids);

        //4.计算价格
        BigDecimal price = new BigDecimal(0);
        for (int i = 0; i < goodsList.size(); i++) {
            price = price.add(goodsList.get(i).getPrice().multiply(BigDecimal.valueOf(numList.get(i))));
            System.out.println(goodsList.get(i).getPrice() + "  " + price);
        }
        order.setPrice(price);
        //5.1用户id
        order.setUserId(jwtTokenUtil.getId(token));
        //5.2雪花算法设置orderId
        SnowFlake snowFlake = new SnowFlake(1, 1);
        order.setId(snowFlake.nextId());
        orderService.create(order);
        //6.生成商品快照
        List<GoodsOrderGoods> orderGoodsList = new ArrayList<>();
        int index = 0;
        for (Goods goods :
                goodsList) {
            GoodsOrderGoods orderGoods = new GoodsOrderGoods();
            orderGoods.setGoodsId(goods.getId());
            orderGoods.setOrderId(order.getId());
            orderGoods.setCurPrice(goods.getPrice());
            orderGoods.setTitle(goods.getGoodsName());
            orderGoods.setIsCancel(0);
            orderGoods.setPicUrl(goods.getCoverUrl());
            orderGoods.setNum(numList.get(index++));
            orderGoodsList.add(orderGoods);
        }
        goodsOrderGoodsService.saveBatch(orderGoodsList);
        redisTemplate.opsForValue().set(RedisAlias.CREATE_ORDER + order.getId(),1,30,TimeUnit.MINUTES);
        return Result.ok().data("orderId",order.getId().toString());
    }

    @Transactional
    @GetMapping("kill/{flash_id}")
    @ApiOperation(value = "创建秒杀订单", notes = "创建秒杀订单")
    public Result kill(
            @RequestHeader("${jwt.header}") String token,
            @PathVariable("flash_id") Integer flashId
    ) {
        Integer userId = jwtTokenUtil.getId(token);
        if (redisTemplate.hasKey("onlyUser"+flashId+jwtTokenUtil.getId(token))){
            throw new BusinessException(3000,"单用户只能购买一次活动商品");
        }

            //验证活动是否开始
        QueryWrapper<FlashSale> wrapper = new QueryWrapper<>();
        wrapper.eq("id",flashId).orderByDesc("start_time");
        FlashSale one = flashSaleService.list(wrapper).get(0);
        if (one.getStartTime().isAfter(LocalDateTime.now())){
            throw new BusinessException(4003,"活动还未开始");
        }
        //令牌桶限流
        boolean pass = rateLimiter.tryAcquire(1, TimeUnit.SECONDS);
        if (!pass) {
            new BusinessException(1000,"抢购太过火爆，请重试");
        }
        //判断库存

        if (!redisTemplate.hasKey(RedisAlias.FLASH_SALE+one.getGoodsId().toString())) {
            throw new BusinessException(1111,"秒杀活动不存在");
        }
        Long decrement = redisTemplate.opsForValue().decrement(RedisAlias.FLASH_SALE + one.getGoodsId().toString());
        if (decrement<0){
            redisTemplate.opsForValue().increment(RedisAlias.FLASH_SALE + one.getGoodsId().toString());
            throw new BusinessException(1111,"库存不足");
        }

        //插入订单表
        FlashSale flashSale = flashSaleService.getById(flashId);
        GoodsOrder order = new GoodsOrder();
        order.setUserId(userId);
        SnowFlake snowFlake = new SnowFlake(1, 1);
        Long orderId = snowFlake.nextId();
        order.setId(orderId);
        order.setPrice(flashSale.getCurPrice());
        order.setAddressId(1);
        orderService.create(order);
        //插入商品快照
        Goods goods = goodsService.getById(flashSale.getGoodsId());
        GoodsOrderGoods orderGoods = new GoodsOrderGoods();
        orderGoods.setGoodsId(goods.getId());
        orderGoods.setOrderId(order.getId());
        orderGoods.setCurPrice(flashSale.getCurPrice());
        orderGoods.setTitle(goods.getGoodsName());
        orderGoods.setIsCancel(0);
        orderGoods.setPicUrl(goods.getCoverUrl());
        orderGoods.setNum(1);
        goodsOrderGoodsService.save(orderGoods);

        //设置redis定时任务,定时取消订单
        redisTemplate.opsForValue().set(RedisAlias.CREATE_FLASH_ORDER + orderId,1,10,TimeUnit.MINUTES);

        //限制单用户购买
        redisTemplate.opsForValue().set("onlyUser"+flashId+jwtTokenUtil.getId(token),1,10,TimeUnit.MINUTES);

        flashSaleService.update();
        goodsService.update();
        return Result.ok().data("orderId",orderId.toString());
    }

    @PostMapping("pay/{orderId}")
    @ApiOperation(value = "支付", notes = "支付")
    public Result pay(@RequestHeader("${jwt.header}") String token,
                      @PathVariable("orderId") Long orderId) {
        jwtTokenUtil.getId(token);
        orderService.pay(orderId);
        return Result.ok();
    }

    @PutMapping("cancel/{orderId}")
    @ApiOperation(value = "取消订单", notes = "取消订单")
    public Result cancel(@PathVariable("orderId") Long orderId, @RequestHeader("${jwt.header}") String token) {
//        if (!checkAuth(id, token)) return Result.error();
        //更新库存
//        QueryWrapper<GoodsOrderGoods> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("order_id",orderId);
//        List<GoodsOrderGoods> goodsOrderGoodsList = goodsOrderGoodsService.list(queryWrapper);
//        for (GoodsOrderGoods snapshop :
//                goodsOrderGoodsList) {
//            int goodsId = snapshop.getGoodsId();
//            int num = snapshop.getNum();
//            Goods newGoods = goodsService.getById(goodsId);
//            newGoods.setStock(newGoods.getStock()+num);
//            newGoods.setSales(newGoods.getSales()-num);
//            goodsService.updateById(newGoods);
//        }
        //更新订单状态
        orderService.cancel(orderId);
        return Result.ok();
    }



    @PutMapping("receive/{id}")
    @ApiOperation(value = "确认收货", notes = "确认收货")
    public Result receive(@PathVariable("id") Long id, @RequestHeader("${jwt.header}") String token) {
//        if (!checkAuth(id, token)) return Result.error();
        orderService.receive(id);
        return Result.ok();
    }

    @PutMapping("comment/{id}")
    @ApiOperation(value = "评论", notes = "评论")
    public Result comment(@PathVariable("id") Long id, @RequestHeader("${jwt.header}") String token) {
//        if (!checkAuth(id, token)) return Result.error();
        //插入评论，计算分数


        //更新订单状态
        orderService.comment(id);
        return Result.ok();
    }

    @PutMapping("returning/{id}")
    @ApiOperation(value = "申请退货", notes = "申请退货")
    public Result returning(@PathVariable("id") Long id, @RequestHeader("${jwt.header}") String token) {
//        if (!checkAuth(id, token)) return Result.error();

        orderService.returning(id);
        return Result.ok();
    }


    private boolean checkAuth(int id, String token) {
        return jwtTokenUtil.getId(token) == orderService.getById(id).getUserId();
    }

}
