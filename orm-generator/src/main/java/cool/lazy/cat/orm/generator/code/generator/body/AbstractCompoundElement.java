package cool.lazy.cat.orm.generator.code.generator.body;

import cool.lazy.cat.orm.generator.constant.ConstantEnum;
import cool.lazy.cat.orm.generator.constant.ConstantRegistry;
import cool.lazy.cat.orm.generator.util.CollectionUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author : jason.ma
 * @date : 2022/7/14 10:31
 */
public abstract class AbstractCompoundElement<E extends JavaCode> implements CompoundElement<E>, Element, JavaCode {

    protected List<E> codes = new ArrayList<>();

    @Override
    public void combination(E e) {
        codes.add(e);
    }

    @Override
    public Collection<E> getContent() {
        return codes;
    }

    protected String pre() {
        return "";
    }

    protected String empty() {
        return "";
    }

    protected String contentSeparator() {
        return ConstantRegistry.getString(ConstantEnum.LINE_SEPARATOR);
    }

    protected boolean separatorAdaptive() {
        return false;
    }

    protected boolean headSeparator() {
        return true;
    }

    @Override
    public String code() {
        if (CollectionUtil.isEmpty(codes)) {
            return this.empty();
        }
        StringBuilder sb = new StringBuilder(this.identStr()).append(this.pre());
        sb.append(this.startCharacter());
        String lineSeparator = this.contentSeparator();
        if (this.headSeparator()) {
            sb.append(lineSeparator);
        }
        for (E code : codes) {
            sb.append(code.full()).append(lineSeparator);
        }
        if (this.separatorAdaptive()) {
            int len = sb.length();
            sb.delete(len - lineSeparator.length(), len);
        }
        sb.append(this.identStr()).append(this.terminator());
        return sb.append(this.spacer()).toString();
    }

    @Override
    public String full() {
        return this.code();
    }
}
