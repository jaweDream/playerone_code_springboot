package com.jawe.system.service;

import com.jawe.system.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jawe
 * @since 2021-09-12
 */
public interface MenuService extends IService<Menu> {


    public List<Menu> queryMenuTree();
}
