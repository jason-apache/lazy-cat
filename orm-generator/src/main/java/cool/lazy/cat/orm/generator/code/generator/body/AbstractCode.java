package cool.lazy.cat.orm.generator.code.generator.body;

/**
 * @author : jason.ma
 * @date : 2022/7/13 15:26
 */
public abstract class AbstractCode implements JavaCode {

    private final String code;

    protected AbstractCode(String code) {
        this.code = code;
    }

    @Override
    public String code() {
        return code;
    }
}
