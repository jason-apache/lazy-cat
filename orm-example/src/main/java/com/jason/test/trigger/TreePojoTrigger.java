package com.jason.test.trigger;

import com.jason.test.base.NodePojo;
import com.jason.test.base.RecordPojo;
import com.jason.test.base.TreePojo;
import cool.lazy.cat.orm.base.util.CollectionUtil;

import java.util.List;

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
        if (pojo instanceof NodePojo) {
            this.setChildrenFields((NodePojo) pojo);
        }
    }

    protected void setParentInfo(TreePojo parent, TreePojo children) {
        if (null == parent) {
            if (null == children.getParentId()) {
                children.setLevel(1);
                children.setPath(children.getId().toString() + "/");
            }
        } else {
            children.setParentId(parent.getId());
            children.setPath(parent.getPath() + children.getId().toString() + "/");
            children.setLevel(parent.getLevel() + 1);
        }
    }

    protected void setChildrenFields(NodePojo parent) {
        List<? extends NodePojo> childrenList = parent.getChildrenList();
        if (CollectionUtil.isNotEmpty(childrenList)) {
            for (NodePojo children : childrenList) {
                children.setParentId(parent.getId());
                children.setPath(parent.getPath() + children.getId().toString() + "/");
                children.setLevel(parent.getLevel() + 1);
            }
        }
    }
}
