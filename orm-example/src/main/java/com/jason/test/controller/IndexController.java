package com.jason.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author: mahao
 * @date: 2021/10/27 16:52
 */
@Controller
@RequestMapping
public class IndexController {

    @RequestMapping
    public String toIndex() {
        return "redirect:dataModel";
    }

    @RequestMapping(value = "dataModel")
    public String dataModel() {
        return "data_model";
    }
}
