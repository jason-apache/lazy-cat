package com.jason.test;


import cool.lazy.cat.orm.core.manager.scan.annotation.PojoScan;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author: mahao
 * @date: 2021/3/4 18:00
 */
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan({"com.jason.test"})
@PojoScan({"com.jason.test.pojo.**"})
public class TestConfiguration {
}
