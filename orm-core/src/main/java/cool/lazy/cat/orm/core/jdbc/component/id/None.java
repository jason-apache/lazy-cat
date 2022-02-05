package cool.lazy.cat.orm.core.jdbc.component.id;

import java.util.List;

/**
 * @author: mahao
 * @date: 2021/3/31 18:55
 * 不使用id生成器，pojoId全部由使用者手动设置
 */
public final class None implements IdGenerator {
    @Override
    public List<Object> generator(List<Object> instances) {
        return null;
    }
}
