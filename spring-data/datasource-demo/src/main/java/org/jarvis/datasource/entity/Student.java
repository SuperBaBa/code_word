package org.jarvis.datasource.entity;

import lombok.Data;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * 学生表实体
 *
 * @author Marcus
 * @date 2021/5/8-11:02
 */
@Data
@Table(name = "STUDENT")
@NamedQuery(name = "findByTheUsersName", query = "FROM STUDENT  WHERE NAME = ?1")
public class Student extends AbstractPersistable<Long> {
    /**
     * 学号
     */
    @Column(name = "STUDENT_NUM")
    private Integer studentNum;
    /**
     * 学生姓名
     */
    @Column(name = "NAME")
    private String name;
    /**
     * 学生绩点分数
     */
    @Column(name = "SCORE")
    private Double score;
}
