package com.jawe.system.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * <p>
 *
 * </p>
 *
 * @author jawe
 * @since 2021-10-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Goods对象", description="")
@ToString
public class Goods implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String goodsName;

    @TableField(exist = false)
    private GoodsCategory category;

    private Integer categoryId;

    private String description;

    private BigDecimal price;

    @TableField(exist = false)
    private BigDecimal curPrice;

    private Integer stock;

    private Integer sales;

    private String coverUrl;

    private Integer isOn;

    private Integer isRecommend;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @ApiModelProperty(value = "评论列表")
    @TableField(exist = false)
    private List<Comment> comments;

    @ApiModelProperty(value = "图集")
    @TableField(exist = false)
    private List<Inset> insets;

    @ApiModelProperty(value = "用户是否收藏该商品")
    @TableField(exist = false)
    private boolean isCollect;

    @ApiModelProperty(value = "商品评分")
    @TableField(exist = false)
    private Integer star;

    public boolean getIsCollect() {
        return isCollect;
    }

    public void setIsCollect(boolean isCollect) {
        this.isCollect = isCollect;
    }
}
