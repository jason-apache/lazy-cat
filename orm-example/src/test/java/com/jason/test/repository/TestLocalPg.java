package com.jason.test.repository;

import com.jason.test.TestConfiguration;
import com.jason.test.pojo.pgsql.Book;
import com.jason.test.pojo.pgsql.BookExt;
import com.jason.test.pojo.pgsql.Student;
import com.jason.test.pojo.pgsql.StudentExt;
import com.jason.test.pojo.pgsql.Subject;
import com.jason.test.pojo.pgsql.SubjectExt;
import cool.lazy.cat.orm.core.base.repository.BaseRepository;
import cool.lazy.cat.orm.core.jdbc.param.SearchParam;
import cool.lazy.cat.orm.core.jdbc.param.SearchParamImpl;
import cool.lazy.cat.orm.core.jdbc.sql.condition.Condition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

/**
 * @author: mahao
 * @date: 2022-01-20 10:26
 */
@SpringBootTest(classes = TestConfiguration.class)
public class TestLocalPg {

    @Autowired
    BaseRepository baseRepository;

    @Test
    public void testSelect() {
        List<Student> studentList = baseRepository.query(new SearchParamImpl<>(Student.class).setCondition(Condition.like("name", "a")));
        System.out.println();
    }

    @Test
    public void testUpdate() {
        baseRepository.save(new Student().setName("asd").setStudentExt(new StudentExt().setBeGoodAt("history"))
                .setBooks(Collections.singletonList(new Book().setName("高等数学").setBookExt(new BookExt().setThickness("2cm")
                        .setSubject(new Subject().setName("math").setSubjectExt(new SubjectExt().setWeight("120")))))));
        System.out.println();
    }

    @Test
    public void testCondition() {
        SearchParam<Student> param = new SearchParamImpl<>(Student.class).setIndex(0).setPageSize(50);
        List<Student> s1 = baseRepository.query(param.setCondition(Condition.lte("createDate", LocalDateTime.now()).and(Condition.eq("sex", "男"))));
        List<Student> s2 = baseRepository.query(param.setCondition(Condition.gte("createDate", LocalDate.now())));
        List<Student> s3 = baseRepository.query(param.setCondition(Condition.lte("createDate", LocalDateTime.now()).or(Condition.isNull("createDate"))));
        List<Student> s4 = baseRepository.query(param.setCondition(Condition.lte("createDate", LocalDateTime.now().minusYears(3))));
        List<Student> s5 = baseRepository.query(param);
        System.out.println();
    }
}
