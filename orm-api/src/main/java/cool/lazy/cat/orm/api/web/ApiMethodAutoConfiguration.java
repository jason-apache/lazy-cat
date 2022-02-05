package cool.lazy.cat.orm.api.web;

import cool.lazy.cat.orm.api.web.entrust.method.MethodEntryConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Import;

/**
 * @author: mahao
 * @date: 2022-02-05 15:27
 */
@ConditionalOnExpression(value = "${cool.lazy-cat.servlet.enabled-common-api:true}")
@Import(value = {MethodEntryConfiguration.class})
public class ApiMethodAutoConfiguration {
}
