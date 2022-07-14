package cool.lazy.cat.orm.generator.code.generator.body;

import cool.lazy.cat.orm.generator.code.generator.body.keyword.Public;
import cool.lazy.cat.orm.generator.code.generator.body.keyword.Void;
import cool.lazy.cat.orm.generator.info.MethodArgumentInfo;
import cool.lazy.cat.orm.generator.info.TypeInfo;
import cool.lazy.cat.orm.generator.util.CollectionUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : jason.ma
 * @date : 2022/7/14 14:39
 */
public class ClassMethodImpl extends AbstractCompoundElement<ClassMethodLine> implements ClassMethod {

    protected final List<TypeInfo> typeInfos = new ArrayList<>();
    protected final String signature;
    protected final TypeInfo returnType;
    protected final MethodArgumentInfo[] args;

    protected DecorationArea<JavaCode> header;

    @Override
    public String code() {
        if (null != this.getHeader()) {
            return this.getHeader().full() + super.full();
        }
        return super.code();
    }

    @Override
    public DecorationArea<JavaCode> getHeader() {
        return header;
    }

    @Override
    public void setHeader(DecorationArea<JavaCode> header) {
        this.header = header;
    }

    public ClassMethodImpl(String signature, TypeInfo returnType, MethodArgumentInfo... args) {
        this.signature = signature;
        this.returnType = returnType;
        this.args = args;
        if (null != returnType) {
            typeInfos.add(returnType);
        }
        if (CollectionUtil.isNotEmpty(args)) {
            for (MethodArgumentInfo arg : args) {
                typeInfos.add(arg.getTypeInfo());
            }
        }
    }

    @Override
    public void combination(ClassMethodLine classMethodLine) {
        classMethodLine.depth(this.getDepth() + 1);
        super.combination(classMethodLine);
    }

    @Override
    public int getDepth() {
        return 1;
    }

    @Override
    public void depth(int depth) {}

    @Override
    protected String empty() {
        return this.pre();
    }

    @Override
    protected String pre() {
        JavaCode returnValue = returnType == null ? new Void() : new JavaCodeImpl(returnType.getName(), " ");
        ClassMethodArgument argument = new ClassMethodArgument();
        if (CollectionUtil.isNotEmpty(this.args)) {
            for (MethodArgumentInfo arg : this.args) {
                argument.combination(new JavaCodeImpl(arg.getTypeInfo().getName() + " " + arg.getArgName()));
            }
        }
        return new Public().full() + returnValue.full() + new JavaCodeImpl(signature).full() + argument.full();
    }

    @Override
    public String startCharacter() {
        return "{";
    }

    @Override
    public String terminator() {
        return "}";
    }

    @Override
    public List<TypeInfo> importTypes() {
        return typeInfos;
    }

    @Override
    public String getMethodSignature() {
        return signature;
    }

    @Override
    public TypeInfo returnType() {
        return returnType;
    }

    @Override
    public MethodArgumentInfo[] arguments() {
        return args;
    }
}
