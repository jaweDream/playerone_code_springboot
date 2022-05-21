package com.jawe.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jawe.conf.jwt.JwtTokenUtil;
import com.jawe.handler.BusinessException;
import com.jawe.response.Result;
import com.jawe.response.ResultCode;
import com.jawe.system.entity.User;
import com.jawe.system.service.UserService;
import com.jawe.vo.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/api/user")
@Api(value = "系统用户模块", tags = "用户信息")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;
//
//    @GetMapping("/findUserList")
//    @ApiOperation(value="用户信息列表",notes = "查询所有用户")
//    public Result findUserList(){
//        List<User> userBaseList = userService.list();
//        return Result.ok().data("userBases",userBaseList);
//    }

    /*
     * 登录获取jwt
     * 用jwt获取用户信息
     * */
    @GetMapping("/findUserByJWT")
    @ApiOperation(value = "获取用户信息", notes = "通过jwt获取用户信息")
    public Result currentUser(@RequestHeader("JWTHeaderName") String jwt) {

        if (!jwtTokenUtil.validateToken(jwt)) {
            return Result.error().message("登录过期，请重新登录");
        }

        User user = userService.currentUser(jwtTokenUtil.getUsernameFromToken(jwt));
        return Result.ok().data("user", user);
    }


    @PostMapping("/findUserList")
    @ApiOperation(value = "分页用户信息 by UserVo", notes = "分页查询用户")
    public Result findUserList(@RequestParam(defaultValue = "1") Integer current,
                               @RequestParam(defaultValue = "7") Integer pageSize,
                               @RequestBody(required = false) UserVo userVo
                               ) {

        Page<User> page = new Page<>(current, pageSize);
        System.out.println("当前页数"+current);

        QueryWrapper<User> queryWrapper = getMyQueryWrapper(userVo);

        IPage<User> userPage = userService.findUserPage(page, queryWrapper);
        long total = userPage.getTotal();
        List<User> records = userPage.getRecords();

        return Result.ok().data("total", total).data("records", records);

    }

    /*
     * 管理员对用户的修改、添加、删除操作
     * /api/user
     * */
    @ApiOperation(value = "获取用户信息", notes = "获取用户信息/api/user/{userId}")
    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public Result getUserInfo(@PathVariable("userId") Integer userId) {

        User user = userService.getById(userId);
        if (user != null) {
            return Result.ok().data("user", user);
        } else {
            throw new BusinessException(ResultCode.USER_ACCOUNT_NOT_EXIST.getCode(), ResultCode.USER_ACCOUNT_NOT_EXIST.getMessage());
        }
    }

    @ApiOperation(value = "修改用户是否可用", notes = "修改用户是否可用/api/user/{userId}")
    @RequestMapping(value = "/{userId}", method = RequestMethod.PUT)
    public Result changeUserEnable(@PathVariable("userId") Integer userId) {
        int res = this.userService.changeUserEnable(userId);
        System.out.println(res);
        if (res != 0) {
            return Result.ok();
        } else {
            return Result.error();
        }
    }

    @PostMapping("")
    @ApiOperation(value = "添加用户", notes = "添加单个用户/api/user")
    public Result addUser(@RequestBody User user) {
        if (userService.save(user)) {
            return Result.ok().data("user", user);
        } else {
            throw new BusinessException(ResultCode.USER_ACCOUNT_ALREADY_EXIST.getCode(), ResultCode.USER_ACCOUNT_ALREADY_EXIST.getMessage());
        }
    }

    @ApiOperation(value = "修改用户", notes = "修改用户/api/user")
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public Result updateUser(@RequestBody User user) {
        if (userService.updateUser(user) != 0) {
            return Result.ok().message("修改成功");
        } else {
            throw new BusinessException(ResultCode.USER_ACCOUNT_ALREADY_EXIST.getCode(), ResultCode.USER_ACCOUNT_ALREADY_EXIST.getMessage());
        }
    }


//    @RequestMapping("/tokenAvalible")
//    @ApiOperation(value = "验证token",notes ="验证token" )
//    public Result tokenAvalible(){
//
//
//        return Result.ok();
//    }

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
                queryWrapper.orderBy(true, userVo.getCreateTime().equals("descend")?false:true,"create_time");
            }
        }
        return queryWrapper;
    }

}

