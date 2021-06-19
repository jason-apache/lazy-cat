package cool.lazy.cat.orm.core.jdbc.component.id;

/**
 * @author: mahao
 * @date: 2021/3/31 18:55
 * 不使用id生成器，pojoid全部由使用者手动设置
 */
public final class None implements IdGenerator {
    @Override
    public Object[] generator(Object... args) {
        return null;
    }
}
