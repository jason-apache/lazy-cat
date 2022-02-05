package cool.lazy.cat.orm.core.jdbc.component.id;

import java.util.List;

/**
 * @author: mahao
 * @date: 2021/3/30 19:53
 * 使用数据库自增，新增时完全忽略该字段
 */
public final class Auto implements IdGenerator {

    @Override
    public List<Object> generator(List<Object> instances) {
        return null;
    }
}
