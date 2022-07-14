package cool.lazy.cat.orm.generator.code.generator.body;

/**
 * @author : jason.ma
 * @date : 2022/7/14 11:46
 */
public interface DecorativeElement<E extends JavaCode> extends Element, JavaCode {

    DecorationArea<E> getHeader();

    void setHeader(DecorationArea<E> header);
}
