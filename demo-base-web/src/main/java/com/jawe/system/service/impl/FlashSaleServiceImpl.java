package com.jawe.system.service.impl;

import com.jawe.system.entity.FlashSale;
import com.jawe.system.mapper.FlashSaleMapper;
import com.jawe.system.service.FlashSaleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jawe
 * @since 2022-05-10
 */
@Service
public class FlashSaleServiceImpl extends ServiceImpl<FlashSaleMapper, FlashSale> implements FlashSaleService {

    @Override
    public List<FlashSale> flashGoodsList() {

        return null;
    }
}
