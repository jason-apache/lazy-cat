package com.jason.test.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author: mahao
 * @date: 2021/3/7 15:14
 */
@Data
@Accessors(chain = true)
public class UserDto {

    private static final long serialVersionUID = -886136424505480837L;
    private User user;
    private String nickName;
}
