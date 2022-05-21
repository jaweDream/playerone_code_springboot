package com.jawe.system.service;

import com.jawe.system.entity.FlashSale;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jawe
 * @since 2022-05-10
 */
public interface FlashSaleService extends IService<FlashSale> {

    public List<FlashSale> flashGoodsList();
}
