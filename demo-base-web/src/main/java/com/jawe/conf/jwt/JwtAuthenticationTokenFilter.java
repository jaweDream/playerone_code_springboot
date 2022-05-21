package com.jawe.conf.jwt;

import com.jawe.system.entity.MyUserDetails;
import com.jawe.system.service.impl.MyUserDetailsService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Resource
    JwtTokenUtil jwtTokenUtil;

    @Resource
    MyUserDetailsService myUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String jwtToken = request.getHeader(jwtTokenUtil.getHeader());
        if(!StringUtils.isEmpty(jwtToken)){
            String username = jwtTokenUtil.getUsernameFromToken(jwtToken);
            if(username != null &&SecurityContextHolder.getContext().getAuthentication() == null){
                MyUserDetails userDetails = myUserDetailsService.loadUserByUsername(username);
                if(jwtTokenUtil.validateToken(jwtToken)){
                    //给使用该JWT令牌的用户进行授权
                    UsernamePasswordAuthenticationToken authenticationToken
                            = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    System.out.println("用户:"+userDetails.getUsername()+" （正在登录）");
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }

        filterChain.doFilter(request,response);
    }
}
