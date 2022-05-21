package com.jawe.test.generator;

import com.jawe.system.entity.AliOssEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class Alioss {

    @Autowired
    private AliOssEntity aliOssEntity;

    @Test
    public void contextLoads(){
        System.out.println(aliOssEntity.toString());
    }
}
