package com.jawe.system.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jawe.system.entity.Goods;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jawe.system.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jawe
 * @since 2021-10-24
 */
public interface GoodsMapper extends BaseMapper<Goods> {
    IPage<Goods> findGoodsPage(Page<Goods> page, @Param(Constants.WRAPPER) QueryWrapper<Goods> queryWrapper);

    Integer isOnGoods(@Param("goodsId")Integer goodsId);



    Integer decStock(@Param("goodsId")Integer goodsId,@Param("num")Integer num);
}
