package com.jawe.system.service;

import com.jawe.system.entity.Cart;
import com.baomidou.mybatisplus.extension.service.IService;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jawe
 * @since 2021-11-18
 */
public interface CartService extends IService<Cart> {

    int delGoods(int userId, ArrayList<Integer> ids);
}
