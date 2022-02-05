package cool.lazy.cat.orm.core.jdbc.sql;

import cool.lazy.cat.orm.core.jdbc.exception.executor.MismatchedSqlTypeException;
import cool.lazy.cat.orm.core.jdbc.param.DataHolderParam;
import cool.lazy.cat.orm.core.jdbc.param.DeleteParam;
import cool.lazy.cat.orm.core.jdbc.param.Param;
import cool.lazy.cat.orm.core.jdbc.param.SearchParam;
import cool.lazy.cat.orm.core.jdbc.param.operation.DataOperationDescriptor;
import cool.lazy.cat.orm.core.jdbc.sql.type.Delete;
import cool.lazy.cat.orm.core.jdbc.sql.type.Insert;
import cool.lazy.cat.orm.core.jdbc.sql.type.Save;
import cool.lazy.cat.orm.core.jdbc.sql.type.Select;
import cool.lazy.cat.orm.core.jdbc.sql.type.SqlType;
import cool.lazy.cat.orm.core.jdbc.sql.type.Update;

import java.util.List;

/**
 * @author: mahao
 * @date: 2021/7/13 16:28
 */
public class SqlParameterMappingImpl implements SqlParameterMapping {

    private final Class<? extends SqlType> type;
    private List<ParameterMapping> mappings;
    private final Param param;
    private final DataOperationDescriptor operationDescriptor;
    private MetaInfo metaInfo;

    public SqlParameterMappingImpl(Class<? extends SqlType> type, Param param, DataOperationDescriptor operationDescriptor) {
        this.type = type;
        this.param = param;
        this.operationDescriptor = operationDescriptor;
        this.check();
    }

    private void check() {
        if (null == this.type) {
            throw new IllegalArgumentException("sqlType不能为空!");
        }
        if (null == this.param) {
            throw new IllegalArgumentException("param不能为空!");
        }
        if (Select.class.isAssignableFrom(type)) {
            if (!(this.param instanceof SearchParam)) {
                throw new MismatchedSqlTypeException("sqlType与param类型不匹配: " + this.type.getName() + "#" + this.param.getClass());
            }
        } else if (Save.class.isAssignableFrom(type) || Insert.class.isAssignableFrom(type) || Update.class.isAssignableFrom(type)) {
            if (!(this.param instanceof DataHolderParam) || this.param instanceof DeleteParam) {
                throw new MismatchedSqlTypeException("sqlType与param类型不匹配: " + this.type.getName() + "#" + this.param.getClass());
            }
        } else if (Delete.class.isAssignableFrom(type)) {
            if (!(this.param instanceof DeleteParam)) {
                throw new MismatchedSqlTypeException("sqlType与param类型不匹配: " + this.type.getName() + "#" + this.param.getClass());
            }
        } else {
            throw new UnsupportedOperationException("不支持的sqlType: " + this.type.getName() + " 请自定义规则实现");
        }
    }

    @Override
    public void setMetaInfo(MetaInfo metaInfo) {
        this.metaInfo = metaInfo;
    }

    @Override
    public Param getParam() {
        return param;
    }

    @Override
    public DataOperationDescriptor getDataOperationDescriptor() {
        return operationDescriptor;
    }

    @Override
    public Class<? extends SqlType> getType() {
        return type;
    }

    @Override
    public List<ParameterMapping> getParameterMappings() {
        return mappings;
    }

    @Override
    public void setParameterMappings(List<ParameterMapping> mappings) {
        this.mappings = mappings;
    }

    @Override
    public MetaInfo getMetaInfo() {
        return this.metaInfo;
    }
}
