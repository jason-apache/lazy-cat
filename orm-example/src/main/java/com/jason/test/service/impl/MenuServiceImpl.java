package com.jason.test.service.impl;

import com.jason.test.pojo.mysql.Menu;
import com.jason.test.service.MenuService;
import cool.lazy.cat.orm.core.base.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * @author: mahao
 * @date: 2021-11-18 21:47
 */
@Service
public class MenuServiceImpl extends BaseServiceImpl<Menu> implements MenuService {

    protected void check(Menu pojo) {
        if (Boolean.TRUE.equals(pojo.getLocked()) && null != pojo.getId()) {
            throw new UnsupportedOperationException("该菜单不允许修改!");
        }
    }

    @Override
    public Menu save(Menu pojo, boolean cascade) {
        this.check(pojo);
        return super.save(pojo, cascade);
    }

    @Override
    public void save(Collection<Menu> pojoCollection, boolean cascade) {
        pojoCollection.forEach(this::check);
        super.save(pojoCollection, cascade);
    }

    @Override
    public Menu update(Menu pojo, boolean cascade, boolean ignoreNull) {
        this.check(pojo);
        return super.update(pojo, cascade, ignoreNull);
    }

    @Override
    public void update(Collection<Menu> pojoCollection, boolean cascade, boolean ignoreNull) {
        pojoCollection.forEach(this::check);
        super.update(pojoCollection, cascade, ignoreNull);
    }

    @Override
    public void delete(Menu pojo, boolean cascade) {
        this.check(pojo);
        super.delete(pojo, cascade);
    }

    @Override
    public void delete(Collection<Menu> pojoCollection, boolean cascade) {
        pojoCollection.forEach(this::check);
        super.delete(pojoCollection, cascade);
    }

    @Override
    public void deleteByInfer(Menu pojo, boolean cascade) {
        this.check(pojo);
        super.deleteByInfer(pojo, cascade);
    }

    @Override
    public void deleteByInfer(Collection<Menu> pojoCollection, boolean cascade) {
        pojoCollection.forEach(this::check);
        super.deleteByInfer(pojoCollection, cascade);
    }
}
