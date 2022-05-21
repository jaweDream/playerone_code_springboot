package com.jawe.state;


import com.jawe.state.enums.OrderStatus;
import com.jawe.system.entity.GoodsOrder;
import org.springframework.messaging.Message;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;
import org.springframework.stereotype.Component;

@Component("orderStateListener")
@WithStateMachine(name = "orderStateMachine")
public class OrderStateListenerImpl{

    @OnTransition(source = "WAIT_PAYMENT", target = "WAIT_DELIVER")
    public boolean payTransition(Message<OrderStatusChangeEvent> message) {
        GoodsOrder order = (GoodsOrder) message.getHeaders().get("order");
        order.setState(OrderStatus.WAIT_DELIVER);
        System.out.println("支付，状态机反馈信息：" + message.getHeaders().toString());
        return true;
    }

    @OnTransition(source = {"WAIT_DELIVER","WAIT_PAYMENT"}, target = "CANCEL_FINISH")
    public boolean cancelTransition(Message<OrderStatusChangeEvent> message) {
        GoodsOrder order = (GoodsOrder) message.getHeaders().get("order");
        order.setState(OrderStatus.CANCEL_FINISH);
        System.out.println("取消订单，状态机反馈信息：" + message.getHeaders().toString());
        return true;
    }

    @OnTransition(source = "WAIT_DELIVER", target = "WAIT_RECEIVE")
    public boolean deliverTransition(Message<OrderStatusChangeEvent> message) {
        GoodsOrder order = (GoodsOrder) message.getHeaders().get("order");
        order.setState(OrderStatus.WAIT_RECEIVE);
        System.out.println("发货，状态机反馈信息：" + message.getHeaders().toString());
        return true;
    }


    @OnTransition(source = "WAIT_RECEIVE", target = "WAIT_COMMENT")
    public boolean receiveTransition(Message<OrderStatusChangeEvent> message){
        GoodsOrder order = (GoodsOrder) message.getHeaders().get("order");
        order.setState(OrderStatus.WAIT_COMMENT);
        System.out.println("收货，状态机反馈信息：" + message.getHeaders().toString());
        return true;
    }

    @OnTransition(source = "WAIT_COMMENT", target = "COMMENT")
    public boolean commentTransition(Message<OrderStatusChangeEvent> message){
        // 7天内才能操作
        GoodsOrder order = (GoodsOrder) message.getHeaders().get("order");
        order.setState(OrderStatus.WAIT_COMMENT);
        System.out.println("退货申请，状态机反馈信息：" + message.getHeaders().toString());
        return true;
    }

    @OnTransition(source = {"WAIT_COMMENT","COMMENT"}, target = "WAIT_RETURN")
    public boolean returnTransition(Message<OrderStatusChangeEvent> message){
        // 7天内才能操作
        GoodsOrder order = (GoodsOrder) message.getHeaders().get("order");
        order.setState(OrderStatus.WAIT_COMMENT);
        System.out.println("退货申请，状态机反馈信息：" + message.getHeaders().toString());
        return true;
    }

    @OnTransition(source = "WAIT_RETURN", target = "WAIT_RETURNING")
    public boolean returningTransition(Message<OrderStatusChangeEvent> message){
        GoodsOrder order = (GoodsOrder) message.getHeaders().get("order");
        order.setState(OrderStatus.WAIT_COMMENT);
        System.out.println("退货中等待仓库收货，状态机反馈信息：" + message.getHeaders().toString());
        return true;
    }

    @OnTransition(source = "WAIT_RETURNING", target = "RETURN_FINISH")
    public boolean returnFinishTransition(Message<OrderStatusChangeEvent> message){
        GoodsOrder order = (GoodsOrder) message.getHeaders().get("order");
        order.setState(OrderStatus.WAIT_COMMENT);
        System.out.println("退货成功，状态机反馈信息：" + message.getHeaders().toString());
        return true;
    }

    @OnTransition(source = "WAIT_COMMENT,COMMENT", target = "FINISH")
    public boolean finishTransition(Message<OrderStatusChangeEvent> message){
        // 7天后自动变为完成状态
        GoodsOrder order = (GoodsOrder) message.getHeaders().get("order");
        order.setState(OrderStatus.WAIT_COMMENT);
        System.out.println("交易完成，状态机反馈信息：" + message.getHeaders().toString());
        return true;
    }

}
