package cool.lazy.cat.orm.generator.dialect.extractor;

/**
 * @author : jason.ma
 * @date : 2022/7/13 11:28
 */
class PrimaryKeyRawData extends AbstractRawData implements RawData {

    @Field(name = "TABLE_CAT")
    String tableCat;
    @Field(name = "TABLE_SCHEM")
    String tableSchem;
    @Field(name = "TABLE_NAME")
    String tableName;
    @Field(name = "COLUMN_NAME")
    String columnName;
    @Field(name = "KEY_SEQ")
    Short keySeq;
    @Field(name = "PK_NAME")
    Short pkName;

    @Override
    public String toString() {
        return "{" + "\"tableCat\":\"" + tableCat + '\"' + ",\"tableSchem\":\"" + tableSchem + '\"' + ",\"tableName\":\"" + tableName + '\"' + ",\"columnName\":\"" + columnName + '\"' + ",\"keySeq\":" + keySeq + ",\"pkName\":" + pkName + super.toString() + '}';
    }
}
