package cool.lazy.cat.orm.generator.dialect.extractor;

/**
 * @author : jason.ma
 * @date : 2022/7/13 11:28
 */
class FieldAnnotationInfo {
    final java.lang.reflect.Field field;
    final Field annotation;

    public FieldAnnotationInfo(java.lang.reflect.Field field, Field annotation) {
        this.field = field;
        this.annotation = annotation;
    }

    public java.lang.reflect.Field getField() {
        return field;
    }

    public Field getAnnotation() {
        return annotation;
    }
}
