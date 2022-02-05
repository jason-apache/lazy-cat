package cool.lazy.cat.orm.core.jdbc.mapping;

import cool.lazy.cat.orm.core.jdbc.component.convert.TypeConverter;
import cool.lazy.cat.orm.core.jdbc.mapping.parameter.ParameterizationInfo;

/**
 * @author: mahao
 * @date: 2021/10/18 11:15
 * 列映射
 */
public interface Column extends ParameterizationInfo {

    /**
     * @return 列是否拥有类型转换器
     */
    boolean havingTypeConverter();

    /**
     * @return 列是否拥有校验器
     */
    boolean havingValidator();

    /**
     * @return 列是否参与新增
     */
    boolean isInsertable();

    /**
     * @return 列是否参与更新
     */
    boolean isUpdatable();

    /**
     * @return 列名
     */
    String getName();

    /**
     * @return 类型转换器类型
     */
    Class<? extends TypeConverter> getTypeConverter();

    /**
     * @return 校验器信息
     */
    ValidatorInfo getValidatorInfo();

    /**
     * @return 列排序
     */
    int getSort();
}
