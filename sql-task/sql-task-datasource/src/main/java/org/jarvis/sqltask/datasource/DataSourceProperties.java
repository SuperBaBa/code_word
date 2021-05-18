package org.jarvis.sqltask.datasource;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * 多数据源配置,key-value形式绑定多个数据源, 也就是存储了多条数据源表示与数据源映射
 * 默认使用default数据源为首选项
 *
 * @author marcus
 * @date 2020/11/14-11:16
 */
@ConfigurationProperties(prefix = "jarvis.database")
public class DataSourceProperties {
    /**
     * 首选数据源,默认使用default数据源作为首选
     */
    private String primary = "default";
    /**
     * 每个数据源配置,使用dataSourceKey用于区分每个数据源
     */
    private Map<String, DataSourceProperty> dataSource = new HashMap<>();

    public String getPrimary() {
        return primary;
    }

    public void setPrimary(String primary) {
        this.primary = primary;
    }

    public Map<String, DataSourceProperty> getDataSource() {
        return dataSource;
    }

    public void setDataSource(Map<String, DataSourceProperty> dataSource) {
        this.dataSource = dataSource;
    }
}
