package com.jawe.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.ArrayList;
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
 * @since 2021-09-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Menu对象", description="")
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "父菜单id")
    private Integer menuPid;

    @ApiModelProperty(value = "当前菜单所有父菜单")
    private String menuPids;

    @ApiModelProperty(value = "0:不是叶子节点 1是")
    private Boolean isLeaf;

    private String menuName;

    private String url;

    private String icon;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "菜单等级")
    private Integer level;

    @ApiModelProperty(value = "0启用1禁用")
    private Boolean status;

    @ApiModelProperty(value = "子菜单")
    @TableField(exist = false)
    private List<Menu> children = new ArrayList<>();

}
