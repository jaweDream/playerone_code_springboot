package com.jawe.conf;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author NieChangan
 */
@Configuration
@EnableSwagger2
public class Swagger2Configuration {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //这里一定要标注你控制器的位置
                .apis(RequestHandlerSelectors.basePackage("com.jawe.system.controller"))
                .apis(RequestHandlerSelectors.basePackage("com.jawe.system.controller_common"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Demo系统API文档")
                .description("Demo系统API文档")
                .termsOfServiceUrl("https://angegit.gitee.io/myblog/")
                .contact(new Contact("jawe",
                        "https://angegit.gitee.io/myblog/",
                        "1461824632@qq.com"))
                .version("1.0")
                .build();
    }
}

