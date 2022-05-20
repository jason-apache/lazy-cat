package com.jason.test.pojo.oracle;

import com.jason.test.annotation.DataSource;
import com.jason.test.constant.Constant;
import cool.lazy.cat.orm.annotation.Column;
import cool.lazy.cat.orm.annotation.Id;
import cool.lazy.cat.orm.annotation.ManyToOne;
import cool.lazy.cat.orm.annotation.On;
import cool.lazy.cat.orm.annotation.OneToOne;
import cool.lazy.cat.orm.annotation.Parameter;
import cool.lazy.cat.orm.annotation.Pojo;
import cool.lazy.cat.orm.annotation.Table;
import cool.lazy.cat.orm.api.base.anno.ApiPojo;
import cool.lazy.cat.orm.api.base.anno.ApiQueryFilter;
import cool.lazy.cat.orm.api.base.anno.Entry;
import cool.lazy.cat.orm.api.web.entrust.method.QueryPageApiEntry;
import cool.lazy.cat.orm.api.web.entrust.method.RemoveCascadeApiEntry;
import cool.lazy.cat.orm.api.web.entrust.method.SaveCascadeApiEntry;
import cool.lazy.cat.orm.core.jdbc.component.convert.OracleLocalDateTimeConverter;
import cool.lazy.cat.orm.core.jdbc.component.id.SequenceIdGenerator;
import cool.lazy.cat.orm.core.jdbc.sql.condition.type.In;
import lombok.Data;

import java.time.LocalDateTime;

@DataSource(id = Constant.ORACLE)
@Pojo(table = @Table(tableName = "MD_PRODUCT_NODE", schema = "mstdata"))
@ApiPojo(nameSpace = "productNode", entry = {
        @Entry(path = "selectPage/", api = QueryPageApiEntry.class),
        @Entry(path = "saveForce/", api = SaveCascadeApiEntry.class),
        @Entry(path = "deleteForce/", api = RemoveCascadeApiEntry.class)
})
@Data
public class ProductNode {
    private static final long serialVersionUID = -5059879337552051288L;
    private Long id;
	private String nodeCode;
	protected Long productTypeId;
    protected ProductType productType;
    protected Long productNodeExtId;
	protected ProductNodeExt productNodeExt;
	private Long parentId;
	private ProductNode parent;
	private LocalDateTime createDate;

	public ProductNode() {
		productNodeExt = new ProductNodeExt();
	}

    @Id(idGenerator = SequenceIdGenerator.class, parameters = {@Parameter(name = Constant.SEQUENCE_SCHEMA, value = "mstdata"), @Parameter(name = Constant.SEQUENCE_NAME, value = "SEQ_MD_PRODUCT_NODE_ID")})
    @Column(name = "MD_PRODUCT_NODE_ID")
	public Long getId() {
		return this.id;
	}

    @Column
    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "NODE_CODE")
    @ApiQueryFilter(In.class)
	public String getNodeCode() {
		return this.nodeCode;
	}

	public void setNodeCode(String nodeCode) {
		this.nodeCode = nodeCode;
	}

	@ManyToOne(condition = {@On(foreignFiled = "productTypeId", targetFiled = "id")}, insertable = true, updatable = true, deletable = true, cascadeLevel = 2)
	public ProductType getProductType() {
		return productType;
	}

	public void setProductType(ProductType productType) {
		this.productType = productType;
	}

	@Column(name = "PRODUCT_TYPE_ID")
	public Long getProductTypeId() {
		return productTypeId;
	}

	public void setProductTypeId(Long productTypeId) {
		this.productTypeId = productTypeId;
	}

	@Column(name = "PRODUCT_NODE_EXT_ID")
	public Long getProductNodeExtId() {
		return productNodeExtId;
	}

	public void setProductNodeExtId(Long productNodeExtId) {
		this.productNodeExtId = productNodeExtId;
	}

	@OneToOne(condition = {@On(foreignFiled = "productNodeExtId", targetFiled = "id")})
	public ProductNodeExt getProductNodeExt() {
		return productNodeExt;
	}

	public void setProductNodeExt(ProductNodeExt productNodeExt) {
		this.productNodeExt = productNodeExt;
	}

    @ManyToOne(condition = {@On(foreignFiled = "parentId", targetFiled = "id")}, cascadeScope = {"parent.productNodeExt", "parent.productType.productTypeExt"})
	public ProductNode getParent() {
		return parent;
	}

	public void setParent(ProductNode parent) {
		this.parent = parent;
	}

	@Column(typeConverter = OracleLocalDateTimeConverter.class)
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }
}
