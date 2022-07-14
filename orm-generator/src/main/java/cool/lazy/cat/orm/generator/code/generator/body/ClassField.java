package cool.lazy.cat.orm.generator.code.generator.body;

import cool.lazy.cat.orm.generator.info.TypeInfo;

/**
 * @author : jason.ma
 * @date : 2022/7/13 17:44
 */
public interface ClassField extends Line, DecorativeElement<JavaCode>, InternalElement, Importable, JavaCode {

    String fieldName();

    TypeInfo fieldType();
}
