package cool.lazy.cat.orm.core.jdbc.provider.impl;

import cool.lazy.cat.orm.core.base.exception.InitFailedException;
import cool.lazy.cat.orm.base.component.IdGenerator;
import cool.lazy.cat.orm.core.jdbc.provider.IdGeneratorProvider;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: mahao
 * @date: 2021/3/30 20:08
 */
public class DefaultIdGeneratorProvider implements IdGeneratorProvider {

    protected final Map<Class<? extends IdGenerator>, IdGenerator> idGeneratorMap = new ConcurrentHashMap<>();

    @Autowired(required = false)
    private void initGeneratorMap(List<IdGenerator> idGeneratorList) {
        for (IdGenerator idGenerator : idGeneratorList) {
            this.idGeneratorMap.put(idGenerator.getClass(), idGenerator);
        }
    }

    @Override
    public IdGenerator provider(Class<? extends IdGenerator> type) {
        IdGenerator idGenerator = this.idGeneratorMap.get(type);
        if (null == idGenerator) {
            try {
                idGenerator = type.newInstance();
            } catch (IllegalAccessException | InstantiationException e) {
                throw new InitFailedException("初始化idGenerator失败！", e);
            }
            idGeneratorMap.put(type, idGenerator);
        }
        return idGenerator;
    }
}
