package com.jawe.conf;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jawe.response.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class MyAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    @Value("${spring.security.logintype}")
    private String loginType;
    private static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception)
            throws IOException, ServletException {
        if(loginType.equalsIgnoreCase("JSON")){
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(objectMapper.writeValueAsString(Result.error().data("错误","请检查你的用户名")));
        }else {
            response.setContentType("text/html;charset=utf-8");
            super.onAuthenticationFailure(request,response,exception);
        }

    }
}
