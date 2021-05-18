package org.jarvis.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author marcus
 * @date 2020/11/4-12:56
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TableStatus {
    private Integer id;
    private String tableName;
    private Long costTimes;
    private Integer tableCount;
    private Date createTime;
    private Date modifyTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Long getCostTimes() {
        return costTimes;
    }

    public void setCostTimes(Long costTimes) {
        this.costTimes = costTimes;
    }

    public Integer getTableCount() {
        return tableCount;
    }

    public void setTableCount(Integer tableCount) {
        this.tableCount = tableCount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    @Override
    public String toString() {
        return "TableStatus{" +
                "id=" + id +
                ", tableName='" + tableName + '\'' +
                ", costTimes=" + costTimes +
                ", tableCount=" + tableCount +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                '}';
    }
}
