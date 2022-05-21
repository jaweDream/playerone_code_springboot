package com.jawe.system.service.impl;

import com.jawe.system.entity.Stock;
import com.jawe.system.entity.StockOrder;
import com.jawe.system.mapper.StockMapper;
import com.jawe.system.mapper.StockOrderMapper;
import com.jawe.system.service.StockOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jawe.system.service.StockService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author jawe
 * @since 2021-10-04
 */
@Service
@Transactional

public class StockOrderServiceImpl extends ServiceImpl<StockOrderMapper, StockOrder>
        implements StockOrderService {

    @Autowired
    private StockMapper stockMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @Override
    public int kill(Integer id) {
        if (!stringRedisTemplate.hasKey("kill" + id)) {
            throw new RuntimeException("抢购未开始或已结束");
        }
        Stock stock = checkStock(id);
        updateSale(stock);
        return createOrder(stock);
    }

    @Override
    public String getRole(Integer storeId, Integer userId) {
        String md5;
        try{
//            md5 = stockService.getMd5();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    //校验库存
    private Stock checkStock(Integer id) {

        Stock stock = stockMapper.selectById(id);
        if (stock.getSale().equals(stock.getCount())) {
            throw new RuntimeException("库存不足");
        }
        return stock;
    }

    //扣库存
    public void updateSale(Stock stock) {
        int updateSale = stockMapper.updateSale(stock);
        if (updateSale == 0) {
            throw new RuntimeException("版本不对，你没有抢到");
        }
    }

    //生成购买记录（创建订单）
    private Integer createOrder(Stock stock) {
        StockOrder stockOrder = new StockOrder();
        stockOrder.setSid(stock.getId());
        stockOrder.setName(stock.getName());
        stockOrder.setCreateTime(new Date());
        if (this.baseMapper.insert(stockOrder) != 1) {
            throw new RuntimeException("错误");
        }
        return stock.getId();
    }


}
