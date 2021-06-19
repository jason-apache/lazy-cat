package cool.lazy.cat.orm.core.base.repository.impl;

import cool.lazy.cat.orm.core.base.bo.PageResult;
import cool.lazy.cat.orm.core.base.bo.QueryInfo;
import cool.lazy.cat.orm.core.base.repository.BaseRepository;
import cool.lazy.cat.orm.core.base.util.Caster;
import cool.lazy.cat.orm.core.base.util.CollectionUtil;
import cool.lazy.cat.orm.core.base.util.Ignorer;
import cool.lazy.cat.orm.core.jdbc.JdbcConstant;
import cool.lazy.cat.orm.core.jdbc.OrderBy;
import cool.lazy.cat.orm.core.jdbc.condition.Condition;
import cool.lazy.cat.orm.core.jdbc.dto.CascadeLevelMapper;
import cool.lazy.cat.orm.core.jdbc.holder.SqlParamHolder;
import cool.lazy.cat.orm.core.jdbc.holder.TableChainHolder;
import cool.lazy.cat.orm.core.jdbc.mapping.PojoMapping;
import cool.lazy.cat.orm.core.jdbc.mapping.TableInfo;
import cool.lazy.cat.orm.core.jdbc.param.SimpleSearchParam;
import cool.lazy.cat.orm.core.jdbc.param.SimpleUpdateParam;
import cool.lazy.cat.orm.core.jdbc.param.UpdateParam;
import cool.lazy.cat.orm.core.jdbc.util.TableChainBuildHelper;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.support.KeyHolder;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @author: mahao
 * @date: 2021/3/4 22:00
 */
public class BaseRepositoryImpl<P> extends AbstractRepository<P> implements BaseRepository<P> {

    @Override
    public List<P> selectByInfo(QueryInfo queryInfo) {
        return Caster.cast(super.queryByInfo(queryInfo));
    }

    @Override
    public List<P> select(Condition condition, Ignorer ignorer) {
        return this.select(condition, null, ignorer);
    }

    @Override
    public List<P> select(Condition condition, OrderBy orderBy, Ignorer ignorer) {
        return Caster.cast(super.query(getPojoType(), condition, orderBy, ignorer));
    }

    @Override
    public P selectSingle(Condition condition, Ignorer ignorer) {
        List<P> result = this.select(condition, ignorer);
        return DataAccessUtils.nullableSingleResult(result);
    }

    @Override
    public <T> List<T> select(Class<T> pojoType, Condition condition, Ignorer ignorer) {
        return this.select(pojoType, condition, null, ignorer);
    }

    @Override
    public <T> List<T> select(Class<T> pojoType, Condition condition, OrderBy orderBy, Ignorer ignorer) {
        return super.query(pojoType, condition, orderBy, ignorer);
    }

    @Override
    public <T> T selectSingle(Class<T> pojoType, Condition condition, Ignorer ignorer) {
        List<T> result = this.select(pojoType, condition, ignorer);
        return DataAccessUtils.nullableSingleResult(result);
    }

    @Override
    public PageResult<P> selectPage(QueryInfo queryInfo) {
        return Caster.cast(super.queryPage(queryInfo));
    }

    @Override
    public PageResult<P> selectPage(Condition condition, int index, int pageSize, Ignorer ignorer) {
        return this.selectPage(condition, index, pageSize, null, ignorer);
    }

    @Override
    public PageResult<P> selectPage(Condition condition, int index, int pageSize, OrderBy orderBy, Ignorer ignorer) {
        return Caster.cast(super.queryPage(getPojoType(), condition, index, pageSize, orderBy, ignorer));
    }

    @Override
    public <T> PageResult<T> selectPage(Class<T> pojoType, Condition condition, int index, int pageSize, Ignorer ignorer) {
        return this.selectPage(pojoType, condition, index, pageSize, null, ignorer);
    }

