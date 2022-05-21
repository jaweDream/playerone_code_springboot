package com.jawe.system.service.impl;

import com.jawe.system.entity.Cart;
import com.jawe.system.mapper.CartMapper;
import com.jawe.system.service.CartService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.swagger.models.auth.In;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jawe
 * @since 2021-11-18
 */
@Service
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart> implements CartService {

    public int delGoods(int userId, ArrayList<Integer> ids) {
        return this.baseMapper.delCart(userId,ids);
    }

}
