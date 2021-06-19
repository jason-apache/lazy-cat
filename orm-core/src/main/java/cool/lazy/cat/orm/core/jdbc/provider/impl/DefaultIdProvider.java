package cool.lazy.cat.orm.core.jdbc.provider.impl;

import cool.lazy.cat.orm.core.base.exception.InitFailedException;
import cool.lazy.cat.orm.core.jdbc.component.id.IdGenerator;
import cool.lazy.cat.orm.core.jdbc.provider.IdProvider;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author: mahao
 * @date: 2021/3/30 20:08
 */
public class DefaultIdProvider implements IdProvider {

    protected Map<Class<? extends IdGenerator>, IdGenerator> idGeneratorMap = new HashMap<>();

    @Autowired(required = false)
    private void initGeneratorMap(List<IdGenerator> idGeneratorList) {
        this.idGeneratorMap = idGeneratorList.stream().collect(Collectors.toMap(IdGenerator::getClass, Function.identity()));
    }

    @Override
    public Object[] provider(Class<? extends IdGenerator> type, Object... args) {
        IdGenerator idGenerator = this.idGeneratorMap.get(type);
        if (null == idGenerator) {
            try {
                idGenerator = type.newInstance();
            } catch (IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
                throw new InitFailedException("初始化idGenerator失败！");
            }
            idGeneratorMap.put(type, idGenerator);
        }
        return idGenerator.generator(args);
    }
}
