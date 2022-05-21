package com.jawe.system.service.impl;

import com.jawe.system.entity.MyUserDetails;
import com.jawe.system.mapper.MyUserDetailsServiceMapper;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class MyUserDetailsService implements UserDetailsService {

    @Resource
    MyUserDetailsServiceMapper myUserDetailsServiceMapper;

    @Override
    public MyUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MyUserDetails myUserDetails = myUserDetailsServiceMapper.findByPhoneNumber(username);

        if (myUserDetails == null){
            throw new UsernameNotFoundException("用户名不存在");
        }

        List<String> roleCodes = myUserDetailsServiceMapper.findRole(myUserDetails.getUsername());
        List<String> authorities = myUserDetailsServiceMapper.findAuthorityByRoleCodes(roleCodes);

        //获取角色和menu、api数据
        roleCodes = roleCodes.stream()
                .map(rc -> "ROLE_"+rc)
                .collect(Collectors.toList());
        authorities.addAll(roleCodes);
        myUserDetails.setAuthorities(AuthorityUtils.commaSeparatedStringToAuthorityList(
                String.join(",",authorities)
        ));

        System.out.println(myUserDetails.getUsername());
        System.out.println(myUserDetails.getPassword());
        System.out.println(myUserDetails.getAuthorities());
        System.out.println(myUserDetails.isEnabled());

        return myUserDetails;
    }



}
