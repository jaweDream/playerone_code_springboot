package com.jawe.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jawe.system.entity.Goods;
import com.jawe.system.mapper.GoodsMapper;
import com.jawe.system.service.GoodsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jawe
 * @since 2021-10-24
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {


    @Override
    public IPage<Goods> findGoodsPage(Page<Goods> page, QueryWrapper<Goods> queryWrapper) {
        return this.baseMapper.findGoodsPage(page,queryWrapper);
    }

    @Override
    public Integer isOnGoods(Integer goodsId) {
        return this.baseMapper.isOnGoods(goodsId);
    }

    @Override
    public Integer desStock(Integer id, Integer num) {
        return this.baseMapper.decStock(id,num);
    }


}
