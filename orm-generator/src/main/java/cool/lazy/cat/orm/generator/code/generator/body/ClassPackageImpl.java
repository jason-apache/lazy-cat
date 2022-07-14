package cool.lazy.cat.orm.generator.code.generator.body;

/**
 * @author : jason.ma
 * @date : 2022/7/13 14:48
 */
public class ClassPackageImpl extends AbstractCode implements ClassPackage, Line {

    public ClassPackageImpl(String code) {
        super("package " + code);
    }
}
