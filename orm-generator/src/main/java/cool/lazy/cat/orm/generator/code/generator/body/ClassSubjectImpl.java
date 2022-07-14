package cool.lazy.cat.orm.generator.code.generator.body;

import cool.lazy.cat.orm.generator.code.generator.body.keyword.Public;
import cool.lazy.cat.orm.generator.code.generator.body.keyword.TheClass;

/**
 * @author : jason.ma
 * @date : 2022/7/13 17:44
 */
public class ClassSubjectImpl extends AbstractDecorativeElement<JavaCode>
        implements ClassSubject, CompoundElement<JavaCode>, JavaCode {

    protected final String className;

    public ClassSubjectImpl(String className) {
        this.className = className;
    }

    @Override
    protected String empty() {
        return this.pre();
    }

    @Override
    protected String pre() {
        return new Public().full() + new TheClass().full() + new JavaCodeImpl(className, " ").full();
    }

    @Override
    public String startCharacter() {
        return "{";
    }

    @Override
    public String terminator() {
        return "}";
    }
}
