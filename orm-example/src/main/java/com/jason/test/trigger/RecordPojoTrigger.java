package com.jason.test.trigger;

import com.jason.test.base.RecordPojo;
import cool.lazy.cat.orm.core.jdbc.component.trigger.Trigger;
import cool.lazy.cat.orm.core.jdbc.sql.source.PojoSqlSource;
import cool.lazy.cat.orm.core.jdbc.sql.source.SqlSource;
import cool.lazy.cat.orm.core.jdbc.sql.type.Insert;
import cool.lazy.cat.orm.core.jdbc.sql.type.SqlType;

import java.time.LocalDateTime;

/**
 * @author: mahao
 * @date: 2021/4/14 13:44
 */
public class RecordPojoTrigger implements Trigger {

    @Override
    public void execute(Class<? extends SqlType> type, Object instance) {
        if (instance instanceof RecordPojo) {
            this.doSet((RecordPojo) instance, Insert.class.isAssignableFrom(type));
        } else if (instance instanceof SqlSource) {
            this.doSet((SqlSource) instance, Insert.class.isAssignableFrom(type));
        }
    }

    protected void doSet(SqlSource sqlSource, boolean onInsert) {
        if (sqlSource instanceof PojoSqlSource && ((PojoSqlSource) sqlSource).getPojo() instanceof RecordPojo) {
            this.doSet(((RecordPojo) ((PojoSqlSource) sqlSource).getPojo()), onInsert);
        } else {
            if (RecordPojo.class.isAssignableFrom(sqlSource.getPojoType())) {
                if (onInsert) {
                    sqlSource.set("createDate", LocalDateTime.now());
                    sqlSource.set("updateDate", LocalDateTime.now());
                    sqlSource.set("modifyCounter", 0);
                } else {
                    sqlSource.set("updateDate", LocalDateTime.now());
                }
            }
        }
    }

    protected void doSet(RecordPojo pojo, boolean onInsert) {
        if (onInsert) {
            pojo.setCreateDate(LocalDateTime.now());
            pojo.setUpdateDate(LocalDateTime.now());
            pojo.setModifyCounter((long) 0);
        } else {
            pojo.setUpdateDate(LocalDateTime.now());
            pojo.setModifyCounter(pojo.getModifyCounter() == null ? 0 : pojo.getModifyCounter() +1);
        }
    }
}
