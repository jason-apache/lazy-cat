package cool.lazy.cat.orm.generator;

import cool.lazy.cat.orm.generator.code.CodeGenerator;
import cool.lazy.cat.orm.generator.config.CodeGeneratorConfig;
import cool.lazy.cat.orm.generator.config.JdbcConnectionConfig;
import org.junit.Test;

/**
 * @author : jason.ma
 * @date : 2022/7/11 18:09
 */
public class MainTest {

    @Test
    public void test() {
        // useInformationSchema=true
        // nullDatabaseMeansCurrent=true
        // jdbc:mysql://localhost:3306/personal
        String url = "jdbc:mysql://192.168.0.39:3306/facebook?useUnicode=true&serverTimezone=Hongkong&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&useInformationSchema=true";
        new CodeGenerator().generate(new CodeGeneratorConfig(new JdbcConnectionConfig(url, "fbdbuser", "51715522", null)));
    }
}
