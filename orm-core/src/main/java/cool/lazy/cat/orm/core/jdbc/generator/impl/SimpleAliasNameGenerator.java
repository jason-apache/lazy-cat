package cool.lazy.cat.orm.core.jdbc.generator.impl;


import cool.lazy.cat.orm.core.jdbc.JdbcConstant;
import cool.lazy.cat.orm.core.jdbc.generator.AliasNameGenerator;

/**
 * @author: mahao
 * @date: 2021/3/20 11:41
 */
public class SimpleAliasNameGenerator implements AliasNameGenerator {

    final static int[] SIZE_TABLE = { 9, 99, 999, 9999, 99999, 999999, 9999999, 99999999, 999999999, Integer.MAX_VALUE };

    static int sizeOfInt(int x) {
        for (int i = 0;; i++) {
            if (x <= SIZE_TABLE[i]) {
                return i + 1;
            }
        }
    }

    @Override
    public String generatorTableName(String tableName, int index) {
        tableName = tableName + "_" + index;
        if (tableName.length() > JdbcConstant.MAX_TABLE_NAME_LENGTH) {
            tableName = tableName.substring(0, JdbcConstant.MAX_TABLE_NAME_LENGTH - sizeOfInt(index) - 1) + "_" + index;
        }
        return tableName;
    }

    @Override
    public String generatorFiledName(String fieldName, int index) {
        fieldName = fieldName + "_" + index;
        if (fieldName.length() > JdbcConstant.MAX_FIELD_NAME_LENGTH) {
            fieldName = fieldName.substring(0, JdbcConstant.MAX_FIELD_NAME_LENGTH - sizeOfInt(index) - 1) + "_" + index;
        }
        return fieldName;
    }
}
