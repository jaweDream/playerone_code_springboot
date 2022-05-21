package com.jawe.system.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;

import java.time.LocalDateTime;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
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
 * @since 2022-05-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="FlashSale对象", description="")
public class FlashSale implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer goodsId;

    private LocalDateTime startTime;

    @TableField(exist = false)
    private Goods goods;

    @TableField(exist = false)
    //0:未开始、1：进行中、2：已结束
    private int state;

    @TableField(exist = false)
    //state==0：距离开始还剩time分钟
    //state==1：距离结束还剩time分钟
    private int time;

    @TableField(exist = false)
    //state==0：距离开始还剩time分钟
    //state==1：距离结束还剩time分钟
    private String startTimeStr;

    private LocalDateTime endTime;

    private BigDecimal curPrice;

    private Integer stock;

    private Integer sale;


    @TableField(exist = false)
    private String goodsName;

    @TableField(exist = false)
    private String coverUrl;

    @TableField(exist = false)
    private BigDecimal price;

    private LocalDateTime createTime;

}
