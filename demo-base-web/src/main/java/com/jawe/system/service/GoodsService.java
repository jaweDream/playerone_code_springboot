package com.jawe.system.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jawe.system.entity.Goods;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jawe.system.entity.User;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jawe
 * @since 2021-10-24
 */
public interface GoodsService extends IService<Goods> {
    IPage<Goods> findGoodsPage(Page<Goods> page, QueryWrapper<Goods> queryWrapper);

    Integer isOnGoods(Integer goodsId);

    Integer desStock(Integer id,Integer num);

}
