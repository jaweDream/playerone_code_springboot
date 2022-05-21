package com.jawe.system.controller_common;

import com.jawe.conf.jwt.JwtAuthService;
import com.jawe.conf.jwt.JwtTokenUtil;
import com.jawe.handler.BusinessException;
import com.jawe.response.Result;
import com.jawe.response.ResultCode;
import com.jawe.system.entity.User;
import com.jawe.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/common/auth")
@Api("前台-授权")
public class JwtAuthCtrl {

    @Resource
    JwtAuthService jwtAuthService;
    @Resource
    UserService userService;
    @Resource
    JwtTokenUtil jwtTokenUtil;

    // 获取token
    @PostMapping(value = "/login")
    public Result login(@RequestBody Map<String, String> map) {
        String username = map.get("username");
        String password = map.get("password");

        if (StringUtils.isEmpty(username)
                || StringUtils.isEmpty(password)) {
            return Result.error().code(403).message("用户名和密码不能为空");
        }
        try {
            //正确  1.设置返回数据   2.保存jwt到redis和mysql中
            HashMap<String, Object> data = new HashMap<>();
            data.put("jwt", jwtAuthService.login(username, password));
            return Result.ok().data(data);
        } catch (BusinessException e) {
            return Result.error().code(402).message("用户名或密码不正确");
        }
    }

    @GetMapping(value = "/isExpired")
    public Result isExpired(@RequestHeader("${jwt.header}") String token) {
        if (jwtTokenUtil.validateToken(token)) {
            return Result.ok();
        }
        return Result.error();
    }

    @GetMapping(value = "/refreshToken")
    public Result refresh(@RequestHeader("${jwt.header}") String token) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("jwt", jwtAuthService.refreshToken(token));
        return Result.ok().data(data);
    }

    @GetMapping(value = "/logout")
    public Result logout(@RequestHeader("${jwt.header}") String token) {

        return Result.ok().message("退出成功");
    }

    @PostMapping("register")
    @ApiOperation(value = "注册用户", notes = "添加单个用户/api/user")
    public Result register(@RequestBody User user) {
        System.out.println(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        if (userService.save(user)) {
            return Result.ok().data("user", user);
        } else {
            throw new BusinessException(ResultCode.USER_ACCOUNT_ALREADY_EXIST.getCode(), ResultCode.USER_ACCOUNT_ALREADY_EXIST.getMessage());
        }
    }
}
