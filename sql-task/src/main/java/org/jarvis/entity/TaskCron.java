package org.jarvis.entity;

import java.util.Date;

/**
 * @author marcus
 * @date 2020/11/12-23:56
 */
public class TaskCron {
    private Integer id;
    private String cronStr;
    private Integer status;
    private Date sendTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCronStr() {
        return cronStr;
    }

    public void setCronStr(String cronStr) {
        this.cronStr = cronStr;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }
}
