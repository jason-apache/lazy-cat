package com.jason.test.component.datasource;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

/**
 * @author: mahao
 * @date: 2022-02-03 16:29
 */
@Component
@ConditionalOnExpression("${custom.data-source.multiple-data-source-enable:false}")
public class MultipleDataSourceRegistry {
}
