package cool.lazy.cat.orm.core.jdbc.mapping;


import cool.lazy.cat.orm.core.base.annotation.Sequence;
import cool.lazy.cat.orm.core.base.util.StringUtil;
import cool.lazy.cat.orm.core.jdbc.component.id.IdGenerator;

/**
 * @author: mahao
 * @date: 2021/3/30 19:45
 * pojo主键
 */
public class IdStrategy extends TableFieldInfo {

    /**
     * id生成器类型
     */
    private Class<? extends IdGenerator> idGenerator;
    /**
     * 序列信息
     */
    private SequenceInfo sequenceInfo;

    public Class<? extends IdGenerator> getIdGenerator() {
        return idGenerator;
    }

    public IdStrategy setIdGenerator(Class<? extends IdGenerator> idGenerator) {
        this.idGenerator = idGenerator;
        return this;
    }

    public void setSequenceInfo(Sequence sequence) {
        if (StringUtil.isNotBlank(sequence.name())) {
            this.sequenceInfo = new SequenceInfo(sequence);
        }
    }

    public SequenceInfo getSequenceInfo() {
        return this.sequenceInfo;
    }

    public boolean havingSequence() {
        return null != this.sequenceInfo;
    }
}
