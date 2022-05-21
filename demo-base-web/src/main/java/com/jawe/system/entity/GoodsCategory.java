package com.jawe.system.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
 * @since 2021-10-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "GoodsCategory对象", description = "")
public class GoodsCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "父节点")
    private Integer pid;

    private String name;

    private Integer sort;

    private Integer enable;

    @TableField(exist = false)
    private List<Cart> carts;

    @TableField(exist = false)
    private Integer isActiveAll;


    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @TableField(exist = false)
    private List<GoodsCategory> children;

}
