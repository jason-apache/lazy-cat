package cool.lazy.cat.orm.generator.code.generator.body;

/**
 * @author : jason.ma
 * @date : 2022/7/14 11:53
 */
public abstract class AbstractDecorativeElement<E extends JavaCode> extends AbstractCompoundElement<E>
        implements DecorativeElement<E> {

    protected DecorationArea<E> header;

    @Override
    public String code() {
        if (null != this.getHeader()) {
            return this.getHeader().full() + super.full();
        }
        return super.code();
    }

    @Override
    public DecorationArea<E> getHeader() {
        return header;
    }

    @Override
    public void setHeader(DecorationArea<E> header) {
        this.header = header;
    }
}
