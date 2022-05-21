package com.jawe.system.controller;

import com.jawe.conf.jwt.JwtAuthService;
import com.jawe.handler.BusinessException;
import com.jawe.response.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class JwtAuthController {


    @Resource
    JwtAuthService jwtAuthService;

    // 获取token
    @PostMapping(value = "/login")
    public Result login(@RequestBody Map<String, String> map) {
        String username = map.get("username");
        String password = map.get("password");

        if (StringUtils.isEmpty(username)
                || StringUtils.isEmpty(password)) {
            return Result.error().code(100).message("用户名和密码不能为空");
        }
        try {
            //正确1.设置返回数据2.保存jwt到redis和mysql中
            HashMap<String, Object> data = new HashMap<>();
            data.put("jwt", jwtAuthService.login(username, password));
            return Result.ok().data(data);
        } catch (BusinessException e) {
            return Result.error().code(402).message("用户名或密码不正确");
        }
    }

    @RequestMapping(value = "/refreshToken")
    public Result refresh(@RequestHeader("${jwt.header}") String token) {

        HashMap<String, Object> data = new HashMap<>();
        //产生新token
        data.put("jwt", jwtAuthService.refreshToken(token));

        return Result.ok().data(data);
    }

}
