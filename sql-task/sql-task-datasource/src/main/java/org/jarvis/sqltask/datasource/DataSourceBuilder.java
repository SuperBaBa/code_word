package org.jarvis.sqltask.datasource;

import org.jarvis.sqltask.driver.DatabaseDriverEnum;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.ConfigurationPropertyName;
import org.springframework.boot.context.properties.source.ConfigurationPropertyNameAliases;
import org.springframework.boot.context.properties.source.ConfigurationPropertySource;
import org.springframework.boot.context.properties.source.MapConfigurationPropertySource;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author marcus
 * @date 2020/11/16-10:55
 */
public class DataSourceBuilder {
    /**
     * 数据源子类
     */
    private Class<? extends DataSource> type;
    private String driverClassName;
    private String url;
    private String username;
    private String password;
    private int minIdle;
    private int maxIdle;
    private int maxActive;
    private long maxWait;
    private int initialSize;
    private String validationQuery;
    private Map<String, Object> properties = new HashMap<>();

    public DataSourceBuilder url(String url) {
        this.url = url;
        this.properties.put("url", url);
        return this;
    }

    public DataSourceBuilder minIdle(int minIdle) {
        this.minIdle = minIdle;
        this.properties.put("minIdle", minIdle);
        return this;
    }

    public DataSourceBuilder maxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
        this.properties.put("maxIdle", maxIdle);
        return this;
    }

    public DataSourceBuilder maxActive(int maxActive) {
        this.maxActive = maxActive;
        this.properties.put("maxActive", maxActive);
        return this;
    }

    public DataSourceBuilder validationQuery(String validationQuery) {
        this.validationQuery = validationQuery;
        this.properties.put("validationQuery", validationQuery);
        return this;
    }

    public DataSourceBuilder maxWait(long maxWait) {
        this.maxWait = maxWait;
        this.properties.put("maxWait", maxWait);
        return this;
    }

    public DataSourceBuilder driverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
        this.properties.put("driverClassName", driverClassName);
        return this;
    }

    public DataSourceBuilder username(String username) {
        this.username = username;
        this.properties.put("username", username);
        return this;
    }

    public DataSourceBuilder password(String password) {
        this.password = password;
        this.properties.put("password", password);
        return this;
    }

    public DataSourceBuilder type(String type) throws ClassNotFoundException {
        return this.type(Class.forName(type));
    }

    public DataSourceBuilder type(Class type) {
        this.type = type;
        return this;
    }

    /**
     * 检验数据，并格式(标准化)数据，根据url或type选出对应的DriveClassName
     *
     * @return
     */
    public DataSource build() {
        // 必须是jdbc协议链接
        Assert.isTrue(url.startsWith("jdbc"), "URL must start with 'jdbc'");
        String urlWithoutPrefix = url.substring("jdbc".length()).toLowerCase(Locale.ENGLISH);
        // 根据连接串筛选出对应的数据库驱动
        DatabaseDriverEnum driver = DatabaseDriverEnum.matchDriver(urlWithoutPrefix);
        this.properties.put(driverClassName, driver.getDriverClassName());
        //TODO 依赖Spring Bean模块工具,后续更改
        DataSource result = BeanUtils.instantiateClass(type);
        bind(result);
        return result;
    }

    private void bind(DataSource result) {
        ConfigurationPropertySource source = new MapConfigurationPropertySource(this.properties);
        ConfigurationPropertyNameAliases aliases = new ConfigurationPropertyNameAliases();
        aliases.addAliases("url", "jdbc-url");
        aliases.addAliases("username", "user");
//        aliases.addAliases("maxActive", "maxPoolSize");
        Binder binder = new Binder(source.withAliases(aliases));
        binder.bind(ConfigurationPropertyName.EMPTY, Bindable.ofInstance(result));
    }

}
