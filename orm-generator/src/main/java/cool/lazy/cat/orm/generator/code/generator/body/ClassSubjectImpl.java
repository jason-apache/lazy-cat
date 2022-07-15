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
    private ClassExtends classExtends;

    private ClassImplements classImplements;

    public ClassSubjectImpl(String className) {
        this.className = className;
    }

    @Override
    public ClassExtends classExtends() {
        return classExtends;
    }

    @Override
    public void classExtends(ClassExtends classExtends) {
        this.classExtends = classExtends;
    }

    @Override
    public ClassImplements classImplements() {
        return classImplements;
    }

    @Override
    public void classImplements(ClassImplements classImplements) {
        this.classImplements = classImplements;
    }

    @Override
    protected String empty() {
        return this.pre();
    }

    @Override
    protected String pre() {
        String pre = new Public().full() + new TheClass().full() + new JavaCodeImpl(className, " ").full();
        if (null != classExtends) {
            pre += classExtends.full();
        }
        if (null != classImplements) {
            pre += classImplements.full();
        }
        return pre;
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
