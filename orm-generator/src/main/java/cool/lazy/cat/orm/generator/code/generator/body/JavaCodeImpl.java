package cool.lazy.cat.orm.generator.code.generator.body;

/**
 * @author : jason.ma
 * @date : 2022/7/14 10:45
 */
public class JavaCodeImpl extends AbstractCode implements JavaCode {

    protected String starterCharacter = "";

    protected String terminator = "";

    protected String spacer = "";

    public JavaCodeImpl(String code) {
        super(code);
    }

    public JavaCodeImpl(String code, String terminator) {
        super(code);
        this.terminator = terminator;
    }

    public JavaCodeImpl(String code, String starterCharacter, String terminator) {
        super(code);
        this.starterCharacter = starterCharacter;
        this.terminator = terminator;
    }

    public JavaCodeImpl(String code, String starterCharacter, String terminator, String spacer) {
        super(code);
        this.starterCharacter = starterCharacter;
        this.terminator = terminator;
        this.spacer = spacer;
    }

    @Override
    public String startCharacter() {
        return starterCharacter;
    }

    @Override
    public String terminator() {
        return terminator;
    }

    @Override
    public String spacer() {
        return spacer;
    }
}
