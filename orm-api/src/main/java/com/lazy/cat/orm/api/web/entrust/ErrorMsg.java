package com.lazy.cat.orm.api.web.entrust;

/**
 * @author: mahao
 * @date: 2021/4/25 16:39
 */
public enum ErrorMsg {
    ERROR("ERROR", "10000", "系统内部错误，请联系管理员！"),
    SERVER_ERROR("FAILED", "20000", "操作失败！"),
    FRAMEWORK_ERROR("ERROR", "30000", "系统内部错误，请联系管理员！"),
    ;

    private final String status;
    private final String code;
    private final String msg;

    ErrorMsg(String status, String code, String msg) {
        this.status = status;
        this.code = code;
        this.msg = msg;
    }

    public String getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
