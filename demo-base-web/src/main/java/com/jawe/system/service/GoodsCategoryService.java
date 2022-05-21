package com.jawe.system.service;

import com.jawe.system.entity.GoodsCategory;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jawe
 * @since 2021-10-28
 */
public interface GoodsCategoryService extends IService<GoodsCategory> {

    List<GoodsCategory> getTreeList(boolean isEnable);

    List<Integer> getArray(int categoryId);

    int isEnable(int id);
}
