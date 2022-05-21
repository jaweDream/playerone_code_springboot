package com.jawe.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jawe.system.entity.MyUserDetails;
import com.jawe.system.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface MyUserDetailsServiceMapper extends BaseMapper<User> {

    @Select("SELECT username,password,enable \n" +
            " from user u \n" +
            " where u.username = #{username};")
    MyUserDetails findByPhoneNumber(@Param("username") String username);

    @Select("SELECT role_code\n" +
            "FROM role r\n" +
            "LEFT JOIN user_role ur ON r.id = ur.role_id\n" +
            "LEFT JOIN user u ON u.id = ur.user_id\n" +
            "WHERE u.username = #{username}")
    List<String> findRole(@Param("username") String username);

//    @Select({
//            "<script>",
//            "SELECT DISTINCT url ",
//            "FROM menu m ",
//            "LEFT JOIN role_menu rm ON m.id = rm.menu_id ",
//            "LEFT JOIN role r ON r.id = rm.role_id ",
//            "WHERE r.role_code IN",
//            "<foreach collection='roleCodes' item='roleCode' open='(' separator=',' close=')'>",
//            "#{roleCode}",
//            "</foreach>",
//            "</script>"
//    })
    @Select({
            "<script>",
            "SELECT DISTINCT url ",
            "FROM api m ",
            "LEFT JOIN role_api rm ON m.id = rm.api_id ",
            "LEFT JOIN role r ON r.id = rm.role_id ",
            "WHERE r.role_code IN",
            "<foreach collection='roleCodes' item='roleCode' open='(' separator=',' close=')'>",
            "#{roleCode}",
            "</foreach>",
            "</script>"
    })
    List<String> findAuthorityByRoleCodes(@Param("roleCodes") List<String> roleCodes);

}
