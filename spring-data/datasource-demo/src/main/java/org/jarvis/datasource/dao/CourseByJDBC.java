package org.jarvis.datasource.dao;

import org.jarvis.datasource.entity.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.util.List;

/**
 * 使用不同的JDBCTemplate对课程表进行操作
 *
 * @author Marcus
 * @date 2021/5/8-11:11
 */
public class CourseByJDBC {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private SimpleJdbcInsert simpleJdbcInsert;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void insertCourseBatch(List<Course> courseList) {
        courseList.forEach(course -> {
            jdbcTemplate.update("INSERT INTO `COURSE` (ID, `NAME`, DURATION, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER) VALUES (?,?,?,?,?,?,?)",
                    course.getId(), course.getName(), course.getDuration(), course.getCreateTime(), course.getCreateUser(), course.getUpdateTime(), course.getUpdateUser());
        });
    }
}
