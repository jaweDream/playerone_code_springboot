package com.jawe.system.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author jawe
 * @since 2022-05-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="GoodsOrderGoods对象", description="")
public class GoodsOrderGoods implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Long orderId;

    private Integer num;

    private Integer goodsId;

    private BigDecimal curPrice;

    private String title;

    private String picUrl;

    private Integer isCancel;


}
