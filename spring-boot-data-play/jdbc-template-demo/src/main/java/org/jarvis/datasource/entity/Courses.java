package org.jarvis.datasource.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * author:marcus date:2020/7/31
 **/
@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
public class Courses {
    private int id;
    private String name;
    private int studentsId;
    private Date createTime;
    private Date updateTime;
}
