package com.jawe.test;

import com.jawe.system.entity.GoodsOrder;
import com.jawe.system.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class stateDemo {

    @Autowired
    private OrderService orderService;

    @Test
    public void testCancel1(){
        GoodsOrder goodsOrder = new GoodsOrder();
        goodsOrder.setUserId(1);
        goodsOrder.setPrice(new BigDecimal(100));
        orderService.create(goodsOrder);

//        orderService.cancel(order.getId());
//        assertTrue(OrderStatus.CANCEL_FINISH == order.getStatus());
    }
//
//    @Test
//    public void testCancel2(){
//        Order order = orderService.create();
//        orderService.pay(order.getId());
//        orderService.cancel(order.getId());
//        assertTrue(OrderStatus.CANCEL_FINISH == order.getStatus());
//    }

//    @Test
//    public void testSuccess(){
//        Order order = orderService.create();
//        orderService.pay(order.getId());
//        orderService.deliver(order.getId());
//        orderService.receive(order.getId());
//        assertTrue(OrderStatus.FINISH == order.getStatus());
//    }
//
//    @Test
//    public void testError(){
//        Order order = orderService.create();
////        orderService.pay(order.getId());
//        // 少这一步 测试一下出现意外的状况
//        orderService.deliver(order.getId());
//        orderService.receive(order.getId());
//        assertTrue(OrderStatus.FINISH == order.getStatus());
//    }
}
