package org.edwin.service;

import lombok.Builder;

import java.util.Date;

/**
 * @author marcus
 * @date 2020/8/12-6:27
 */
@Builder
public class LogService {
    private String name;
    private Date createTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
