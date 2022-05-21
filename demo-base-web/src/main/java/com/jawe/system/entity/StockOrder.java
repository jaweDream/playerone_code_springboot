package com.jawe.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
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
 * @since 2021-10-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="StockOrder对象", description="")
public class StockOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "库存id")
    private Integer sid;

    @ApiModelProperty(value = "商品名称")
    private String name;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;


}
