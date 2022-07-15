package cool.lazy.cat.orm.generator.code.generator.body;

import java.util.List;

/**
 * @author : jason.ma
 * @date : 2022/7/13 17:44
 */
public interface ClassExtends extends CompoundElement<JavaCode>, Importable, JavaCode {

    /**
     * @return 继承类集合完全限定名
     */
    List<String> extendsFullNames();

    @Override
    default String terminator() {
        return "";
    }

    @Override
    default String spacer() {
        return " ";
    }
}
