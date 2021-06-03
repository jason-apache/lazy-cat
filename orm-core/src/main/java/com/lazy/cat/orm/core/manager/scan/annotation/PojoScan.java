package com.lazy.cat.orm.core.manager.scan.annotation;


import com.lazy.cat.orm.core.manager.scan.PojoScanner;
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
@Import(PojoScanner.class)
public @interface PojoScan {

    /**
     * 扫描包名（包含子包），pojo类需要标注@Pojo
     * @see com.lazy.kitty.jdbc.base.annotation.Pojo
     * @see com.lazy.kitty.jdbc.manager.scan.ClassPathPojoScanner#doScanPojo
     */
    String[] value();
}
