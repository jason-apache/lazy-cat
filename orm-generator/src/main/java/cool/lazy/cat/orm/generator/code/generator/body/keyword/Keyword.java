package cool.lazy.cat.orm.generator.code.generator.body.keyword;

import cool.lazy.cat.orm.generator.code.generator.body.JavaCode;

/**
 * @author : jason.ma
 * @date : 2022/7/13 15:51
 */
public interface Keyword extends JavaCode {

    @Override
    default String startCharacter() {
        return "";
    }

    @Override
    default String terminator() {
        return " ";
    }
}
