package com.jason.test.component;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.jason.test.bo.ErrorMsg;
import cool.lazy.cat.orm.core.jdbc.exception.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @author: mahao
 * @date: 2021-11-18 16:51
 */
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return super.handleHttpRequestMethodNotSupported(ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    @ExceptionHandler({})
    protected ResponseEntity<ErrorMsg> handleException(Exception ex) {
        Throwable rootCause = ExceptionUtil.getRootCause(ex);
        String message = this.getMessage(rootCause);
        ErrorMsg error = new ErrorMsg("系统异常", message, "-1");
        if (rootCause instanceof ValidationException) {
            error.setDesc("数据格式错误").setCode("40000");
        } else if (rootCause instanceof UnsupportedOperationException) {
            error.setDesc("不允许的操作").setCode("70000");
        }
        logger.error("全局异常", ex);
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    protected String getMessage(Throwable cause) {
        String msg = cause.getMessage();
        if (null != msg) {
            return msg;
        }
        return ExceptionUtil.getMessage(cause);
    }
}
