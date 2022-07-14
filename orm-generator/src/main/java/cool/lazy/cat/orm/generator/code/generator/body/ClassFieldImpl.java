package cool.lazy.cat.orm.generator.code.generator.body;

import cool.lazy.cat.orm.generator.code.generator.body.keyword.Private;
import cool.lazy.cat.orm.generator.info.TypeInfo;

import java.util.Collections;
import java.util.List;

/**
 * @author : jason.ma
 * @date : 2022/7/14 11:42
 */
public class ClassFieldImpl extends AbstractCode implements ClassField, Line {

    private final List<TypeInfo> infos;

    private final String fieldName;
    protected DecorationArea<JavaCode> header;
    public ClassFieldImpl(TypeInfo typeInfo, String fieldName) {
        super(new Private().full() + new JavaCodeImpl(typeInfo.getName(), " ").full() + new JavaCodeImpl(fieldName).full());
        this.infos = Collections.singletonList(typeInfo);
        this.fieldName = fieldName;
    }

    @Override
    public String fieldName() {
        return fieldName;
    }

    @Override
    public TypeInfo fieldType() {
        return infos.get(0);
    }

    @Override
    public int getDepth() {
        return 1;
    }

    @Override
    public void depth(int depth) {}

    @Override
    public List<TypeInfo> importTypes() {
        return infos;
    }

    @Override
    public DecorationArea<JavaCode> getHeader() {
        return header;
    }

    @Override
    public void setHeader(DecorationArea<JavaCode> header) {
        this.header = header;
    }
}
