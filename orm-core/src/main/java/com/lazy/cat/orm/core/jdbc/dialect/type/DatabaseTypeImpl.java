package com.lazy.cat.orm.core.jdbc.dialect.type;

import java.util.Objects;

/**
 * @author: mahao
 * @date: 2021/4/24 16:13
 */
public class DatabaseTypeImpl implements DatabaseType {

    private final String name;

    public DatabaseTypeImpl(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DatabaseTypeImpl that = (DatabaseTypeImpl) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
