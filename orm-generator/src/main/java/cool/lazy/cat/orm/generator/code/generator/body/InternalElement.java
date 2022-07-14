package cool.lazy.cat.orm.generator.code.generator.body;

/**
 * @author : jason.ma
 * @date : 2022/7/13 15:05
 */
public interface InternalElement extends JavaCode, Element {

    /**
     * @return 元素深度
     */
    int getDepth();

    /**
     * 设置深度
     */
    void depth(int depth);

    @Override
    default int indent() {
        return this.getDepth();
    }
}
