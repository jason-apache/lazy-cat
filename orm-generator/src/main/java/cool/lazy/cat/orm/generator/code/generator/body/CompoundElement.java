package cool.lazy.cat.orm.generator.code.generator.body;

import java.util.Collection;

/**
 * 复合元素
 * @author : jason.ma
 * @date : 2022/7/13 15:07
 */
public interface CompoundElement<C extends JavaCode> extends Element, JavaCode {

    void combination(C c);

    Collection<C> getContent();
}
