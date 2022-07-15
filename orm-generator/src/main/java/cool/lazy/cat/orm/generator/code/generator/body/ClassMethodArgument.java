package cool.lazy.cat.orm.generator.code.generator.body;

/**
 * @author : jason.ma
 * @date : 2022/7/14 15:00
 */
public class ClassMethodArgument extends AbstractCompoundElement<JavaCode> {

    @Override
    protected String empty() {
        return this.startCharacter() + this.terminator() + this.spacer();
    }

    @Override
    public String spacer() {
        return " ";
    }

    @Override
    public String startCharacter() {
        return "(";
    }

    @Override
    public String terminator() {
        return ")";
    }

    @Override
    protected String contentSeparator() {
        return ", ";
    }

    @Override
    protected boolean separatorAdaptive() {
        return true;
    }

    @Override
    protected boolean headSeparator() {
        return false;
    }
}
