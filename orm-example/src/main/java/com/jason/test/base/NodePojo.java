package com.jason.test.base;

import java.util.List;

/**
 * @author: mahao
 * @date: 2021/11/8 16:19
 */
public abstract class NodePojo extends TreePojo {
    private static final long serialVersionUID = 1825441839748314760L;

    public abstract List<? extends NodePojo> getChildrenList();
}
