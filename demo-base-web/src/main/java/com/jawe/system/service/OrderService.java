package com.jawe.system.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jawe.system.entity.Department;
import com.jawe.system.entity.Goods;
import com.jawe.system.entity.GoodsOrder;
import com.jawe.system.entity.Role;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


public interface OrderService  extends IService<GoodsOrder> {
    IPage<GoodsOrder> findOrderPage(Page<GoodsOrder> page, QueryWrapper<GoodsOrder> queryWrapper);
    //创建新订单
    Integer create(GoodsOrder order);
    //发起支付
    GoodsOrder pay(Long id);
    //订单发货
    GoodsOrder deliver(Long id);
    //取消订单
    GoodsOrder cancel(Long id);
    //订单收货
    GoodsOrder receive(Long id);
    //订单评论
    GoodsOrder comment(Long id);
    //订单退货
    GoodsOrder returning(Long id);
    //退货完成
    GoodsOrder returnFinish(Long id);
    //交易完成
    GoodsOrder finish(Long id);
    //获取所有订单信息
    List<GoodsOrder> getOrders();
}
