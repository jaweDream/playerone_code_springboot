package com.jawe.system.entity;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.jawe.state.enums.OrderStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Order对象", description="")
public class GoodsOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private Integer userId;

    @ApiModelProperty(value = "用户名")
    @TableField(exist = false)
    private String userName;

    private BigDecimal price;

    private int addressId;

    private OrderStatus state;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    private Date payTime;

    private Date deliverTime;

    private Date receiveTime;

    private Date commentTime;

    private Date finishTime;

    @Override
    public String toString() {
        return "订单号：" + id + ", 订单状态：" + state;
    }
}
