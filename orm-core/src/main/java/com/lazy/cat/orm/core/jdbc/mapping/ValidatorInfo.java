package com.lazy.cat.orm.core.jdbc.mapping;


import com.lazy.cat.orm.core.jdbc.component.validator.Validator;

/**
 * @author: mahao
 * @date: 2021/3/31 16:41
 * 验证器信息
 */
public class ValidatorInfo {

    /**
     * 验证器类型
     */
    private Class<? extends Validator> validator;
    /**
     * 非空字段
     */
    private boolean notNull;
    /**
     * 验证内容
     */
    private String[] validateContent;
    /**
     * 错误信息
     */
    private String[] errorMsg;

    public ValidatorInfo(com.lazy.cat.orm.core.base.annotation.Validator validator) {
        this.validator = validator.type();
        this.notNull = validator.notNull();
        this.validateContent = validator.validateContent();
        this.errorMsg = validator.errorMsg();
    }

    public Class<? extends Validator> getValidator() {
        return validator;
    }

    public ValidatorInfo setValidator(Class<? extends Validator> validator) {
        this.validator = validator;
        return this;
    }

    public boolean isNotNull() {
        return notNull;
    }

    public ValidatorInfo setNotNull(boolean notNull) {
        this.notNull = notNull;
        return this;
    }

    public String[] getValidateContent() {
        return validateContent;
    }

    public ValidatorInfo setValidateContent(String[] validateContent) {
        this.validateContent = validateContent;
        return this;
    }

    public String[] getErrorMsg() {
        return errorMsg;
    }

    public ValidatorInfo setErrorMsg(String[] errorMsg) {
        this.errorMsg = errorMsg;
        return this;
    }
}
