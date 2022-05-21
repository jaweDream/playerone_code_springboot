package com.jawe.system.mapper;

import com.jawe.system.entity.Stock;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jawe
 * @since 2021-10-04
 */
@Mapper
public interface StockMapper extends BaseMapper<Stock> {

    public int updateSale(Stock stock);
}
