package cool.lazy.cat.orm.generator.dialect.extractor;

/**
 * @author : jason.ma
 * @date : 2022/7/13 11:29
 */
class TableRawData extends AbstractRawData implements RawData {
    @Field(name = "TABLE_CAT")
    String tableCat;
    @Field(name = "TABLE_SCHEM")
    String tableSchem;
    @Field(name = "TABLE_NAME")
    String tableName;
    @Field(name = "TABLE_TYPE")
    String tableType;
    @Field(name = "REMARKS")
    String remarks;
    @Field(name = "TYPE_CAT")
    String typeCat;
    @Field(name = "TYPE_SCHEM")
    String typeSchem;
    @Field(name = "TYPE_NAME")
    String typeName;
    @Field(name = "SELF_REFERENCING_COL_NAME")
    String selfReferencingColName;
    @Field(name = "REF_GENERATION")
    String refGeneration;

    @Override
    public String toString() {
        return "{" + "\"tableCat\":\"" + tableCat + '\"' + ",\"tableSchem\":\"" + tableSchem + '\"' + ",\"tableName\":\"" + tableName + '\"' + ",\"tableType\":\"" + tableType + '\"' + ",\"remarks\":\"" + remarks + '\"' + ",\"typeCat\":\"" + typeCat + '\"' + ",\"typeSchem\":\"" + typeSchem + '\"' + ",\"typeName\":\"" + typeName + '\"' + ",\"selfReferencingColName\":\"" + selfReferencingColName + '\"' + ",\"refGeneration\":\"" + refGeneration + '\"' + super.toString() + '}';
    }
}
