package com.lazy.cat.orm.core.base.util;


import com.lazy.cat.orm.core.jdbc.mapping.ManyToOneMapping;
import com.lazy.cat.orm.core.jdbc.mapping.OneToManyMapping;
import com.lazy.cat.orm.core.jdbc.mapping.OneToOneMapping;
import com.lazy.cat.orm.core.jdbc.mapping.PojoMapping;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * @author: mahao
 * @date: 2021/3/20 14:53
 */
public final class CollectionUtil {

    public static List<PojoMapping> concat(List<OneToOneMapping> otoList, List<OneToManyMapping> otmList, List<ManyToOneMapping> mtoList) {
        List<PojoMapping> concat = new ArrayList<>();
        if (isNotEmpty(otoList)) {
            concat.addAll(otoList);
        }
        if (isNotEmpty(otmList)) {
            concat.addAll(otmList);
        }
        if (isNotEmpty(mtoList)) {
            concat.addAll(mtoList);
        }
        return concat;
    }
    public static boolean isEmpty(Collection<?> collection) {
        return !isNotEmpty(collection);
    }

    public static boolean isNotEmpty(Collection<?> collection) {
        return null != collection && !collection.isEmpty();
    }

    public static boolean isEmpty(Object[] arr) {
        return !isNotEmpty(arr);
    }

    public static boolean isNotEmpty(Object[] arr) {
        return null != arr && arr.length > 0;
    }

    public static boolean contains(Object[] arr, Object target) {
        return null != arr && indexOf(arr, target) != -1;
    }

    public static int indexOf(Object[] arr, Object target) {
        for (int i = 0; i < arr.length; i++) {
            if (Objects.equals(target, arr[i])) {
                return i;
            }
        }
        return -1;
    }
}
