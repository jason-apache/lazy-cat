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
 * @date : 2022/7/13 17:31
 */
public class JavaClassStructureImpl implements JavaClassStructure {

    private ClassPackage classPackage;

    private ClassSubject classSubject;

    private List<ClassField> classFields;

    private List<ClassMethod> classMethods;

    private ClassExtends classExtends;

    private ClassImplements classImplements;
    private ClassImports classImports;

    public void setClassPackage(ClassPackage classPackage) {
        this.classPackage = classPackage;
    }

    @Override
    public ClassPackage getPackage() {
        return classPackage;
    }

    public void setClassImports(ClassImports classImports) {
        this.classImports = classImports;
    }

    @Override
    public ClassImports getImports() {
        return classImports;
    }

    public void setClassSubject(ClassSubject classSubject) {
        this.classSubject = classSubject;
    }

    @Override
    public ClassSubject getSubject() {
        return classSubject;
    }

    public void setClassExtends(ClassExtends classExtends) {
        this.classExtends = classExtends;
    }

    @Override
    public ClassExtends getExtends() {
        return classExtends;
    }

    public void setClassImplements(ClassImplements classImplements) {
        this.classImplements = classImplements;
    }

    @Override
    public ClassImplements getImplements() {
        return classImplements;
    }

    public void setClassFields(List<ClassField> classFields) {
        this.classFields = classFields;
    }

    @Override
    public List<ClassField> getFields() {
        return classFields;
    }

    public void setClassMethods(List<ClassMethod> classMethods) {
        this.classMethods = classMethods;
    }

    @Override
    public List<ClassMethod> getMethods() {
        return classMethods;
    }

    @Override
    public String getBody() {
        return null;
    }
}
