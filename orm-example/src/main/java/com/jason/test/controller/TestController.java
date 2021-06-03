package com.jason.test.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author: mahao
 * @date: 2021/3/6 11:38
 */
@RestController
@RequestMapping(value = "user")
public class TestController {

    //@RequestMapping(value = "/example/test")
    public Map<String, Object> a(@RequestBody Map<String, Object> a) {
        return a;
    }
}
