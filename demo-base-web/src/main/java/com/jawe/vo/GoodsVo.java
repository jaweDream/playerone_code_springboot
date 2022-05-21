package com.jawe.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class GoodsVo {
    private String goodsName;
    private BigDecimal price;
    private Integer stock;
    private Integer sales;
}
