package cool.lazy.cat.orm.generator.code.generator.event;

/**
 * @author : jason.ma
 * @date : 2022/7/13 16:55
 */
public interface EventName {

    String message();

    enum NameEnum implements EventName {

        BEFORE_GENERATE(null),
        PACKAGE_DONE("生成class package信息已完成"),
        SUBJECT_INITIALIZED("class subject初始化"),
        FIELDS_DONE("class field已生成"),
        SETTER_GETTER_DONE("class setter getter已生成"),
        ;

        private final String message;

        NameEnum(String message) {
            this.message = message;
        }

        @Override
        public String message() {
            return message;
        }
    }
}
