package cool.lazy.cat.orm.core.base.bo;


import cool.lazy.cat.orm.core.base.state.ResponseState;

/**
 * @author: mahao
 * @date: 2021/3/7 16:21
 * 统一API返回值
 */
public class WebResponse {

    private String state;
    private String message;
    private Object data;

    public static WebResponse success(Object data) {
        return success(ResponseState.SUCCESS.getMessage(), data);
    }

    public static WebResponse success(String message, Object data) {
        return new WebResponse().setState(ResponseState.SUCCESS.getCode()).setMessage(message).setData(data);
    }

    public static WebResponse failed(Object data) {
        return failed(ResponseState.FAILED.getMessage(), data);
    }

    public static WebResponse failed(String message, Object data) {
        return new WebResponse().setState(ResponseState.FAILED.getCode()).setMessage(message).setData(data);
    }

    public static WebResponse error(Object data) {
        return error(ResponseState.ERROR.getMessage(), data);
    }

    public static WebResponse error(String message, Object data) {
        return new WebResponse().setState(ResponseState.ERROR.getCode()).setMessage(message).setData(data);
    }

    public static WebResponse missing(Object data) {
        return missing(ResponseState.MISSING.getMessage(), data);
    }

    public static WebResponse missing(String message, Object data) {
        return new WebResponse().setState(ResponseState.MISSING.getCode()).setMessage(message).setData(data);
    }

    public String getState() {
        return state;
    }

    public WebResponse setState(String state) {
        this.state = state;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public WebResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    public Object getData() {
        return data;
    }

    public WebResponse setData(Object data) {
        this.data = data;
        return this;
    }
}
