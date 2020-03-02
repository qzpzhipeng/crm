package com.mage;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @param
 * @author qzp
 * @create 2020-03-02 11:56
 */
@SpringBootApplication
@MapperScan("com.mage.crm.dao")
public class Starter {
    public static void main(String[] args) {
        SpringApplication.run(Starter.class);
    }
}
