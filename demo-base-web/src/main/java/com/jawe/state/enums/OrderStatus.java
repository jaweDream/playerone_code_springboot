package com.jawe.state.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.Getter;


@Getter
public enum OrderStatus {
    WAIT_PAYMENT("待付款",1), // 待付款
    WAIT_DELIVER("待发货",2), // 待发货
    CANCEL_FINISH("已取消",3), // 1,2
    WAIT_RECEIVE("待收货",4), // 待收货
    WAIT_COMMENT("待评论",5), // 待评论
    COMMENTED("已评论可退货",6),// 3,4 能到 5，收货时间7天内才能进入这个状态，申请并且后台确定后3天内没有发货或已发货则变为已完成状态
    WAIT_RETURNING("待仓库收货",7),// 后台仓库确认收获后
    RETURN_FINISH("退货完成",8),
    FINISH("交易完成",9);// 103，201，202 能到达



    OrderStatus(String desc,Integer code) {
        this.desc = desc;
        this.code = code;
    }

    public static String getByCode(Integer code){
        for(OrderStatus orderStatus: OrderStatus.values()){
            if(code.equals(orderStatus.getCode())){
                return orderStatus.getDesc();
            }
        }
        return  null;
    }

    private final String desc;

    @EnumValue
    private final Integer code;
}

