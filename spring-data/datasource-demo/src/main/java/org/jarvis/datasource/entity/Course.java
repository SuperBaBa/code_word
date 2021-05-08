package org.jarvis.datasource.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 课程表实体
 *
 * @author Marcus
 * @date 2021/5/8-11:02
 */
@Data
public class Course {
    private Integer id;
    private String name;
    private Integer duration;
    private LocalDateTime createTime;
    private Integer createUser;
    private LocalDateTime updateTime;
    private Integer updateUser;

}
