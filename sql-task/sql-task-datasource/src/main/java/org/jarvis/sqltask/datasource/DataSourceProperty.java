package org.jarvis.sqltask.datasource;

import org.jarvis.sqltask.driver.DatabaseDriverEnum;

/**
 * 数据源配置信息,接收配置文件中key-value配置的绑定
 *
 * @author marcus
 * @date 2020/11/14-12:36
 */
public class DataSourceProperty {
    /**
     * 各类数据源参数默认配置
     */
    public final static int DEFAULT_INITIAL_SIZE = 0;
    public final static int DEFAULT_MAX_ACTIVE_SIZE = 8;
    public final static int DEFAULT_MAX_IDLE = 8;
    public final static int DEFAULT_MIN_IDLE = 0;
    public final static int DEFAULT_MAX_WAIT = -1;
    /**
     * 数据源连接串,也将用于确定驱动类型(判断使用oracle数据库驱动,还是mysql数据库驱动)
     */
    private volatile String url;
    /**
     * 数据库用户名
     */
    private volatile String username;
    /**
     * 数据库用户口令
     */
    private volatile String password;
    /**
     * 数据库驱动全类名,默认使用MySQL数据库驱动
     */
    private volatile String driverClassName = DatabaseDriverEnum.MYSQL.getDriverClassName();
    private volatile DataSourceTypeEnum type = DataSourceTypeEnum.HIKARI;
    private volatile int minIdle = DEFAULT_MIN_IDLE;
    private volatile int maxIdle = DEFAULT_MAX_IDLE;
    private volatile int maxActive = DEFAULT_MAX_ACTIVE_SIZE;
    protected volatile long maxWait = DEFAULT_MAX_WAIT;
    private volatile int initialSize = DEFAULT_INITIAL_SIZE;
    private volatile String validationQuery = DatabaseDriverEnum.MYSQL.getValidationQuery();

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public DataSourceTypeEnum getType() {
        return type;
    }

    public void setType(DataSourceTypeEnum type) {
        this.type = type;
    }

    public int getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }

    public int getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    public int getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(int maxActive) {
        this.maxActive = maxActive;
    }

    public long getMaxWait() {
        return maxWait;
    }

    public void setMaxWait(long maxWait) {
        this.maxWait = maxWait;
    }

    public int getInitialSize() {
        return initialSize;
    }

    public void setInitialSize(int initialSize) {
        this.initialSize = initialSize;
    }

    public String getValidationQuery() {
        return validationQuery;
    }

    public void setValidationQuery(String validationQuery) {
        this.validationQuery = validationQuery;
    }
}
