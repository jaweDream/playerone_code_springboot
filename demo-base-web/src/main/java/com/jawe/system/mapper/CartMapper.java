package com.jawe.system.mapper;

import com.jawe.system.entity.Cart;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jawe.system.entity.MyUserDetails;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jawe
 * @since 2021-11-18
 */
public interface CartMapper extends BaseMapper<Cart> {

    @Update("update cart c set num=num+1 \n" +
            " where c.id = #{id};")
    Integer addNum (@Param("id")Integer id);

//    @Update("update cart c set is_active=abs(is_active-1) \n" +
//            " where c.user_id = #{userId} and c.goods_id = #{goodsId};")
//    Integer onActive (@Param("userId")Integer userId,@Param("goodsId")Integer goodsId);

    @Update("update cart c set is_active=abs(is_active-1) \n" +
            " where c.id = #{id};")
    Integer onActive (@Param("id")Integer id);

    Integer delCart(@Param("userId")Integer userId,@Param("ids") List<Integer> ids);
}
