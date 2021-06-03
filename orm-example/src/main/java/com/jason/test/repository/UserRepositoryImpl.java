package com.jason.test.repository;


import com.jason.test.pojo.User;
import com.lazy.cat.orm.core.base.repository.impl.BaseRepositoryImpl;
import org.springframework.stereotype.Repository;

/**
 * @author: mahao
 * @date: 2021/3/28 10:54
 */
@Repository(value = "userRepository")
public class UserRepositoryImpl extends BaseRepositoryImpl<User> implements UserRepository {
}
