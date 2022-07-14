package cool.lazy.cat.orm.generator.code.generator.event;

import cool.lazy.cat.orm.generator.code.generator.structure.JavaClassStructure;
import cool.lazy.cat.orm.generator.dialect.Dialect;
import cool.lazy.cat.orm.generator.info.TableInfo;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author : jason.ma
 * @date : 2022/7/13 17:01
 */
public class EventParam extends LinkedHashMap<String, Object> {
    private static final long serialVersionUID = 9017903569039511849L;

    private List<TableInfo> allTable;
    private TableInfo currentTable;
    private Dialect dialect;
    private JavaClassStructure javaClassStructure;

    public List<TableInfo> getAllTable() {
        return allTable;
    }

    public EventParam setAllTable(List<TableInfo> allTable) {
        this.allTable = allTable;
        return this;
    }

    public TableInfo getCurrentTable() {
        return currentTable;
    }

    public EventParam setCurrentTable(TableInfo currentTable) {
        this.currentTable = currentTable;
        return this;
    }

    public Dialect getDialect() {
        return dialect;
    }

    public EventParam setDialect(Dialect dialect) {
        this.dialect = dialect;
        return this;
    }

    public JavaClassStructure getCodeStructure() {
        return javaClassStructure;
    }

    public EventParam setCodeStructure(JavaClassStructure javaClassStructure) {
        this.javaClassStructure = javaClassStructure;
        return this;
    }
}
