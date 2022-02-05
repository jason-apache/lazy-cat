package com.jason.test.repository;

import com.jason.test.TestConfiguration;
import com.jason.test.pojo.oracle.ProductNode;
import com.jason.test.pojo.oracle.ProductType;
import com.jason.test.service.ProductNodeService;
import cool.lazy.cat.orm.core.base.service.BaseService;
import cool.lazy.cat.orm.core.base.service.CommonService;
import cool.lazy.cat.orm.core.jdbc.OrderBy;
import cool.lazy.cat.orm.core.jdbc.param.SearchParam;
import cool.lazy.cat.orm.core.jdbc.param.SearchParamImpl;
import cool.lazy.cat.orm.core.jdbc.sql.condition.Condition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 * @author: mahao
 * @date: 2021-12-08 16:35
 */
@SpringBootTest(classes = TestConfiguration.class)
public class TestOracle {

    @Autowired
    ProductNodeService productNodeService;
    @Autowired
    BaseService<Object> baseService;
    @Autowired
    CommonService commonService;

    @Test
    public void testQuery() {
        List<ProductNode> productNodeList = commonService.select(new SearchParamImpl<>(ProductNode.class).setCondition(Condition.eq("nodeCode", "MH_TEST")));
        System.out.println(productNodeList.size());
    }

    @Test
    public void testInsert() {
        ProductNode productNode = new ProductNode();
        productNode.setNodeCode("MH_TEST");
        ProductType productType = new ProductType();
        productType.setTypeCode("fly");
        productType.setTypeName("fly way");
        productNode.setProductType(productType);
        productNodeService.save(productNode, true);
    }

    @Test
    public void testCondition() {
        SearchParam<ProductNode> param = new SearchParamImpl<>(ProductNode.class).setIndex(0).setPageSize(200000).setOrderBy(OrderBy.buildOrderBy("id"));
        List<ProductNode> p1 = commonService.select(param.setCondition(Condition.lt("createDate", new Date())));
        List<ProductNode> p2 = commonService.select(param.setCondition(Condition.gte("createDate", Date.from(LocalDate.now().minusDays(20).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()))));
        System.out.println();
    }
}
