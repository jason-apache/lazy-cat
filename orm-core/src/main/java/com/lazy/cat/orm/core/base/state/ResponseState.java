package com.lazy.cat.orm.core.base.state;

/**
 * @author: mahao
 * @date: 2021/3/7 16:26
 */
public enum ResponseState {

    SUCCESS("success", "操作成功"),
    FAILED("failed", "操作失败"),
    ERROR("error", "服务器异常"),
    MISSING("unKnow", "所访问的资源不存在"),
    ;

    private final String code;
    private final String message;

    ResponseState(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ResponseState getByCode(String code) {
        ResponseState[] values = ResponseState.values();
        for (ResponseState value : values) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
