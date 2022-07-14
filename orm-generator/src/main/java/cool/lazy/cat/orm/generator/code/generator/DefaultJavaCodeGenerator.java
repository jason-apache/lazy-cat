package cool.lazy.cat.orm.generator.code.generator;

import cool.lazy.cat.orm.generator.code.generator.body.ClassField;
import cool.lazy.cat.orm.generator.code.generator.body.ClassFieldImpl;
import cool.lazy.cat.orm.generator.code.generator.body.ClassMethod;
import cool.lazy.cat.orm.generator.code.generator.body.ClassMethodImpl;
import cool.lazy.cat.orm.generator.code.generator.body.ClassMethodLineImpl;
import cool.lazy.cat.orm.generator.code.generator.body.ClassPackage;
import cool.lazy.cat.orm.generator.code.generator.body.ClassPackageImpl;
import cool.lazy.cat.orm.generator.code.generator.body.ClassSubject;
import cool.lazy.cat.orm.generator.code.generator.body.ClassSubjectImpl;
import cool.lazy.cat.orm.generator.config.CodeStyleConfig;
import cool.lazy.cat.orm.generator.dialect.Dialect;
import cool.lazy.cat.orm.generator.info.MethodArgumentInfo;
import cool.lazy.cat.orm.generator.info.TableFieldInfo;
import cool.lazy.cat.orm.generator.info.TableInfo;
import cool.lazy.cat.orm.generator.info.TypeInfo;
import cool.lazy.cat.orm.generator.type.TypeCorrespondTable;

/**
 * @author : jason.ma
 * @date : 2022/7/13 17:54
 */
public class DefaultJavaCodeGenerator extends AbstractJavaCodeGenerator implements JavaCodeGenerator {

    @Override
    protected ClassPackage generatePackage(CodeStyleConfig codeStyleConfig) {
        return new ClassPackageImpl(codeStyleConfig.getBasePackage());
    }

    @Override
    protected ClassSubject generateClassSubject(TableInfo tableInfo, Dialect dialect, CodeStyleConfig codeStyleConfig) {
        return new ClassSubjectImpl(super.namingFactory.namingClass(tableInfo, codeStyleConfig.getNamingConfig()));
    }

    @Override
    protected void processExtend() {

    }

    @Override
    protected void processImplements() {

    }

    @Override
    protected ClassField generateField(TableFieldInfo tableFieldInfo, Dialect dialect, CodeStyleConfig codeStyleConfig) {
        TypeInfo typeInfo = TypeCorrespondTable.javaType(tableFieldInfo.getType());
        return new ClassFieldImpl(typeInfo, namingFactory.namingField(tableFieldInfo, codeStyleConfig.getNamingConfig()));
    }

    @Override
    protected ClassMethod generateGetter(ClassField field, Dialect dialect, CodeStyleConfig codeStyleConfig) {
        TypeInfo fieldType = field.fieldType();
        ClassMethod classMethod = new ClassMethodImpl(namingFactory.namingGetter(fieldType, field.fieldName()), fieldType);
        classMethod.combination(new ClassMethodLineImpl("return this." + field.fieldName()));
        return classMethod;
    }

    @Override
    protected ClassMethod generateSetter(ClassField field, Dialect dialect, CodeStyleConfig codeStyleConfig) {
        TypeInfo fieldType = field.fieldType();
        MethodArgumentInfo methodArgumentInfo = new MethodArgumentInfo(fieldType, field.fieldName());
        ClassMethod classMethod = new ClassMethodImpl(namingFactory.namingSetter(fieldType, field.fieldName()), null, methodArgumentInfo);
        classMethod.combination(new ClassMethodLineImpl("this." + field.fieldName() + " = " + methodArgumentInfo.getArgName()));
        return classMethod;
    }

    @Override
    protected void processImport() {

    }

    @Override
    protected void writeToFile(String content) {

    }
}
