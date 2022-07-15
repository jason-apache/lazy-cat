package cool.lazy.cat.orm.generator.code.generator.body;

/**
 * @author : jason.ma
 * @date : 2022/7/14 15:06
 */
public interface ClassMethodLine extends Line, InternalElement {

    @Override
    default String spacer() {
        return "";
    }
}
