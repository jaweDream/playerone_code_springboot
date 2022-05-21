package com.jawe.conf;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jawe.response.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component

public class MyAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Value("${spring.security.logintype}")
    private String loginType;
    private static ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {
        if (loginType.equalsIgnoreCase("JSON")){
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(objectMapper.writeValueAsString(Result.ok()));
        }else{
            super.onAuthenticationSuccess(request,response,authentication);
        }
    }
}
