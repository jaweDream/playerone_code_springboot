package com.jawe.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jawe.conf.redis.RedisAlias;
import com.jawe.handler.BusinessException;
import com.jawe.system.entity.Goods;
import com.jawe.system.entity.GoodsOrder;
import com.jawe.state.enums.OrderStatus;
import com.jawe.state.OrderStatusChangeEvent;
import com.jawe.system.entity.GoodsOrderGoods;
import com.jawe.system.entity.StockOrder;
import com.jawe.system.mapper.OrderMapper;
import com.jawe.system.mapper.StockOrderMapper;
import com.jawe.system.service.GoodsOrderGoodsService;
import com.jawe.system.service.GoodsService;
import com.jawe.system.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, GoodsOrder> implements OrderService {

    private final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Resource
    private StateMachine<OrderStatus,OrderStatusChangeEvent> orderStateMachine;

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private GoodsService goodsService;

    @Resource
    private GoodsOrderGoodsService goodsOrderGoodsService;

    @Autowired
    private StateMachinePersister<OrderStatus,OrderStatusChangeEvent, GoodsOrder> persister;

    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public IPage<GoodsOrder> findOrderPage(Page<GoodsOrder> page, QueryWrapper<GoodsOrder> queryWrapper) {
        return this.baseMapper.findOrderPage(page,queryWrapper);
    }

    @Transactional
    @Override
    public Integer create(GoodsOrder order) {
        //设置订单状态为临时订单
        //扣减库存（事务）
        //30分钟定时未支付取消订单
        order.setState(OrderStatus.WAIT_PAYMENT);
        order.setCreateTime(new Date());

//        System.out.println(order.getState().getValue());
        return orderMapper.insert(order);
    }

    @Override
    public GoodsOrder pay(Long orderId) {
//        Long orderId = Long.getLong(orderIdStr);
        GoodsOrder order = orderMapper.selectById(orderId);;
        if (!sendEvent(OrderStatusChangeEvent.PAYED, order)) {
            throw new RuntimeException(" 等待支付 -> 等待发货 失败, 状态异常 order=" + order);
        }
        order.setPayTime(new Date());
        orderMapper.updateById(order);

        return order;
    }

    @Override
    public GoodsOrder deliver(Long id) {

        GoodsOrder order = orderMapper.selectById(id);;
        if (!sendEvent(OrderStatusChangeEvent.DELIVERY, order)) {
            throw new RuntimeException(" 等待发货 -> 等待收货 失败，状态异常 order=" + order);
        }
        order.setDeliverTime(new Date());
        orderMapper.updateById(order);
        return order;
    }

    @Override
    public GoodsOrder cancel(Long id) {
        GoodsOrder order = orderMapper.selectById(id);
        logger.info(String.valueOf(id));
        if (!sendEvent(OrderStatusChangeEvent.CANCEL, order)) {
            throw new RuntimeException(" "+ order.getState()+" -> 取消订单 失败，状态异常 order=" + order);
        }
        QueryWrapper<GoodsOrderGoods> wrapper = new QueryWrapper<>();
        wrapper.eq("order_id",id);
        List<GoodsOrderGoods> list = goodsOrderGoodsService.list(wrapper);
        for (GoodsOrderGoods temp :
                list) {
            int num = temp.getNum();
            UpdateWrapper<Goods> updateWrapper = new UpdateWrapper<>();
            Goods goods = goodsService.getById(temp.getGoodsId());
            updateWrapper.eq("id",goods.getId())
                    .set("stock",goods.getStock()+num)
                    .set("sales",goods.getSales()-num);
            goodsService.update(updateWrapper);
        }

        orderMapper.updateById(order);
        return order;
    }

    @Override
    public GoodsOrder receive(Long id) {
        GoodsOrder order = orderMapper.selectById(id);;
        if (!sendEvent(OrderStatusChangeEvent.RECEIVED, order)) {
            throw new BusinessException(3003," 等待收货 -> 等待评论 失败，状态异常 order=" + order);
        }
        order.setReceiveTime(new Date());
        orderMapper.updateById(order);

        return order;
    }

    @Override
    public GoodsOrder comment(Long id) {
        GoodsOrder order = orderMapper.selectById(id);;
        if (!sendEvent(OrderStatusChangeEvent.COMMENT, order)) {
            throw new BusinessException(3004," 等待评论 -> 已评论 失败，状态异常 order=" + order);
        }
        order.setCommentTime(new Date());
        orderMapper.updateById(order);
        redisTemplate.opsForValue().set(RedisAlias.COMMENT_ORDER + order.getId(),1,30, TimeUnit.MINUTES);
        return order;
    }

    @Override
    public GoodsOrder returning(Long id) {
        GoodsOrder order = orderMapper.selectById(id);;
        if (!sendEvent(OrderStatusChangeEvent.RETURN, order)) {
            throw new RuntimeException(" 已评论 -> 退货中等待仓库收货 失败，状态异常 order=" + order);
        }
        orderMapper.updateById(order);

        return order;
    }

    @Override
    public GoodsOrder returnFinish(Long id) {
        GoodsOrder order = orderMapper.selectById(id);;
        if (!sendEvent(OrderStatusChangeEvent.RETURNED, order)) {
            throw new RuntimeException(" 退货中等待仓库收货 -> 退货完成 失败，状态异常 order=" + order);
        }
        orderMapper.updateById(order);

        return order;
    }

    @Override
    public GoodsOrder finish(Long id) {
        GoodsOrder order = orderMapper.selectById(id);;
        if (!sendEvent(OrderStatusChangeEvent.FINISH, order)) {
            throw new RuntimeException(" 等待收货 -> 完成 失败，状态异常 order=" + order);
        }
        orderMapper.updateById(order);
        return order;
    }


    @Override
    public List<GoodsOrder> getOrders() {

        return orderMapper.selectList(new QueryWrapper<>());
    }

    /**
     * 发送订单状态转换事件
     *
     * @param event 事件
     * @param order 订单
     * @return 执行结果
     */
    private boolean sendEvent(OrderStatusChangeEvent event, GoodsOrder order) {
        boolean result = false;
        try {
            orderStateMachine.start();
            // 设置状态机状态
            persister.restore(orderStateMachine, order);
            result = orderStateMachine.sendEvent(MessageBuilder.withPayload(event).setHeader("order", order).build());
            // 保存状态机状态
            persister.persist(orderStateMachine, order);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            orderStateMachine.stop();
        }
        return result;
    }
}
