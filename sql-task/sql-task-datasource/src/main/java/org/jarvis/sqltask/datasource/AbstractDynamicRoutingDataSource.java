package org.jarvis.sqltask.datasource;

import org.springframework.jdbc.datasource.AbstractDataSource;

import javax.sql.DataSource;
import java.util.Map;

/**
 * @author marcus
 * @date 2020/11/15-17:32
 */
public abstract class AbstractDynamicRoutingDataSource extends AbstractDataSource {
    protected abstract DataSource determineCurrentLookupKey();

    public abstract void initDataSourceCollection(Map<String, DataSource> dataSourceMap);

    public abstract void addDataSource(String dsKey, DataSource dataSource);

    public abstract void removeDataSource(String lookupKey);

    public abstract void removeDataSource(String dsKey, DataSource dataSource);

    public abstract boolean isExistDataSource(String key);

    public abstract DataSource getDataSource(String dsKey);
}
