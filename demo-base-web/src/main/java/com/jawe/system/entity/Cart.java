package com.jawe.system.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
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
 * @since 2021-11-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Cart对象", description="")
public class Cart implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer goodsId;

    private Integer num;

    private BigDecimal price;

    private Integer userId;

    private Integer isActive;

    private Integer categoryId;

    @ApiModelProperty(value = "分类（游戏）名称")
    @TableField(exist = false)
    private String categoryName;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "商品信息")
    @TableField(exist = false)
    private Goods goods;


}
