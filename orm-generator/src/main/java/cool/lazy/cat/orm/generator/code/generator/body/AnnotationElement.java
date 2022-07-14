package cool.lazy.cat.orm.generator.code.generator.body;

/**
 * @author : jason.ma
 * @date : 2022/7/13 15:24
 */
public interface AnnotationElement extends Importable, Element, InternalElement, CompoundElement<AnnotationElement> {

    @Override
    default String startCharacter() {
        return "(";
    }

    @Override
    default String terminator() {
        return ")";
    }
}
