package com.lazy.cat.orm.core.jdbc.provider;


import com.lazy.cat.orm.core.jdbc.holder.SqlParamHolder;
import com.lazy.cat.orm.core.jdbc.holder.TableChainHolder;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.util.List;

/**
 * @author: mahao
 * @date: 2021/3/28 19:09
 * 返回一个处理resultSet的执行器
 * 该执行器应当完全完成pojo对象的反射、赋值，返回一个完整的pojo集
 */
public interface ResultSetExtractorProvider {

    /**
     * 根据列映射关系、sql参数（包含列映射）完成pojo初始化并赋值
     * @param tableChainHolder 包含表信息、嵌套的表链、平铺的表链
     * @param sqlParamHolder sql参数（包含列映射）
     * @param initialCapacity 容器初始大小
     * @param <T> 泛型类型
     * @return pojo集
     */
    <T> ResultSetExtractor<List<T>> provider(TableChainHolder tableChainHolder, SqlParamHolder sqlParamHolder, int initialCapacity);
}
