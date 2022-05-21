package com.jawe.system.service;

import com.jawe.system.entity.Stock;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jawe.system.entity.StockOrder;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jawe
 * @since 2021-10-04
 */
public interface StockService extends IService<Stock> {
    public StockOrder kill(Integer id);
}
