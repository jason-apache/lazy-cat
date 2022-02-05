package com.jason.test.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author: mahao
 * @date: 2022-01-30 10:19
 */
@Data
@Accessors(chain = true)
public class ApiInfo {

    private String apiPath;
}
