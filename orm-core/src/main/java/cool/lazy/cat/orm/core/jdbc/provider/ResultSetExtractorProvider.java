package cool.lazy.cat.orm.core.jdbc.provider;


import cool.lazy.cat.orm.core.jdbc.sql.SqlParameterMapping;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.util.List;

/**
 * @author: mahao
 * @date: 2021/3/28 19:09
 * 返回一个处理resultSet的执行器
 * 该执行器应当完全完成pojo对象的反射、赋值，返回一个完整的pojo集
 */
public interface ResultSetExtractorProvider {

    <T> ResultSetExtractor<List<T>> provider(SqlParameterMapping sqlParameterMapping);
}
