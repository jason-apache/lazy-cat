package cool.lazy.cat.orm.core.jdbc.component.id;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * @author: mahao
 * @date: 2021/3/30 19:54
 * UUID生成器
 */
public class UUIdGenerator implements IdGenerator {

    @Override
    public List<Object> generator(List<Object> instances) {
        if (instances.size() == 1) {
            return Collections.singletonList(this.generator());
        } else {
            List<Object> ids = new ArrayList<>(instances.size());
            for (int i = 0; i < instances.size(); i++) {
                ids.add(this.generator());
            }
            return ids;
        }
    }

    protected Object generator() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