    @Override
    public <T> PageResult<T> selectPage(Class<T> pojoType,  Condition condition, int index, int pageSize, OrderBy orderBy, Ignorer ignorer) {
        return super.queryPage(pojoType, condition, index, pageSize, orderBy, ignorer);
    }

    @Override
    public <T> List<T> selectByStructure(Class<T> pojoType, int cascadeLevel, Condition condition, OrderBy orderBy, Ignorer ignorer) {
        return this.selectByStructure(pojoType, CascadeLevelMapper.buildAll(cascadeLevel), condition, orderBy, ignorer);
    }

    @Override
    public <T> List<T> selectByStructure(Class<T> pojoType, CascadeLevelMapper cascadeLevelMapper, Condition condition, OrderBy orderBy, Ignorer ignorer) {
        TableInfo tableInfo = pojoTableManager.getByPojoType(pojoType).getTableInfo();
        List<PojoMapping> mappings = CollectionUtil.concat(tableInfo.getOneToOneMapping(), tableInfo.getOneToManyMapping(), tableInfo.getManyToOneMapping());
        TableChainHolder holder = TableChainBuildHelper.build(tableInfo, mappings, pojoTableManager.getAllSubjectMap(), cascadeLevelMapper);
        if (holder.getFlatChain().size() > JdbcConstant.TABLE_CHAIN_WARN_COUNT) {
            logger.warn("级联查询表已超过：" + JdbcConstant.TABLE_CHAIN_WARN_COUNT + "\t请注意对不需要的字段、对象忽略查询#" + pojoType.getName());
        }
        SimpleSearchParam<T> sqlSearchParam = new SimpleSearchParam<T>(pojoTableManager.getByPojoType(pojoType).getTableInfo());
        sqlSearchParam.setPojoType(pojoType).setCondition(condition == null ? Condition.EMPTY_CONDITION : condition).setOrderBy(orderBy)
                .setIgnorer(ignorer).setNestedChain(holder.getNestedChain()).setFlatChain(holder.getFlatChain());
        return super.query(sqlSearchParam);
    }

    @Override
    public int insert(P pojo) {
        SqlParamHolder holder = super.sqlParamProvider.getInsertSql(new SimpleUpdateParam().setData(pojo));
        return jdbcTemplate.update(holder.getSql(), holder.getParamSource());
    }

    @Override
    public KeyHolder insert(P pojo, KeyHolder keyHolder) {
        SqlParamHolder holder = super.sqlParamProvider.getInsertSql(new SimpleUpdateParam().setData(pojo));
        jdbcTemplate.update(holder.getSql(), holder.getParamSource(), keyHolder);
        return keyHolder;
    }

    @Override
    public int batchInsert(Collection<P> pojoList) {
        SqlParamHolder holder = super.sqlParamProvider.getInsertSql(new SimpleUpdateParam().setData(pojoList));
        int[] affectedRows = jdbcTemplate.batchUpdate(holder.getSql(), holder.getParamSources());
        return Arrays.stream(affectedRows).sum();
    }

    @Override
    public int update(P pojo, Condition condition, boolean ignoreNull, String ... ignoreFields) {
        return this.updateByParam(new SimpleUpdateParam(null, condition, ignoreNull, ignoreFields, pojo));
    }

    @Override
    public int updateByParam(UpdateParam updateParam) {
        SqlParamHolder holder = super.sqlParamProvider.getUpdateSql(updateParam);
        return jdbcTemplate.update(holder.getSql(), holder.getParamSource());
    }

    @Override
    public <T> int delete(Class<T> pojoType, Condition condition) {
        SqlParamHolder holder = super.sqlParamProvider.getDeleteSql(new SimpleUpdateParam(pojoType, condition, false, null, null));
        return jdbcTemplate.update(holder.getSql(), holder.getParam());
    }

    @Override
    public int logicDelete(P pojo, Condition condition) {
        SqlParamHolder holder = super.sqlParamProvider.getLogicDeleteSql(new SimpleUpdateParam(null, condition, false, null, pojo));
        return jdbcTemplate.update(holder.getSql(), holder.getParamSource());
    }
}