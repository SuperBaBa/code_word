package org.jarvis.datasource;

import org.springframework.jdbc.datasource.AbstractDataSource;

import javax.sql.DataSource;
import java.util.Map;

/**
 * 继承spring-data的数据源
 *
 * @author marcus
 * @date 2020/11/15-17:32
 */
public abstract class AbstractDynamicRoutingDataSource extends AbstractDataSource {
    /**
     * 确认当前线程的数据源
     *
     * @return
     */
    protected abstract DataSource determineCurrentLookupKey();

    public abstract void initDataSourceCollection(Map<String, DataSource> dataSourceMap);

    public abstract void addDataSource(String dsKey, DataSource dataSource);

    public abstract void removeDataSource(String lookupKey);

    public abstract void removeDataSource(String dsKey, DataSource dataSource);

    public abstract boolean isExistDataSource(String key);

    public abstract DataSource getDataSource(String dsKey);
}
