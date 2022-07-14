package cool.lazy.cat.orm.generator.code.generator.body;

/**
 * @author : jason.ma
 * @date : 2022/7/13 14:45
 */
public interface Line extends JavaCode {

    @Override
    default String startCharacter() {
        return "";
    }

    @Override
    default String terminator() {
        return ";";
    }
}
