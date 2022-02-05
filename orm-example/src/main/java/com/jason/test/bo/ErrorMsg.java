package com.jason.test.bo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author: mahao
 * @date: 2021-11-18 19:07
 */
@Data
@Accessors(chain = true)
public class ErrorMsg implements Serializable {
    private static final long serialVersionUID = 210920557952406584L;

    private String desc;
    private String message;
    private String code;

    public ErrorMsg(String desc, String message, String code) {
        this.desc = desc;
        this.message = message;
        this.code = code;
    }
}
