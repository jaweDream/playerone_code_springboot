package com.jawe.system.entity;

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
 * @since 2021-11-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="GoodsCollection对象", description="")
public class GoodsCollection implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer goodsId;

    private Integer userId;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    private Integer isDelete;


}
