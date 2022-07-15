package cool.lazy.cat.orm.generator.code.generator.body;

/**
 * @author : jason.ma
 * @date : 2022/7/13 14:48
 */
public class ClassImport extends AbstractCode implements Line {

    public ClassImport(String code) {
        super("import " + code);
    }

    @Override
    public String spacer() {
        return "";
    }
}
