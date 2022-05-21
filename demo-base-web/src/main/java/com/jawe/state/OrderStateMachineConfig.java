package com.jawe.state;


import com.jawe.state.enums.OrderStatus;
import com.jawe.system.entity.GoodsOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.persist.DefaultStateMachinePersister;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.statemachine.transition.Transition;

import java.util.EnumSet;

/**
 * 订单状态机配置
 */
@Configuration
@EnableStateMachine(name = "orderStateMachine")
public class OrderStateMachineConfig extends StateMachineConfigurerAdapter<OrderStatus, OrderStatusChangeEvent> {
    // 配置状态
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     *
     * @param states
     * @throws Exception
     */
    @Override
    public void configure(StateMachineStateConfigurer<OrderStatus, OrderStatusChangeEvent> states) throws Exception {
        states.withStates()
                .initial(OrderStatus.WAIT_PAYMENT)
                .states(EnumSet.allOf(OrderStatus.class));
    }

    /**
     * 配置状态和事件之间的转换关系
     * @param transitions
     * @throws Exception
     */
    @Override
    public void configure(StateMachineTransitionConfigurer<OrderStatus, OrderStatusChangeEvent> transitions) throws Exception {
        transitions.withExternal().source(OrderStatus.WAIT_PAYMENT).target(OrderStatus.WAIT_DELIVER).event(OrderStatusChangeEvent.PAYED).and()
                .withExternal().source(OrderStatus.WAIT_DELIVER).target(OrderStatus.WAIT_RECEIVE).event(OrderStatusChangeEvent.DELIVERY).and()
                .withExternal().source(OrderStatus.WAIT_PAYMENT).target(OrderStatus.CANCEL_FINISH).event(OrderStatusChangeEvent.CANCEL).and()
                .withExternal().source(OrderStatus.WAIT_DELIVER).target(OrderStatus.CANCEL_FINISH).event(OrderStatusChangeEvent.CANCEL).and()
                .withExternal().source(OrderStatus.WAIT_RECEIVE).target(OrderStatus.WAIT_COMMENT).event(OrderStatusChangeEvent.RECEIVED).and()
                .withExternal().source(OrderStatus.WAIT_COMMENT).target(OrderStatus.COMMENTED).event(OrderStatusChangeEvent.COMMENT).and()
                .withExternal().source(OrderStatus.WAIT_COMMENT).target(OrderStatus.WAIT_RETURNING).event(OrderStatusChangeEvent.RETURN).and()
                .withExternal().source(OrderStatus.COMMENTED).target(OrderStatus.WAIT_RETURNING).event(OrderStatusChangeEvent.RETURN).and()
                .withExternal().source(OrderStatus.WAIT_RETURNING).target(OrderStatus.RETURN_FINISH).event(OrderStatusChangeEvent.RETURNED).and()
                .withExternal().source(OrderStatus.WAIT_RETURNING).target(OrderStatus.RETURN_FINISH).event(OrderStatusChangeEvent.RETURNED).and()
                .withExternal().source(OrderStatus.WAIT_COMMENT).target(OrderStatus.FINISH).event(OrderStatusChangeEvent.FINISH).and()
                .withExternal().source(OrderStatus.COMMENTED).target(OrderStatus.FINISH).event(OrderStatusChangeEvent.FINISH)





        ;
    }

    /**
     * 制定状态机的处理监听器
     * @param config
     * @throws Exception
     */
    @Override
    public void configure(StateMachineConfigurationConfigurer<OrderStatus, OrderStatusChangeEvent> config) throws Exception {
        config.withConfiguration().listener(listener());
    }

    /**
     * 可以持久化到redis里面 https://projects.spring.io/spring-statemachine/
     */
    /** 状态机持久化 */
    @Bean
    public StateMachinePersister<OrderStatus, OrderStatusChangeEvent, GoodsOrder> orderStateMachinePersister() {
        return new DefaultStateMachinePersister<>(new StateMachinePersist<OrderStatus, OrderStatusChangeEvent, GoodsOrder>() {
            @Override
            public void write(StateMachineContext<OrderStatus, OrderStatusChangeEvent> context, GoodsOrder order) {
                // 进行持久化操作
                order.setState(context.getState());
            }

            @Override
            public StateMachineContext<OrderStatus, OrderStatusChangeEvent> read(GoodsOrder order) {
                // 读取状态并设置到context中
                return new DefaultStateMachineContext<>(order.getState(), null, null, null);
            }
        });
    }

    /**
     * 配置listener
     * @return
     */
    @Bean
    public StateMachineListener<OrderStatus,OrderStatusChangeEvent> listener(){
        return new StateMachineListenerAdapter<OrderStatus,OrderStatusChangeEvent>(){
            @Override
            public void transition(Transition<OrderStatus, OrderStatusChangeEvent> transition) {
                if(transition.getTarget().getId()==OrderStatus.WAIT_PAYMENT){
                    logger.info("用户订单创建，待支付");
                    return;
                }
                if(transition.getSource().getId()==OrderStatus.WAIT_PAYMENT && transition.getTarget().getId()==OrderStatus.WAIT_DELIVER){
                    logger.info("用户已支付，待发货");

                    return;
                }
                if (transition.getSource().getId()==OrderStatus.WAIT_DELIVER && transition.getTarget().getId()==OrderStatus.WAIT_RECEIVE){
                    logger.info("商家已发货，待接收");
                    return;
                }
                if (transition.getSource().getId()==OrderStatus.WAIT_RECEIVE && transition.getTarget().getId()==OrderStatus.FINISH){
                    logger.info("用户已收货，订单完成");
                    return;
                }
            }
        };
    }
}
