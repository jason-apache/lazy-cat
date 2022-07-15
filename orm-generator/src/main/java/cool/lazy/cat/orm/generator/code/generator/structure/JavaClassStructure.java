package cool.lazy.cat.orm.generator.code.generator.structure;

import cool.lazy.cat.orm.generator.code.generator.body.ClassExtends;
import cool.lazy.cat.orm.generator.code.generator.body.ClassField;
import cool.lazy.cat.orm.generator.code.generator.body.ClassImplements;
import cool.lazy.cat.orm.generator.code.generator.body.ClassImports;
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

    ClassImports getImports();

    ClassSubject getSubject();

    ClassExtends getExtends();

    ClassImplements getImplements();

    List<ClassField> getFields();

    List<ClassMethod> getMethods();
}
