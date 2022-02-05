package com.jason.test;

import cool.lazy.cat.orm.core.manager.scan.annotation.PojoScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author: mahao
 * @date: 2021/3/3 17:20
 */
@SpringBootApplication
@PojoScan({"com.jason.test.pojo.**"})
public class ApplicationRun {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationRun.class, args);
    }
}
