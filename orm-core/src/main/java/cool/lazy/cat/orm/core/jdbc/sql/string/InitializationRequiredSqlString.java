package cool.lazy.cat.orm.core.jdbc.sql.string;

/**
 * @author: mahao
 * @date: 2021/7/28 16:37
 * sqlString实例至少需要初始化一次
 */
public interface InitializationRequiredSqlString extends SqlString {

    /**
     * @return 是否已初始化
     */
    boolean initialized();

    /**
     * @param initialization 是否已初始化
     */
    void setInitialization(boolean initialization);

    /**
     * @return 卡纸原因: 未初始化
     */
    @Override
    default boolean paperJam() {
        return !initialized();
    }
}
