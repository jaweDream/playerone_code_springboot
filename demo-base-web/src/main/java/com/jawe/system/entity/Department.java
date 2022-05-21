package com.jawe.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * @since 2021-05-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Department对象", description="")
public class Department implements Serializable {

//    private static final long serialVersionUID = 1L;
//
//    @TableId(value = "id", type = IdType.AUTO)
//    private Integer id;
//
//    private String name;
//
//    @ApiModelProperty(value = "分组查询中的部门人数")
//    @TableField(exist = false)
//    private Integer deptCount;

}
