package cool.lazy.cat.orm.generator.code.generator.body;

import java.util.List;

/**
 * @author : jason.ma
 * @date : 2022/7/15 14:16
 */
public class ClassImportsImpl extends AbstractCompoundElement<Line> implements ClassImports {

    public ClassImportsImpl(List<String> imports) {
        for (String theImport : imports) {
            this.combination(new ClassImport(theImport));
        }
    }

    @Override
    protected boolean headSeparator() {
        return false;
    }
}
