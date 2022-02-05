package cool.lazy.cat.orm.api.web.bo;


/**
 * @author: mahao
 * @date: 2021/3/7 16:21
 * 统一API返回值
 */
public class WebResponse {

    private boolean success;
    private String message;
    private Object data;

    public static WebResponse success(Object data) {
        return success(null, data);
    }

    public static WebResponse success(String message, Object data) {
        return new WebResponse().setSuccess(true).setMessage(message).setData(data);
    }

    public static WebResponse failed(Object data) {
        return failed(null, data);
    }

    public static WebResponse failed(String message, Object data) {
        return new WebResponse().setSuccess(false).setMessage(message).setData(data);
    }

    public boolean getSuccess() {
        return success;
    }

    public WebResponse setSuccess(boolean success) {
        this.success = success;
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
