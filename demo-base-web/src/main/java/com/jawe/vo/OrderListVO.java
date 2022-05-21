package com.jawe.vo;


import com.jawe.state.enums.OrderStatus;
import com.jawe.system.entity.GoodsOrderGoods;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
public class OrderListVO {
    private String id;
    private String store;
    private String deal;

    public OrderListVO(String id,  Integer state, List<GoodsOrderGoods> goodsOrderGoods) {
        this.id = id;
        this.store = "官方旗舰店";
        this.deal = OrderStatus.getByCode(state);
        this.goodsOrderGoods = goodsOrderGoods;
    }

    private List<GoodsOrderGoods> goodsOrderGoods;

}


