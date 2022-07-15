package cool.lazy.cat.orm.generator.code.generator.body;

import cool.lazy.cat.orm.generator.constant.ConstantEnum;
import cool.lazy.cat.orm.generator.constant.ConstantRegistry;

import java.util.Collections;

/**
 * @author : jason.ma
 * @date : 2022/7/13 14:43
 */
public interface JavaCode {

    /**
     * @return 起始符
     */
    default String startCharacter() {
        return "";
    }
    /**
     * @return 终止符
     */
    String terminator();

    /**
     * @return 间隔符
     */
    default String spacer() {
        return ConstantRegistry.getString(ConstantEnum.CODE_ELEMENT_SEPARATOR);
    }

    /**
     * @return 缩进
     */
    default int indent() {
        return 0;
    }

    default String identStr() {
        return this.indent() > 0 ? String.join("", Collections.nCopies(this.indent(), ConstantRegistry.getString(ConstantEnum.INDENT))) : "";
    }

    /**
     * @return 代码
     */
    String code();

    default String full() {
        return this.identStr() + this.startCharacter() + this.code() + this.terminator() + this.spacer();
    }
}
