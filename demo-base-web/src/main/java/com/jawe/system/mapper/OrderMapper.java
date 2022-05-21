package com.jawe.system.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jawe.system.entity.Goods;
import com.jawe.system.entity.GoodsOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jawe
 * @since 2021-12-11
 */
public interface OrderMapper extends BaseMapper<GoodsOrder> {

    IPage<GoodsOrder> findOrderPage(Page<GoodsOrder> page, @Param(Constants.WRAPPER) QueryWrapper<GoodsOrder> queryWrapper);


}
