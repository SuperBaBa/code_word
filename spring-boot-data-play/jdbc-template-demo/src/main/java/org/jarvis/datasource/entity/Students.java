package org.jarvis.datasource.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * author:marcus date:2020/7/31
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Students {
    private int id;
    private String name;
    private int coursesId;
    private Date createTime;
    private Date updateTime;
}
