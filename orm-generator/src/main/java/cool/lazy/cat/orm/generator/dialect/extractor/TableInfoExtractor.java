package cool.lazy.cat.orm.generator.dialect.extractor;

import cool.lazy.cat.orm.generator.config.ScanningConfig;
import cool.lazy.cat.orm.generator.dialect.Dialect;
import cool.lazy.cat.orm.generator.info.TableInfo;

import java.util.List;

/**
 * 表信息提取器
 * @author : jason.ma
 * @date : 2022/7/13 11:22
 */
public interface TableInfoExtractor {

    /**
     * @param scanningConfig 扫描配置
     * @return 表信息
     */
    List<TableInfo> extractTableInfo(ScanningConfig scanningConfig);

    /**
     * @return 数据库方言
     */
    Dialect getDialect();
}
