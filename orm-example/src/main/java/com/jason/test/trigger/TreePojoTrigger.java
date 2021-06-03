package com.jason.test.trigger;

import com.jason.test.base.RecordPojo;
import com.jason.test.base.TreePojo;

/**
 * @author: mahao
 * @date: 2021/4/20 13:21
 */
public class TreePojoTrigger extends RecordPojoTrigger {

    @Override
    protected void doSet(RecordPojo pojo, boolean onInsert) {
        super.doSet(pojo, onInsert);
        if (null == pojo.getId()) {
            return;
        }
        if (pojo instanceof TreePojo) {
            TreePojo ref = (TreePojo) pojo;
            TreePojo parent = ref.getParent();
            this.setParentInfo(parent, ref);
        }
    }

    protected void setParentInfo(TreePojo parent, TreePojo children) {
        if (null == parent) {
            children.setPath(children.getId().toString() + "/");
            children.setLevel(1);
        } else {
            children.setPath(parent.getPath() + children.getId().toString() + "/");
            children.setLevel(parent.getLevel() + 1);
        }
    }
}
