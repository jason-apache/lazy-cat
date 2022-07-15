package cool.lazy.cat.orm.generator.code.generator.body;

import cool.lazy.cat.orm.generator.code.generator.body.keyword.Implements;
import cool.lazy.cat.orm.generator.info.TypeInfo;

import java.util.List;

/**
 * @author : jason.ma
 * @date : 2022/7/15 13:57
 */
public class ClassImplementsImpl extends ClassExtendsImpl implements ClassImplements {

    public ClassImplementsImpl(String... extendTypes) {
        super(extendTypes);
    }

    public ClassImplementsImpl(List<TypeInfo> infos) {
        super(infos);
    }

    @Override
    protected String pre() {
        return new Implements().full();
    }
}
