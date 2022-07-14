package cool.lazy.cat.orm.generator.code.generator;

import cool.lazy.cat.orm.generator.BeanUtil;
import cool.lazy.cat.orm.generator.code.generator.body.ClassField;
import cool.lazy.cat.orm.generator.code.generator.body.ClassMethod;
import cool.lazy.cat.orm.generator.code.generator.body.ClassPackage;
import cool.lazy.cat.orm.generator.code.generator.body.ClassSubject;
import cool.lazy.cat.orm.generator.code.generator.event.EventName;
import cool.lazy.cat.orm.generator.code.generator.event.EventParam;
import cool.lazy.cat.orm.generator.code.generator.event.EventPublisher;
import cool.lazy.cat.orm.generator.code.generator.structure.JavaClassStructureImpl;
import cool.lazy.cat.orm.generator.code.naming.NamingFactory;
import cool.lazy.cat.orm.generator.config.CodeGeneratorConfig;
import cool.lazy.cat.orm.generator.config.CodeStyleConfig;
import cool.lazy.cat.orm.generator.dialect.Dialect;
import cool.lazy.cat.orm.generator.info.TableFieldInfo;
import cool.lazy.cat.orm.generator.info.TableInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : jason.ma
 * @date : 2022/7/13 14:26
 */
public abstract class AbstractJavaCodeGenerator implements JavaCodeGenerator {

    protected final NamingFactory namingFactory = BeanUtil.getBeanInstance(NamingFactory.class);

    @Override
    public void generate(List<TableInfo> tableInfoList, Dialect dialect, CodeGeneratorConfig codeGeneratorConfig) {
        EventPublisher.publish(EventName.NameEnum.BEFORE_GENERATE, new EventParam().setAllTable(tableInfoList));
        CodeStyleConfig codeStyleConfig = codeGeneratorConfig.getCodeStyleConfig();
        for (TableInfo tableInfo : tableInfoList) {
            JavaClassStructureImpl javaClassStructure = new JavaClassStructureImpl();
            javaClassStructure.setClassPackage(this.generatePackage(codeStyleConfig));
            EventPublisher.publish(EventName.NameEnum.PACKAGE_DONE, new EventParam().setCurrentTable(tableInfo).setCodeStructure(javaClassStructure));
            ClassSubject classSubject = this.generateClassSubject(tableInfo, dialect, codeStyleConfig);
            javaClassStructure.setClassSubject(classSubject);
            EventPublisher.publish(EventName.NameEnum.SUBJECT_INITIALIZED, new EventParam().setCurrentTable(tableInfo).setDialect(dialect).setCodeStructure(javaClassStructure));
            javaClassStructure.setClassFields(new ArrayList<>());
            for (TableFieldInfo field : tableInfo.getFields()) {
                ClassField classField = this.generateField(field, dialect, codeStyleConfig);
                javaClassStructure.getFields().add(classField);
                classSubject.combination(classField);
            }
            EventPublisher.publish(EventName.NameEnum.FIELDS_DONE, new EventParam().setCurrentTable(tableInfo).setDialect(dialect).setCodeStructure(javaClassStructure));
            javaClassStructure.setClassMethods(new ArrayList<>());
            for (ClassField field : javaClassStructure.getFields()) {
                ClassMethod getter = this.generateGetter(field, dialect, codeStyleConfig);
                javaClassStructure.getMethods().add(getter);
                ClassMethod setter = this.generateSetter(field, dialect, codeStyleConfig);
                javaClassStructure.getMethods().add(setter);
                classSubject.combination(getter);
                classSubject.combination(setter);
            }
            EventPublisher.publish(EventName.NameEnum.SETTER_GETTER_DONE, new EventParam().setCurrentTable(tableInfo).setDialect(dialect).setCodeStructure(javaClassStructure));
        }
    }

    protected abstract ClassPackage generatePackage(CodeStyleConfig codeStyleConfig);

    protected abstract ClassSubject generateClassSubject(TableInfo tableInfo, Dialect dialect, CodeStyleConfig codeStyleConfig);

    protected abstract void processExtend();

    protected abstract void processImplements();

    protected abstract ClassField generateField(TableFieldInfo tableFieldInfo, Dialect dialect, CodeStyleConfig codeStyleConfig);

    protected abstract ClassMethod generateGetter(ClassField field, Dialect dialect, CodeStyleConfig codeStyleConfig);

    protected abstract ClassMethod generateSetter(ClassField field, Dialect dialect, CodeStyleConfig codeStyleConfig);

    protected abstract void processImport();

    protected abstract void writeToFile(String content);
}
