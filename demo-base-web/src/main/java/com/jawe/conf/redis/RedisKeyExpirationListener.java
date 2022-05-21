package com.jawe.conf.redis;

import com.jawe.system.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener {

    @Autowired
    private OrderService orderService;


    public RedisKeyExpirationListener(RedisMessageListenerContainer container) {
        super(container);
    }

//    /**
//     * 针对redis数据失效事件，进行数据处理
//     * @param message
//     * @param pattern
//     */
    @Override
    public void onMessage(Message message, byte[] pattern) {
        String key=message.toString();//生效的key
        if (key==null)return;
        //订单（state==1） 30分钟后取消
        String orderNo=key.substring(10);
        if (key.startsWith(RedisAlias.CREATE_ORDER)||key.startsWith(RedisAlias.CREATE_FLASH_ORDER)){//从失效key中筛选代表订单失效的key
            //截取订单号，查询订单，如果是未支付状态则取消订单
            System.out.println("++++++++++++++++++"+orderNo);
            //修改订单状态
            orderService.cancel(Long.parseLong(orderNo));
            System.out.println("订单号为："+orderNo+"的订单超时未支付，取消订单");
        }else if (key.startsWith(RedisAlias.COMMENT_ORDER)){
            orderService.comment(Long.parseLong(orderNo));
        }


    }
}
