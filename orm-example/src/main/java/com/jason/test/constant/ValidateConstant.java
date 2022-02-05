package com.jason.test.constant;

import cn.hutool.core.util.StrUtil;
import cool.lazy.cat.orm.core.jdbc.exception.ValidationException;
import cool.lazy.cat.orm.core.jdbc.mapping.field.attr.PojoField;
import lombok.Data;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;

/**
 * @author: mahao
 * @date: 2022-02-04 14:10
 */
public class ValidateConstant {

    public static class MessageInfoHolder extends HashMap<String, ValidateMessageInfo> {
        private static final long serialVersionUID = 6494614923162958380L;
        public static MessageInfoHolder defaultInstance;
        static {
            defaultInstance = new MessageInfoHolder();
            Field[] fields = ValidateConstant.class.getFields();
            for (Field field : fields) {
                if (ValidateMessageInfo.class.isAssignableFrom(field.getType())) {
                    boolean isStatic = Modifier.isStatic(field.getModifiers());
                    boolean isFinal = Modifier.isFinal(field.getModifiers());
                    if (isStatic && isFinal) {
                        try {
                            ValidateMessageInfo info = (ValidateMessageInfo) field.get(ValidateConstant.class);
                            defaultInstance.put(info.getUniqueKey(), info);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    public static final String VALIDATE_USER_PHONE_NUM_KEY = "VALIDATE_USER_PHONE_NUM_KEY";
    public static final ValidateMessageInfo VALIDATE_USER_PHONE_NUM = new ValidateMessageInfo(VALIDATE_USER_PHONE_NUM_KEY,
            "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(16[5,6])|(17[0-8])|(18[0-9])|(19[1、5、8、9]))\\d{8}$", "无效手机号:{}", (f, v, info) -> {
        ValidateConstant.COMMON_ACTION.action(f, v, info);
        if (null != v && !v.toString().matches(info.getContent())) {
            throw new ValidationException(StrUtil.format(info.getErrorMessage(), v));
        }
    });

    public static final String VALIDATE_HUMAN_AGE_KEY = "VALIDATE_HUMAN_AGE_KEY";
    public static final ValidateMessageInfo VALIDATE_HUMAN_AGE = new ValidateMessageInfo(VALIDATE_HUMAN_AGE_KEY, "", "无效年龄参数: {}", (f, v, info) -> {
        ValidateConstant.COMMON_ACTION.action(f, v, info);
        if (null != v && ((int) v > 200 || (int) v < 0)) {
            throw new ValidationException(StrUtil.format(info.getErrorMessage(), v));
        }
    });

    @Data
    public static class ValidateMessageInfo {

        public final String uniqueKey;
        private final String content;
        private final String errorMessage;
        private final ValidateAction action;
        public ValidateMessageInfo(String uniqueKey, String content, String errorMessage, ValidateAction action) {
            this.uniqueKey = uniqueKey;
            this.content = content;
            this.errorMessage = errorMessage;
            this.action = action;
        }
    }


    public static final ValidateAction COMMON_ACTION = (f, v, info) -> {
        if (f.getColumn().getValidatorInfo().isNotNull() && v == null) {
            throw new ValidationException(StrUtil.format(info.getErrorMessage(), "空"));
        }
    };

    @FunctionalInterface
    public interface ValidateAction {
        void action(PojoField field, Object value, ValidateMessageInfo info);
    }
}
