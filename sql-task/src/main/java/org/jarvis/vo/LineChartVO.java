package org.jarvis.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

/**
 * @author marcus
 * @date 2020/11/4-12:56
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LineChartVO {
    private Integer tableCount;
    private String createTime;

    public Integer getTableCount() {
        return tableCount;
    }

    public void setTableCount(Integer tableCount) {
        this.tableCount = tableCount;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
