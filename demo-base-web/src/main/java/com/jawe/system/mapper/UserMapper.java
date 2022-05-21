package com.jawe.system.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jawe.system.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.yaml.snakeyaml.scanner.Constant;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jawe
 * @since 2021-05-26
 */
public interface UserMapper extends BaseMapper<User> {
    IPage<User> findUserPage(Page<User> page,@Param(Constants.WRAPPER) QueryWrapper<User> queryWrapper);

    @Select("SELECT name,token,enable,phone_number\n" +
            " from user u \n" +
            " where u.phone_number = #{phoneNumber};")
    List<String> findByPhoneNumber(@Param("phoneNumber") String phoneNumber);

    User currentUser(@Param("username") String username);

    Integer changeUserEnable(@Param("userId")Integer userId);



}
