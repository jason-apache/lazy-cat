package com.jason.test.controller;

import com.jason.test.service.ApiInfoExtractService;
import com.jason.test.vo.ApiInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: mahao
 * @date: 2022-01-30 10:18
 */
@RestController
@RequestMapping(value = "apiInfo")
public class ApiInformationController {

    @Autowired
    ApiInfoExtractService apiInfoExtractService;

    @GetMapping(value = "getAll")
    public List<ApiInfo> allApiInfo() {
        return apiInfoExtractService.extract();
    }
}
