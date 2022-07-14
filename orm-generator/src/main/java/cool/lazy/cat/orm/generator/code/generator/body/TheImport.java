package cool.lazy.cat.orm.generator.code.generator.body;

/**
 * @author : jason.ma
 * @date : 2022/7/13 14:48
 */
public class TheImport extends AbstractCode implements Line {

    public TheImport(String code) {
        super("import " + code);
    }
}
