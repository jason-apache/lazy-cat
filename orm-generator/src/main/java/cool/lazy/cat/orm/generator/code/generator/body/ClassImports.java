package cool.lazy.cat.orm.generator.code.generator.body;

/**
 * @author : jason.ma
 * @date : 2022/7/13 17:44
 */
public interface ClassImports extends CompoundElement<Line>, JavaCode {

    @Override
    default String terminator() {
        return "";
    }
}
