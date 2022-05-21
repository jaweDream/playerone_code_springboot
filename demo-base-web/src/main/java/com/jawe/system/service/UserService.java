package com.jawe.system.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jawe.system.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author jawe
 * @since 2021-05-26
 */
public interface UserService extends IService<User> {
    IPage<User> findUserPage(Page<User> page, QueryWrapper<User> queryWrapper);

    User currentUser(String username);

    User currentUserById(Integer id);

    Integer changeUserEnable(Integer userId);

    Integer addUser(User user);

    Integer updateUser(User user);
}
