package cool.lazy.cat.orm.generator.code.generator.body;

import cool.lazy.cat.orm.generator.info.MethodArgumentInfo;
import cool.lazy.cat.orm.generator.info.TypeInfo;

/**
 * @author : jason.ma
 * @date : 2022/7/13 17:44
 */
public interface ClassMethod extends CompoundElement<ClassMethodLine>, Importable, InternalElement, DecorativeElement<JavaCode>, JavaCode {

    String getMethodSignature();

    TypeInfo returnType();

    MethodArgumentInfo[] arguments();
}
