package com.jason.test.pojo.mysql.resource;

/**
 * @author: mahao
 * @date: 2021/11/4 16:42
 */
public enum ResourceType {
    SHARE("共享"),
    COLLECTION("收藏"),
    PRIVATE("私有"),
    PROTECTED("局限"),
    PUBLIC("公有");

    private final String name;

    ResourceType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
