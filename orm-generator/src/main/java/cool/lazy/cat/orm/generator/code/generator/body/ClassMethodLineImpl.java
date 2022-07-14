package cool.lazy.cat.orm.generator.code.generator.body;

/**
 * @author : jason.ma
 * @date : 2022/7/14 15:08
 */
public class ClassMethodLineImpl extends AbstractCode implements ClassMethodLine {

    protected int depth;

    public ClassMethodLineImpl(String expression) {
        super(expression);
    }

    @Override
    public int getDepth() {
        return depth;
    }

    @Override
    public void depth(int depth) {
        this.depth = depth;
    }
}
