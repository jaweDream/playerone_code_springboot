package com.jawe;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.jawe.system.mapper")
@EnableSwagger2
@EnableSwaggerBootstrapUI
public class DemoApplication {


    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        String[] names = context.getBeanDefinitionNames();
        for (String name :
                names) {
            System.out.println(name);
        }

        SpringApplication.run(DemoApplication.class,args);
    }

}

