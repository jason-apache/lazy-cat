package cool.lazy.cat.orm.generator.code.generator.structure;

import cool.lazy.cat.orm.generator.code.generator.body.ClassField;
import cool.lazy.cat.orm.generator.code.generator.body.ClassMethod;
import cool.lazy.cat.orm.generator.code.generator.body.ClassPackage;
import cool.lazy.cat.orm.generator.code.generator.body.ClassSubject;

import java.util.List;

/**
 * @author : jason.ma
 * @date : 2022/7/14 11:17
 */
public interface JavaClassStructure extends JavaCodeStructure {

    ClassPackage getPackage();

    ClassImport getImport();

    ClassSubject getSubject();

    ClassExtend getExtend();

    ClassImplement getImplement();

    List<ClassField> getFields();

    List<ClassMethod> getMethods();
}
