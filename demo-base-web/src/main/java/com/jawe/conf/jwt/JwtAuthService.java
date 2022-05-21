package com.jawe.conf.jwt;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jawe.handler.BusinessException;
import com.jawe.system.entity.MyUserDetails;
import com.jawe.system.entity.User;
import com.jawe.system.service.UserService;
import com.jawe.system.service.impl.MyUserDetailsService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class JwtAuthService {

    @Resource
    AuthenticationManager authenticationManager;

    @Resource
    MyUserDetailsService userDetailsService;

    @Resource
    JwtTokenUtil jwtTokenUtil;

    /**
     * 登录认证换取JWT令牌
     * @return JWT
     */
    public String login(String username,String password) throws BusinessException{
        try {
            UsernamePasswordAuthenticationToken upToken =
                    new UsernamePasswordAuthenticationToken(username, password);
            Authentication authentication = authenticationManager.authenticate(upToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }catch (AuthenticationException e){
            throw new BusinessException(100,e.getMessage());
        }

        MyUserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return jwtTokenUtil.generateToken(userDetails);
    }

    //刷新token
    public String refreshToken(String oldToken){

        if(!jwtTokenUtil.isTokenExpired(oldToken)){
            return jwtTokenUtil.refreshToken(oldToken);
        }
        return null;
    }


}
