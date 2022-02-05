package cool.lazy.cat.orm.core.jdbc.mapping.field.access;

import java.util.Collection;

/**
 * @author: mahao
 * @date: 2021/8/23 09:41
 */
public class PathDescriptorImpl implements PathDescriptor {

    private final Collection<String> fullPath;
    private final String fullPathStr;

    public PathDescriptorImpl(Collection<String> fullPath) {
        this.fullPath = fullPath;
        this.fullPathStr = String.join(".", this.fullPath);
    }

    @Override
    public String getFullPath() {
        return fullPathStr;
    }

    @Override
    public boolean belong(String fullPath) {
        return this.getFullPath().startsWith(fullPath);
    }

    @Override
    public boolean nested() {
        return this.fullPath.size() > 1;
    }

    @Override
    public String toString() {
        return fullPathStr;
    }
}
