package com.jason.test.pojo.oracle;

import com.jason.test.constant.Constant;
import com.jason.test.annotation.DataSource;
import cool.lazy.cat.orm.core.base.annotation.Column;
import cool.lazy.cat.orm.core.base.annotation.Id;
import cool.lazy.cat.orm.core.base.annotation.Parameter;
import cool.lazy.cat.orm.core.base.annotation.Pojo;
import cool.lazy.cat.orm.core.base.annotation.Table;
import cool.lazy.cat.orm.core.jdbc.component.id.SequenceIdGenerator;
import lombok.Data;

@DataSource(id = Constant.ORACLE)
@Pojo(table = @Table(tableName = "MD_PRODUCT_TYPE_EXT", schema = "mstdata"))
@Data
public class ProductTypeExt {

	private static final long	serialVersionUID	= -4689189114227286330L;
	private Long				id;

	public ProductTypeExt() {
	}

    @Id(idGenerator = SequenceIdGenerator.class, parameters = {@Parameter(name = Constant.SEQUENCE_SCHEMA, value = "mstdata"), @Parameter(name = Constant.SEQUENCE_NAME, value = "SEQ_MD_PRODUCT_TYPE_EXT_ID")})
    @Column(name = "MD_PRODUCT_TYPE_EXT_ID")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
