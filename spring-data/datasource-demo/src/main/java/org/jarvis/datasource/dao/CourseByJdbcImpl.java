package org.jarvis.datasource.dao;

import org.jarvis.datasource.entity.Course;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author marcus @date 2021/5/8
 **/
public class CourseByJdbcImpl implements ICourseByJdbc<Course> {

    private final JdbcTemplate template;

    public CourseByJdbcImpl(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public void insert(Course course) {
    }

}
