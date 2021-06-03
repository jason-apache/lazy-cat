package com.lazy.cat.orm.core.jdbc.component.id;

import com.lazy.cat.orm.core.base.util.BeanFieldUtil;
import com.lazy.cat.orm.core.jdbc.dialect.Dialect;
import com.lazy.cat.orm.core.jdbc.dialect.DialectRegister;
import com.lazy.cat.orm.core.jdbc.exception.SequenceNotFoundException;
import com.lazy.cat.orm.core.jdbc.manager.PojoTableManager;
import com.lazy.cat.orm.core.jdbc.mapping.IdStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Collection;

/**
 * @author: mahao
 * @date: 2021/3/30 20:01
 * 序列id生成器
 */
public class SequenceIdGenerator implements IdGenerator {

    @Autowired
    protected PojoTableManager pojoTableManager;
    @Autowired
    protected JdbcTemplate jdbcTemplate;
    protected Dialect dialect;

    @Autowired
    private void initDialect(DialectRegister dialectRegister) {
        // 注册一个当前项目使用的数据库方言
        this.dialect = dialectRegister.getDialect();
    }

    @Override
    public Object[] generator(Object... args) {
        Class<?> pojoType = BeanFieldUtil.getTypeFromObj(args[0]);
        IdStrategy id = pojoTableManager.getByPojoType(pojoType).getTableInfo().getId();
        if (!id.havingSequence()) {
            throw new SequenceNotFoundException("pojo主键未定义序列：#" + pojoType.getName());
        }
        return generator(args[0], id);
    }

    /**
     * 根据数据库方言生成序列查询语句，执行查询
     * @param data 原始数据集
     * @param id id生成策略
     * @return 序列id结果集
     */
    protected Object[] generator(Object data, IdStrategy id) {
        if (data instanceof Collection) {
            Collection<?> dataRef = (Collection<?>) data;
            Object[] ids = new Object[dataRef.size()];
            for (int i = 0; i < ids.length; i++) {
                ids[i] = jdbcTemplate.queryForObject(dialect.selectSequenceNextValueSql(id.getSequenceInfo().getSchema(), id.getSequenceInfo().getName()), id.getJavaType());
            }
            return ids;
        }
        return new Object[]{jdbcTemplate.queryForObject(dialect.selectSequenceNextValueSql(id.getSequenceInfo().getSchema(), id.getSequenceInfo().getName()), id.getJavaType())};
    }
}
