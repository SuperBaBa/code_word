package org.jarvis.sqltask.datasource;

import org.jarvis.sqltask.util.DynamicDataSourceContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 动态数据源配置
 *
 * @author marcus
 * @date 2020/11/2-0:29
 */
public class DynamicDataSource extends AbstractDynamicRoutingDataSource {
    private final Logger log = LoggerFactory.getLogger(DynamicDataSource.class);

    private String primary;
    /**
     * 所有数据库
     */
    private Map<String, DataSource> dataSources = new LinkedHashMap<>();

    @Override
    protected DataSource determineCurrentLookupKey() {
        String dsKey = DynamicDataSourceContextHolder.getDataSourceLookupKey();
        if (StringUtils.isEmpty(dsKey)) {
            return determinePrimaryDataSource();
        }
        return dataSources.get(DynamicDataSourceContextHolder.getDataSourceLookupKey());
    }

    public DataSource determinePrimaryDataSource() {
        return dataSources.get(primary);
    }

    @Override
    public DataSource getDataSource(String dsKey) {
        if (!StringUtils.isEmpty(dsKey)) {
            return dataSources.get(dsKey);
        }
        return determinePrimaryDataSource();
    }

    @Override
    public synchronized void initDataSourceCollection(Map<String, DataSource> dataSourceMap) {
        Assert.isTrue(dataSourceMap.containsKey(primary), "请检查Primary数据源是否设置");
        log.info("初始共加载 {} 个数据源", dataSourceMap.size());
        dataSources.putAll(dataSourceMap);
        DynamicDataSourceContextHolder.setDataSourceLookupKey(primary);
    }

    @Override
    public synchronized void addDataSource(String dsKey, DataSource dataSource) {
        dataSources.put(dsKey, dataSource);
    }


    @Override
    public synchronized void removeDataSource(String dsKey) {
        this.dataSources.remove(dsKey);
    }

    @Override
    public synchronized void removeDataSource(String dsKey, DataSource dataSource) {
        this.dataSources.remove(dsKey, dataSource);

    }


    /**
     * 是否存在当前key的 DataSource
     *
     * @param key
     * @return 存在返回 true, 不存在返回 false
     */
    @Override
    public boolean isExistDataSource(String key) {
        return this.dataSources.containsKey(key);
    }

    @Override
    public Connection getConnection() throws SQLException {
        return determineCurrentLookupKey().getConnection();
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return determineCurrentLookupKey().getConnection(username, password);
    }

    public String getPrimary() {
        return primary;
    }

    public void setPrimary(String primary) {
        this.primary = primary;
    }

    private static class DynamicDataSourceBuilder {
        private static DynamicDataSource INSTANCE = new DynamicDataSource();
    }

    /**
     * 使用静态内部类方法单例,保证线程安全,同时延迟加载{@code DynamicDataSource}初始化
     *
     * @return {@code DynamicDataSource}实例化对象
     */
    public static DynamicDataSource getInstance() {
        return DynamicDataSourceBuilder.INSTANCE;
    }
//    private static DynamicDataSource instance;
//    private static byte[] lock = new byte[0];
//
//    public static synchronized DynamicDataSource getInstance() {
//        if (instance == null) {
//            synchronized (lock) {
//                if (instance == null) {
//                    instance = new DynamicDataSource();
//                }
//            }
//        }
//        return instance;
//    }
}
