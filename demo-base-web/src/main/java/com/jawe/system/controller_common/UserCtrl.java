package com.jawe.system.controller_common;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jawe.conf.jwt.JwtTokenUtil;
import com.jawe.handler.BusinessException;
import com.jawe.response.Result;
import com.jawe.response.ResultCode;
import com.jawe.system.entity.AliOssEntity;
import com.jawe.system.entity.User;
import com.jawe.system.service.UserService;
import com.jawe.vo.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author jawe
 * @since 2021-05-21
 */
@RestController
@CrossOrigin
@RequestMapping("/api/common/user")
@Api(value = "前台-用户-api", tags = "前台-用户")
public class UserCtrl {

    @Autowired
    private UserService userService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;


    /*
     * 登录获取jwt
     * 用jwt获取用户信息
     * */
    @GetMapping("")
    @ApiOperation(value = "获取用户信息", notes = "通过jwt获取用户信息")
    public Result userInfo(@RequestHeader("JWTHeaderName") String jwt) {
        if (!jwtTokenUtil.validateToken(jwt)) {
            return Result.error().message("登录过期，请重新登录");
        }
        String username = jwtTokenUtil.getUsernameFromToken(jwt);
        User user = userService.currentUser(username);
        return Result.ok().data("user", user);
    }

    @PostMapping("")
    @ApiOperation(value = "注册用户", notes = "添加单个用户/api/user")
    public Result addUser(@RequestBody User user) {
        if (userService.save(user)) {
            return Result.ok().data("user", user);
        } else {
            throw new BusinessException(ResultCode.USER_ACCOUNT_ALREADY_EXIST.getCode(), ResultCode.USER_ACCOUNT_ALREADY_EXIST.getMessage());
        }
    }

    @ApiOperation(value = "修改个人信息", notes = "修改个人信息")
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public Result updateUser(@RequestBody User user) {
        try {
            userService.updateById(user);
            return Result.ok().message("修改成功");
        } catch (Exception e) {
            throw new BusinessException(ResultCode.USER_ACCOUNT_ALREADY_EXIST.getCode(), "用户名已存在");
        }
    }


    @PostMapping("/findUserList")
    @ApiOperation(value = "分页用户信息 by UserVo", notes = "分页查询用户")
    public Result findUserList(@RequestParam(defaultValue = "1") Integer current,
                               @RequestParam(defaultValue = "7") Integer pageSize,
                               @RequestBody(required = false) UserVo userVo
    ) {

        Page<User> page = new Page<>(current, pageSize);
        System.out.println("当前页数" + current);

        QueryWrapper<User> queryWrapper = getMyQueryWrapper(userVo);

        IPage<User> userPage = userService.findUserPage(page, queryWrapper);
        long total = userPage.getTotal();
        List<User> records = userPage.getRecords();

        return Result.ok().data("total", total).data("records", records);

    }

    private UpdateWrapper<User> getMyUpdateWrapper(UserVo userVo) {
        UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();
        if (userVo != null) {
            if (!StringUtils.isEmpty(userVo.getUsername())) {
                userUpdateWrapper.eq("username", userVo.getUsername());
            }
            if (!StringUtils.isEmpty(userVo.getPassword())) {
                userUpdateWrapper.eq("password", userVo.getPassword());
            }
            if (!StringUtils.isEmpty(userVo.getGender())) {
                userUpdateWrapper.eq("gender", userVo.getGender());
            }
            if (!StringUtils.isEmpty(userVo.getEmail())) {
                userUpdateWrapper.eq("email", userVo.getEmail());
            }
        }

        return userUpdateWrapper;
    }


    private QueryWrapper<User> getMyQueryWrapper(UserVo userVo) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (userVo != null) {
            if (!StringUtils.isEmpty(userVo.getUsername())) {
                queryWrapper.like("username", userVo.getUsername());
            }
            if (!StringUtils.isEmpty(userVo.getGender())) {
                queryWrapper.eq("gender", userVo.getGender());
            }
            if (!StringUtils.isEmpty(userVo.getEmail())) {
                queryWrapper.like("email", userVo.getEmail());
            }
            if (!StringUtils.isEmpty(userVo.getCreateTime())) {
                queryWrapper.orderBy(true, userVo.getCreateTime().equals("descend") ? false : true, "create_time");
            }
        }
        return queryWrapper;
    }

}

