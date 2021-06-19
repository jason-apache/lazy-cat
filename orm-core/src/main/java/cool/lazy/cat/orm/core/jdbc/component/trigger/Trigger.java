package cool.lazy.cat.orm.core.jdbc.component.trigger;

/**
 * @author: mahao
 * @date: 2021/4/14 11:52
 * 触发器
 */
public interface Trigger {

    /**
     * 执行触发器
     * @param args 参数
     */
    void execute(Object ...args);
}
