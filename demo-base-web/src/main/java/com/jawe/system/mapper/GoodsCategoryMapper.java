package com.jawe.system.mapper;

import com.jawe.system.entity.GoodsCategory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jawe
 * @since 2021-10-28
 */
public interface GoodsCategoryMapper extends BaseMapper<GoodsCategory> {

    @Update("update goods_category set enable=ABS(enable-1) where id = #{id}")
    Integer isEnable(@Param("id")Integer id);

    @Select("select name from goods_category where id=#{id}")
    String getName(@Param("id")Integer id);
}
