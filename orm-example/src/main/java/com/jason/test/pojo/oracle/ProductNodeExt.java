package com.jason.test.pojo.oracle;

import com.jason.test.annotation.DataSource;
import com.jason.test.constant.Constant;
import cool.lazy.cat.orm.annotation.Column;
import cool.lazy.cat.orm.annotation.Id;
import cool.lazy.cat.orm.annotation.Parameter;
import cool.lazy.cat.orm.annotation.Pojo;
import cool.lazy.cat.orm.annotation.Table;
import cool.lazy.cat.orm.core.jdbc.component.id.SequenceIdGenerator;
import lombok.Data;

@DataSource(id = Constant.ORACLE)
@Pojo(table = @Table(tableName = "MD_PRODUCT_NODE_EXT", schema = "mstdata"))
@Data
public class ProductNodeExt {

	private static final long serialVersionUID = 37769369083455312L;
	private Long id;
	private String color;

    @Id(idGenerator = SequenceIdGenerator.class, parameters = {@Parameter(name = Constant.SEQUENCE_SCHEMA, value = "mstdata"), @Parameter(name = Constant.SEQUENCE_NAME, value = "SEQ_MD_PRODUCT_NODE_EXT_ID")})
    @Column(name = "MD_PRODUCT_NODE_EXT_ID")
    public Long getId() {
        return this.id;
    }

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "COLOR")
	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
}
