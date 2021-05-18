package org.jarvis.util;

/**
 * @author marcus
 * @date 2020/11/2-1:36
 */
public class DynamicDataSourceContextHolder {
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    /**
     * To switch DataSource
     *
     * @param key the key
     */
    public static synchronized void setDataSourceKey(String key) {
        contextHolder.set(key);
    }

    /**
     * Get current DataSource
     *
     * @return data source key
     */
    public static String  getDataSourceKey() {
        return contextHolder.get();
    }

    /**
     * To set DataSource as default
     */
    public static void clearDataSourceKey() {
        contextHolder.remove();
    }
}
