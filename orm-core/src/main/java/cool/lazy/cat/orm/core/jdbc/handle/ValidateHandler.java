package cool.lazy.cat.orm.core.jdbc.handle;


import cool.lazy.cat.orm.core.jdbc.mapping.TableFieldInfo;

/**
 * @author: mahao
 * @date: 2021/3/31 18:57
 * 参数校验器处理器
 */
public interface ValidateHandler {

    /**
     * 处理执行该字段的参数校验器
     * @param fieldInfo 字段信息
     * @param data 源数据
     */
    void handle(TableFieldInfo fieldInfo, Object data);
}
