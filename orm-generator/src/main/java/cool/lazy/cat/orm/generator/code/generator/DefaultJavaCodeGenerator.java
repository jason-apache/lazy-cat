package cool.lazy.cat.orm.generator.code.generator;

import cool.lazy.cat.orm.generator.code.generator.body.ClassExtends;
import cool.lazy.cat.orm.generator.code.generator.body.ClassExtendsImpl;
import cool.lazy.cat.orm.generator.code.generator.body.ClassField;
import cool.lazy.cat.orm.generator.code.generator.body.ClassFieldImpl;
import cool.lazy.cat.orm.generator.code.generator.body.ClassImplements;
import cool.lazy.cat.orm.generator.code.generator.body.ClassImplementsImpl;
import cool.lazy.cat.orm.generator.code.generator.body.ClassImports;
import cool.lazy.cat.orm.generator.code.generator.body.ClassImportsImpl;
import cool.lazy.cat.orm.generator.code.generator.body.ClassMethod;
import cool.lazy.cat.orm.generator.code.generator.body.ClassMethodImpl;
import cool.lazy.cat.orm.generator.code.generator.body.ClassMethodLineImpl;
import cool.lazy.cat.orm.generator.code.generator.body.ClassPackage;
import cool.lazy.cat.orm.generator.code.generator.body.ClassPackageImpl;
import cool.lazy.cat.orm.generator.code.generator.body.ClassSubject;
import cool.lazy.cat.orm.generator.code.generator.body.ClassSubjectImpl;
import cool.lazy.cat.orm.generator.code.generator.body.Importable;
import cool.lazy.cat.orm.generator.code.generator.structure.JavaClassStructure;
import cool.lazy.cat.orm.generator.config.CodeStyleConfig;
import cool.lazy.cat.orm.generator.dialect.Dialect;
import cool.lazy.cat.orm.generator.info.MethodArgumentInfo;
import cool.lazy.cat.orm.generator.info.TableFieldInfo;
import cool.lazy.cat.orm.generator.info.TableInfo;
import cool.lazy.cat.orm.generator.info.TypeInfo;
import cool.lazy.cat.orm.generator.type.TypeCorrespondTable;
import cool.lazy.cat.orm.generator.util.ClassUtil;
import cool.lazy.cat.orm.generator.util.CollectionUtil;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author : jason.ma
 * @date : 2022/7/13 17:54
 */
public class DefaultJavaCodeGenerator extends AbstractJavaCodeGenerator implements JavaCodeGenerator {

    protected static final Set<String> JAVA_BASIC_TYPES = new HashSet<>(Arrays.asList("byte", "short", "int", "long", "char", "boolean", "float", "double"));
    protected static final Set<String> JAVA_BASIC_TYPES_ARR = new HashSet<>(Arrays.asList("[B", "[S", "[I", "[J", "[C", "[Z", "[F", "[D"));

    @Override
    protected ClassPackage generatePackage(CodeStyleConfig codeStyleConfig) {
        return new ClassPackageImpl(codeStyleConfig.getBasePackage());
    }

    @Override
    protected ClassSubject generateClassSubject(TableInfo tableInfo, Dialect dialect, CodeStyleConfig codeStyleConfig) {
        return new ClassSubjectImpl(super.namingFactory.namingClass(tableInfo, codeStyleConfig.getNamingConfig()));
    }

    @Override
    protected ClassExtends generateExtend(TableInfo tableInfo, Dialect dialect, CodeStyleConfig codeStyleConfig) {
        if (CollectionUtil.isNotEmpty(codeStyleConfig.getClassExtends())) {
            return new ClassExtendsImpl(codeStyleConfig.getClassExtends());
        }
        return null;
    }

    @Override
    protected ClassImplements generateImplements(TableInfo tableInfo, Dialect dialect, CodeStyleConfig codeStyleConfig) {
        if (CollectionUtil.isNotEmpty(codeStyleConfig.getClassImplements())) {
            return new ClassImplementsImpl(codeStyleConfig.getClassImplements());
        }
        return null;
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
    protected ClassImports generateImport(TableInfo tableInfo, JavaClassStructure classStructure, Dialect dialect, CodeStyleConfig codeStyleConfig) {
        Stream<Importable> typeImports = Stream.concat(Stream.of(classStructure.getExtends(), classStructure.getImplements()), this.cast(classStructure.getSubject().getContent().stream().filter(c -> c instanceof Importable)));
        List<String> imports = typeImports.flatMap(i -> i.importTypes().stream()).filter(this.filterImportRule(classStructure.getPackage()))
                .map(this::getTargetClassName).distinct().sorted(Comparator.comparingInt(String::hashCode)).collect(Collectors.toList());
        return new ClassImportsImpl(imports);
    }

    @SuppressWarnings("unchecked")
    protected <T> T cast(Object o) {
        return (T) o;
    }

    protected Predicate<TypeInfo> filterImportRule(ClassPackage classPackage) {
        return (t -> {
            String className = this.getTargetClassName(t);
            // java.lang包无需导入
            return !className.startsWith("java.lang")
                    // 基本类型无需导入 基本类型数组无需导入
                    && ! JAVA_BASIC_TYPES.contains(t.getFullName()) && !JAVA_BASIC_TYPES_ARR.contains(t.getFullName())
                    && !Objects.equals(ClassUtil.getClassPackageName(className), classPackage.getPackage());
        });
    }

    protected String getTargetClassName(TypeInfo typeInfo) {
        // 代表是数组 eg: [Ljava.lang.String;
        if (typeInfo.getFullName().startsWith("[")) {
            // 截取掉前面的 "[L" 和后面的 ";"
            return typeInfo.getFullName().substring(2).substring(0, typeInfo.getFullName().length() -3);
        }
        return typeInfo.getFullName();
    }

    @Override
    protected void writeToFile(String content) {

    }
}
