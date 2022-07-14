package cool.lazy.cat.orm.generator.code.generator.body;

import cool.lazy.cat.orm.generator.info.TypeInfo;

import java.util.List;

/**
 * @author : jason.ma
 * @date : 2022/7/14 14:09
 */
public interface Importable extends JavaCode {

    /**
     * @return 需要导入的依赖项
     */
    List<TypeInfo> importTypes();
}
