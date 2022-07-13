package cool.lazy.cat.orm.generator.dialect.extractor;

import cool.lazy.cat.orm.generator.config.ScanningConfig;
import cool.lazy.cat.orm.generator.dialect.Dialect;
import cool.lazy.cat.orm.generator.exception.GetDatabaseInfoException;
import cool.lazy.cat.orm.generator.exception.GetResultSetMetaDataException;
import cool.lazy.cat.orm.generator.exception.ReflectException;
import cool.lazy.cat.orm.generator.info.TableFieldInfo;
import cool.lazy.cat.orm.generator.info.TableInfo;
import cool.lazy.cat.orm.generator.jdbc.ConnectionManager;
import cool.lazy.cat.orm.generator.util.CollectionUtil;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author : jason.ma
 * @date : 2022/7/13 11:23
 */
public abstract class AbstractTableInfoExtractor implements TableInfoExtractor {

    protected final Dialect dialect;

    protected AbstractTableInfoExtractor(Dialect dialect) {
        this.dialect = dialect;
    }
    protected static final Map<Class<?>, Map<String, FieldAnnotationInfo>> CLASS_FIELD_INFO_MAP;
    protected static final Map<Class<?>, List<ColumnLabel>> RESULT_SET_COLUMN_LABEL_CACHE = new HashMap<>();

    static {
        CLASS_FIELD_INFO_MAP = new HashMap<>();
        CLASS_FIELD_INFO_MAP.put(TableRawData.class, buildClassFieldInfo(TableRawData.class));
        CLASS_FIELD_INFO_MAP.put(ColumnRawData.class, buildClassFieldInfo(ColumnRawData.class));
        CLASS_FIELD_INFO_MAP.put(PrimaryKeyRawData.class, buildClassFieldInfo(PrimaryKeyRawData.class));
    }

    @Override
    public List<TableInfo> extractTableInfo(ScanningConfig scanningConfig) {
        try {
            Connection connection = ConnectionManager.getConnection();
            List<TableInfo> tableInfoList = this.extractTableRawData(connection, scanningConfig.getTableScanningConfig()).stream().map(this::buildTableInfo).collect(Collectors.toList());
            Map<String, List<ColumnRawData>> columnGroupByTable = this.extractColumnRawData(connection, scanningConfig.getColumnScanningConfig()).stream().collect(Collectors.groupingBy(r -> r.tableName));
            Map<String, List<PrimaryKeyRawData>> primaryKeyGroupByTable = this.extractPrimaryKeyRawData(connection, tableInfoList).stream().collect(Collectors.groupingBy(r -> r.tableName));
            for (TableInfo tableInfo : tableInfoList) {
                tableInfo.getFields().addAll(this.buildTableFieldInfos(columnGroupByTable.get(tableInfo.getName()), primaryKeyGroupByTable.get(tableInfo.getName())));
                tableInfo.setHavePrimaryKey(tableInfo.getFields().stream().anyMatch(TableFieldInfo::isPrimaryKey));
            }
            return tableInfoList;
        } catch (SQLException e) {
            throw new GetDatabaseInfoException("提取数据库表信息失败", e);
        }
    }

    protected TableInfo buildTableInfo(TableRawData raw) {
        return new TableInfo(raw.tableCat, raw.tableSchem, raw.tableName, raw.remarks);
    }

    protected List<TableFieldInfo> buildTableFieldInfos(List<ColumnRawData> columnRawDataList, List<PrimaryKeyRawData> primaryKeyRawDataList) throws SQLException {
        if (CollectionUtil.isEmpty(columnRawDataList)) {
            return Collections.emptyList();
        }
        // 只取表中第一个主键列名称
        String pkName = CollectionUtil.isEmpty(primaryKeyRawDataList) ? null : primaryKeyRawDataList.get(0).columnName;
        return columnRawDataList.stream().map(ca -> this.buildTableFieldInfo(ca, pkName)).collect(Collectors.toList());
    }

    protected TableFieldInfo buildTableFieldInfo(ColumnRawData raw, String pkName) {
        return TableFieldInfo.builder().name(raw.columnName).type(raw.dataType).length(raw.columnSize)
                .nullable(raw.nullable == DatabaseMetaData.columnNullable).defaultValue(raw.columnDef).scale(raw.decimalDigits)
                .comment(raw.remarks).keyWord(this.getDialect().getKeywordMap().isKeyword(raw.columnName)).primaryKey(Objects.equals(pkName, raw.columnName))
                .autoIncrement(this.getDialect().isAutoIncrement(raw.isAutoincrement))
                .build();
    }

