package com.jawe.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class WebConf extends WebMvcConfigurationSupport {
    //
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 将所有/static/** 访问都映射到classpath:/static/ 目录下
        registry.addResourceHandler("/**").addResourceLocations("classpath:/resources/")
                .addResourceLocations("classpath:/static/").addResourceLocations("classpath:/templates/")
                .addResourceLocations("classpath:/public/");
        // swagger2
        registry.addResourceHandler("/swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/docs.html").addResourceLocations("classpath:/META-INF/resources/");

        super.addResourceHandlers(registry);
    }
}
