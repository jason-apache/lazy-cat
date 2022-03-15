package cool.lazy.cat.orm.core.manager.scan.annotation;


import cool.lazy.cat.orm.annotation.Pojo;
import cool.lazy.cat.orm.core.manager.scan.PojoClassScanner;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: mahao
 * @date: 2021/3/4 13:23
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(PojoClassScanner.class)
public @interface PojoScan {

    /**
     * 扫描包名（包含子包），pojo类需要标注@Pojo
     * @see Pojo
     * @see cool.lazy.cat.orm.core.manager.scan.ClassPathPojoScanner#doScanPojo
     */
    String[] value();
}
