package cool.lazy.cat.orm.generator.code.generator.body;

/**
 * @author : jason.ma
 * @date : 2022/7/14 11:32
 */
public interface ClassSubject extends CompoundElement<JavaCode>, DecorativeElement<JavaCode>, Element, JavaCode {

    ClassExtends classExtends();
    void classExtends(ClassExtends classExtends);

    ClassImplements classImplements();

    void classImplements(ClassImplements classImplements);

    @Override
    default String spacer() {
        return "";
    }
}
