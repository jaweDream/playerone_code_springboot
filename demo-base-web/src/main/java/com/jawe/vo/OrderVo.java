package com.jawe.vo;

import com.jawe.state.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderVo {

    private Long id;

    private Date startTime;

    private Date endTime;

    private OrderStatus state;

}