    protected List<TableRawData> extractTableRawData(Connection connection, ScanningConfig.TableScanningConfig tableScanningConfig) throws SQLException {
        String[] types = (null == tableScanningConfig || tableScanningConfig.isSkipView()) ? new String[] {"TABLE"} : new String[] {"TABLE", "VIEW"};
        ResultSet resultSet = connection.getMetaData().getTables(connection.getCatalog(), connection.getSchema(), null, types);
        List<TableRawData> tableRawData = this.extractRawData(resultSet, TableRawData.class);
        if (null != tableScanningConfig && null != tableScanningConfig.getStrategyList()) {
            tableRawData = tableRawData.stream().filter(tr -> {
                for (ScanningConfig.ScanningStrategy strategy : tableScanningConfig.getStrategyList()) {
                    if (!strategy.match(tr.tableName)) {
                        return false;
                    }
                }
                return true;
            }).collect(Collectors.toList());
        }
        return tableRawData;
    }

    protected List<ColumnRawData> extractColumnRawData(Connection connection, ScanningConfig.ColumnScanningConfig columnScanningConfig) throws SQLException {
        ResultSet resultSet = connection.getMetaData().getColumns(connection.getCatalog(), connection.getSchema(), null, null);
        List<ColumnRawData> columnRawData = this.extractRawData(resultSet, ColumnRawData.class);
        if (null != columnScanningConfig && null != columnScanningConfig.getStrategyList()) {
            columnRawData = columnRawData.stream().filter(tr -> {
                for (ScanningConfig.ScanningStrategy strategy : columnScanningConfig.getStrategyList()) {
                    if (!strategy.match(tr.columnName)) {
                        return false;
                    }
                }
                return true;
            }).collect(Collectors.toList());
        }
        return columnRawData;
    }

    protected List<PrimaryKeyRawData> extractPrimaryKeyRawData(Connection connection, List<TableInfo> tableInfoList) throws SQLException {
        DatabaseMetaData metaData = connection.getMetaData();
        List<PrimaryKeyRawData> result = new ArrayList<>();
        for (TableInfo tableInfo : tableInfoList) {
            ResultSet resultSet = metaData.getPrimaryKeys(connection.getCatalog(), connection.getSchema(), tableInfo.getName());
            result.addAll(this.extractRawData(resultSet, PrimaryKeyRawData.class));
        }
        return result;
    }

    protected <T extends RawData> List<T> extractRawData(ResultSet resultSet, Class<T> type) throws SQLException {
        List<T> result = new ArrayList<>();
        Map<String, FieldAnnotationInfo> infoMap = CLASS_FIELD_INFO_MAP.get(type);
        try {
            ResultSetMetaData metaData = resultSet.getMetaData();
            while (resultSet.next()) {
                T raw = type.newInstance();
                for (ColumnLabel label : this.buildLabels(type, metaData)) {
                    FieldAnnotationInfo fieldAnnotationInfo = infoMap.get(label.getName());
                    if (null == fieldAnnotationInfo) {
                        raw.getRaw().put(label.getName(), resultSet.getObject(label.getIndex()));
                    } else {
                        java.lang.reflect.Field field = fieldAnnotationInfo.getField();
                        field.setAccessible(true);
                        field.set(raw, resultSet.getObject(label.getIndex(), field.getType()));
                    }
                }
                result.add(raw);
            }
            return result;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new ReflectException("反射异常", e);
        }
    }

    protected static Map<String, FieldAnnotationInfo> buildClassFieldInfo(Class<?> type) {
        return Arrays.stream(type.getDeclaredFields()).filter(f -> f.getAnnotation(Field.class) != null)
                .map(f -> new FieldAnnotationInfo(f, f.getAnnotation(Field.class)))
                .collect(Collectors.toMap(f -> f.annotation.name(), Function.identity()));
    }

    /**
     * <p>调用不同的查询api返回的结果集风格迥异，但不需要每次调用都生成一遍标签</p>
     * <p>因此将每次返回结果集按分组缓存</p>
     * @param type 绑定类型
     * @param metaData 结果集元信息
     * @return 列标签集合
     */
    protected List<ColumnLabel> buildLabels(Class<?> type, ResultSetMetaData metaData) {
        return RESULT_SET_COLUMN_LABEL_CACHE.computeIfAbsent(type, k -> {
            List<ColumnLabel> result;
            try {
                result = new ArrayList<>(metaData.getColumnCount() -1);
                for (int i = 1; i < metaData.getColumnCount(); i++) {
                    result.add(new ColumnLabel(metaData.getColumnLabel(i), i));
                }
            } catch (SQLException e) {
                throw new GetResultSetMetaDataException(e);
            }
            return result;
        });
    }

    @Override
    public Dialect getDialect() {
        return dialect;
    }
}
