package com.jawe.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author jawe
 * @since 2021-11-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Inset对象", description="")
public class Inset implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer goodsId;

    private String url;

    private Integer sort;

    public Inset(Integer id, Integer goodsId, String url, Integer sort) {
        this.id = id;
        this.goodsId = goodsId;
        this.url = "https://player-one.oss-cn-beijing.aliyuncs.com"+url;
        this.sort = sort;
    }

    public void setUrl(String url) {
        this.url = "https://player-one.oss-cn-beijing.aliyuncs.com"+url;
    }
}
