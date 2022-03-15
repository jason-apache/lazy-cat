package com.jason.test.pojo.oracle;

import com.jason.test.constant.Constant;
import com.jason.test.annotation.DataSource;
import cool.lazy.cat.orm.annotation.Column;
import cool.lazy.cat.orm.annotation.Id;
import cool.lazy.cat.orm.annotation.On;
import cool.lazy.cat.orm.annotation.OneToOne;
import cool.lazy.cat.orm.annotation.Parameter;
import cool.lazy.cat.orm.annotation.Pojo;
import cool.lazy.cat.orm.annotation.Table;
import cool.lazy.cat.orm.core.jdbc.component.id.SequenceIdGenerator;
import lombok.Data;


@DataSource(id = Constant.ORACLE)
@Pojo(table = @Table(tableName = "MD_PRODUCT_TYPE", schema = "mstdata"))
@Data
public class ProductType {

    private static final long serialVersionUID = -1357974339839791401L;
    private Long id;
	private String typeCode;
	private String typeName;
	private Long productTypeExtId;
	private ProductTypeExt productTypeExt = new ProductTypeExt();

    @Id(idGenerator = SequenceIdGenerator.class, parameters = {@Parameter(name = Constant.SEQUENCE_SCHEMA, value = "mstdata"), @Parameter(name = Constant.SEQUENCE_NAME, value = "SEQ_MD_PRODUCT_TYPE_ID")})
    @Column(name = "MD_PRODUCT_TYPE_ID")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "TYPE_CODE")
	public String getTypeCode() {
		return this.typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	@Column(name = "TYPE_NAME")
	public String getTypeName() {
		return this.typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	@Column(name = "PRODUCT_TYPE_EXT_ID")
	public Long getProductTypeExtId() {
		return productTypeExtId;
	}

	public void setProductTypeExtId(Long productTypeExtId) {
		this.productTypeExtId = productTypeExtId;
	}

    @OneToOne(condition = {@On(foreignFiled = "productTypeExtId", targetFiled = "id")})
	public ProductTypeExt getProductTypeExt() {
		return productTypeExt;
	}

	public void setProductTypeExt(ProductTypeExt productTypeExt) {
		this.productTypeExt = productTypeExt;
	}
}
