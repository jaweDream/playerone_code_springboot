package com.jawe.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * @since 2021-11-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Address对象", description="")
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer userId;

    private String mobile;

    private String address;

    private String detail;

    private String name;

    private String tag;

    @ApiModelProperty(value = "标签列表")
    @TableField(exist = false)
    private List<String> tagList;

    private Integer isDefault;


}
