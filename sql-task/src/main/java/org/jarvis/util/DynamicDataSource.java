package org.jarvis.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.HashMap;
import java.util.Map;

/**
 * @author marcus
 * @date 2020/11/2-0:29
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    //单例句柄
    private static DynamicDataSource instance;
    private static byte[] lock = new byte[0];
    //用于存储已实例的数据源map
    private static Map<Object, Object> dataSourceMap = new HashMap<>();

    /**
     * @return
     */
    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicDataSourceContextHolder.getDataSourceKey();
    }

    /**
     * @param targetDataSources
     */
    @Override
    public void setTargetDataSources(Map<Object, Object> targetDataSources) {
        super.setTargetDataSources(targetDataSources);
    }

    /**
     * 获取存储已实例的数据源map
     *
     * @return
     */
    public Map<Object, Object> getDataSourceMap() {
        return dataSourceMap;
    }

    /**
     * 单例方法
     *
     * @return
     */
    public static synchronized DynamicDataSource getInstance() {
        if (instance == null) {
            synchronized (lock) {
                instance = new DynamicDataSource();
            }
        }
        return instance;
    }

    /**
     * 是否存在当前key的 DataSource
     *
     * @param key
     * @return 存在返回 true, 不存在返回 false
     */
    public static boolean isExistDataSource(String key) {
        return dataSourceMap.containsKey(key);
    }
}
