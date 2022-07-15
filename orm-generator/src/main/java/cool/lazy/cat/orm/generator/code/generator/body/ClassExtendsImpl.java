package cool.lazy.cat.orm.generator.code.generator.body;

import cool.lazy.cat.orm.generator.code.generator.body.keyword.Extends;
import cool.lazy.cat.orm.generator.info.TypeInfo;
import cool.lazy.cat.orm.generator.util.ClassUtil;
import cool.lazy.cat.orm.generator.util.CollectionUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : jason.ma
 * @date : 2022/7/15 13:00
 */
public class ClassExtendsImpl extends AbstractCompoundElement<JavaCode> implements ClassExtends, Importable {

    private final List<TypeInfo> infos;
    private final List<String> extendsFullNames;

    public ClassExtendsImpl(String... extendTypes) {
        this(Arrays.stream(extendTypes).map(s -> new TypeInfo(ClassUtil.getClassSimpleName(s), s)).collect(Collectors.toList()));
    }

    public ClassExtendsImpl(List<TypeInfo> infos) {
        this.infos = infos;
        if (CollectionUtil.isNotEmpty(infos)) {
            for (TypeInfo info : infos) {
                this.combination(new JavaCodeImpl(info.getName()));
            }
            extendsFullNames = infos.stream().map(TypeInfo::getFullName).collect(Collectors.toList());
        } else {
            extendsFullNames = Collections.emptyList();
        }
    }

    @Override
    protected String pre() {
        return new Extends().full();
    }

    @Override
    protected String contentSeparator() {
        return ", ";
    }

    @Override
    protected boolean separatorAdaptive() {
        return true;
    }

    @Override
    protected boolean headSeparator() {
        return false;
    }

    @Override
    public List<TypeInfo> importTypes() {
        return infos;
    }

    @Override
    public List<String> extendsFullNames() {
        return extendsFullNames;
    }
}
