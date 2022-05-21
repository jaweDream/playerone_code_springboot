package com.jawe.system.service.impl;

import com.jawe.system.entity.Stock;
import com.jawe.system.entity.StockOrder;
import com.jawe.system.mapper.StockMapper;
import com.jawe.system.service.StockService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jawe
 * @since 2021-10-04
 */
@Service
public class StockServiceImpl extends ServiceImpl<StockMapper, Stock> implements StockService {



    @Override
    public StockOrder kill(Integer id) {
        //1.验证库存
        Stock stock = this.baseMapper.selectById(id);
        if (stock.getCount()<1){
            return null;
        }

        StockOrder result = new StockOrder();
        result.setSid(stock.getId());
        result.setName(stock.getName());

        return result;
    }
}
