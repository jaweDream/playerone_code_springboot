package com.jawe.conf;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CustomExpiredSessionStrategy implements SessionInformationExpiredStrategy {
    private ObjectMapper objectMapper = new ObjectMapper();
    //    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent sessionInformationExpiredEvent)
            throws IOException, ServletException {
//        redirectStrategy.sendRedirect(sessionInformationExpiredEvent.getRequest(),
//                sessionInformationExpiredEvent.getResponse(),
//                "/invaild"
        Map<String,Object> map = new HashMap<>();
        map.put("code",403);
        map.put("msg","您的登录已经超时，或在另一台机器登录"
                + sessionInformationExpiredEvent.getSessionInformation().getLastRequest());
        String json = objectMapper.writeValueAsString(map);
        sessionInformationExpiredEvent.getResponse().setContentType("application/json;charset=utf-8");
        sessionInformationExpiredEvent.getResponse().getWriter().write(json);


    }
}
