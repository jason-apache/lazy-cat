package cool.lazy.cat.orm.core.jdbc.provider.impl;

import cool.lazy.cat.orm.core.base.exception.InitFailedException;
import cool.lazy.cat.orm.base.util.Caster;
import cool.lazy.cat.orm.core.jdbc.component.SpecialColumn;
import cool.lazy.cat.orm.core.jdbc.provider.SpecialColumnProvider;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: mahao
 * @date: 2021/11/3 11:06
 */
public class SimpleSpecialColumnProvider implements SpecialColumnProvider {

    protected final Map<Class<? extends SpecialColumn>, SpecialColumn> specialColumnMap = new ConcurrentHashMap<>();

    @Autowired(required = false)
    private void initSpecialColumnMap(List<SpecialColumn> specialColumnList) {
        for (SpecialColumn specialColumn : specialColumnList) {
            this.specialColumnMap.put(specialColumn.getClass(), specialColumn);
        }
    }

    @Override
    public <T extends SpecialColumn> T provider(Class<T> type) {
        SpecialColumn specialColumn = this.specialColumnMap.get(type);
        if (null == specialColumn) {
            try {
                specialColumn = type.newInstance();
            } catch (IllegalAccessException | InstantiationException e) {
                throw new InitFailedException("初始化组件失败！" + type, e);
            }
            specialColumnMap.put(type, specialColumn);
        }
        return Caster.cast(specialColumn);
    }
}
