package com.jawe.state;

public enum OrderStatusChangeEvent {
    PAYED,  // 支付
    DELIVERY, // 发货
    CANCEL, // 取消
    RECEIVED, // 收货
    COMMENT, // 评价
    RETURN, // 退货
    RETURNED, // 后台收货
    FINISH;

}
