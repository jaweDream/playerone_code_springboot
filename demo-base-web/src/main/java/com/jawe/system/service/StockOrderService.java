package com.jawe.system.service;

import com.jawe.system.entity.StockOrder;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author jawe
 * @since 2021-10-04
 */
public interface StockOrderService extends IService<StockOrder> {

    public int kill(Integer id);

    public String getRole(Integer storeId, Integer userId);
}
