package com.jason.test.trigger;

import com.jason.test.base.RecordPojo;
import cool.lazy.cat.orm.core.jdbc.component.trigger.Trigger;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * @author: mahao
 * @date: 2021/4/14 13:44
 */
public class RecordPojoTrigger implements Trigger {

    @Override
    @SuppressWarnings("unchecked")
    public void execute(Object... args) {
        if (args[0] instanceof Collection && ((Collection<?>)args[0]).iterator().next() instanceof RecordPojo) {
            Collection<RecordPojo> ref = (Collection<RecordPojo>) args[0];
            for (RecordPojo pojo : ref) {
                this.doSet(pojo, (boolean) args[2]);
            }
        } else if (args[0] instanceof RecordPojo) {
            this.doSet((RecordPojo) args[0], (boolean) args[2]);
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
