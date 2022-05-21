package com.jawe.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jawe.system.entity.User;
import com.jawe.system.mapper.UserMapper;
import com.jawe.system.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jawe
 * @since 2021-05-26
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public IPage<User> findUserPage(Page<User> page, QueryWrapper<User> queryWrapper) {
        return this.baseMapper.findUserPage(page,queryWrapper);
    }

    @Override
    public User currentUser(String username) {
        return this.baseMapper.currentUser(username);
    }

    @Override
    public User currentUserById(Integer id) {
        System.out.println(id);
        User user = this.baseMapper.selectById(id);
        return user;
    }

    @Override
    public Integer changeUserEnable(Integer userId) {
        return this.baseMapper.changeUserEnable(userId);
    }

    @Override
    public Integer updateUser(User user) {
        return this.baseMapper.updateById(user);
    }

    @Override
    public Integer addUser(User user) {
        return this.baseMapper.insert(user);
    }


}
