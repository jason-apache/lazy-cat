package cool.lazy.cat.orm.generator.dialect.extractor;

/**
 * @author : jason.ma
 * @date : 2022/7/13 11:28
 */
class ColumnRawData extends AbstractRawData implements RawData {
    @Field(name = "TABLE_CAT")
    String tableCat;
    @Field(name = "TABLE_SCHEM")
    String tableSchem;
    @Field(name = "TABLE_NAME")
    String tableName;
    @Field(name = "COLUMN_NAME")
    String columnName;
    @Field(name = "DATA_TYPE")
    Integer dataType;
    @Field(name = "TYPE_NAME")
    String typeName;
    @Field(name = "COLUMN_SIZE")
    Integer columnSize;
    @Field(name = "BUFFER_LENGTH")
    String bufferLength;
    @Field(name = "DECIMAL_DIGITS")
    Integer decimalDigits;
    @Field(name = "NUM_PREC_RADIX")
    Integer numPrecRadix;
    @Field(name = "NULLABLE")
    Integer nullable;
    @Field(name = "REMARKS")
    String remarks;
    @Field(name = "COLUMN_DEF")
    String columnDef;
    @Field(name = "SQL_DATA_TYPE")
    Integer sqlDataType;
    @Field(name = "SQL_DATETIME_SUB")
    Integer sqlDatetimeSub;
    @Field(name = "CHAR_OCTET_LENGTH")
    Integer charOctetLength;
    @Field(name = "ORDINAL_POSITION")
    Integer ordinalPosition;
    @Field(name = "IS_NULLABLE")
    String isNullable;
    @Field(name = "SCOPE_CATALOG")
    String scopeCatalog;
    @Field(name = "SCOPE_SCHEMA")
    String scopeSchema;
    @Field(name = "SCOPE_TABLE")
    String scopeTable;
    @Field(name = "SOURCE_DATA_TYPE")
    Short sourceDataType;
    @Field(name = "IS_AUTOINCREMENT")
    String isAutoincrement;
    @Field(name = "IS_GENERATEDCOLUMN")
    String isGeneratedcolumn;

    @Override
    public String toString() {
        return "{" + "\"tableCat\":\"" + tableCat + '\"' + ",\"tableSchem\":\"" + tableSchem + '\"' + ",\"tableName\":\"" + tableName + '\"' + ",\"columnName\":\"" + columnName + '\"' + ",\"dataType\":" + dataType + ",\"typeName\":\"" + typeName + '\"' + ",\"columnSize\":" + columnSize + ",\"bufferLength\":\"" + bufferLength + '\"' + ",\"decimalDigits\":" + decimalDigits + ",\"numPrecRadix\":" + numPrecRadix + ",\"nullable\":" + nullable + ",\"remarks\":\"" + remarks + '\"' + ",\"columnDef\":\"" + columnDef + '\"' + ",\"sqlDataType\":" + sqlDataType + ",\"sqlDatetimeSub\":" + sqlDatetimeSub + ",\"charOctetLength\":" + charOctetLength + ",\"ordinalPosition\":" + ordinalPosition + ",\"isNullable\":\"" + isNullable + '\"' + ",\"scopeCatalog\":\"" + scopeCatalog + '\"' + ",\"scopeSchema\":\"" + scopeSchema + '\"' + ",\"scopeTable\":\"" + scopeTable + '\"' + ",\"sourceDataType\":" + sourceDataType + ",\"isAutoincrement\":\"" + isAutoincrement + '\"' + ",\"isGeneratedcolumn\":\"" + isGeneratedcolumn + '\"' + super.toString() + '}';
    }
}
