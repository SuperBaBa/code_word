package org.jarvis.config;

import com.alibaba.druid.pool.DruidDataSource;
import io.prometheus.client.exporter.MetricsServlet;
import io.prometheus.client.hotspot.DefaultExports;
import org.apache.ibatis.session.SqlSessionFactory;
import org.jarvis.util.DynamicDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.config.SortedResourcesFactoryBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author marcus
 * @date 2020/11/2-0:32
 */
@Configuration
public class DataSourceConfigurer {
    //日志logger句柄
    private final Logger logger = LoggerFactory.getLogger(getClass());
    //自动注入环境类，用于获取配置文件的属性值
    @Autowired
    private Environment evn;

    private MybatisProperties mybatisProperties;

    public DataSourceConfigurer(MybatisProperties properties) {
        this.mybatisProperties = properties;
    }

    /**
     * 创建数据源对象
     *
     * @param dbType 数据库类型
     * @return data source
     */
    private DruidDataSource createDataSource(String dbType) {
        //如果不指定数据库类型，则使用默认数据库连接
        String dbName = dbType.trim().isEmpty() ? "default" : dbType.trim();
        DruidDataSource dataSource = new DruidDataSource();
        String prefix = "db." + dbName + ".";
        String dbUrl = evn.getProperty(prefix + "url-base")
                + evn.getProperty(prefix + "host") + ":"
                + evn.getProperty(prefix + "port") + "/"
                + evn.getProperty(prefix + "dbname") + evn.getProperty(prefix + "url-other");
        logger.info("+++default默认数据库连接url = " + dbUrl);
        dataSource.setUrl(dbUrl);
        dataSource.setUsername(evn.getProperty(prefix + "username"));
        dataSource.setPassword(evn.getProperty(prefix + "password"));
        dataSource.setDriverClassName(evn.getProperty(prefix + "driver-class-name"));
        return dataSource;
    }

    /**
     * spring boot 启动后将自定义创建好的数据源对象放到TargetDataSources中用于后续的切换数据源用
     * (比如：DynamicDataSourceContextHolder.setDataSourceKey("dbMall")，手动切换到dbMall数据源
     * 同时指定默认数据源连接
     *
     * @return 动态数据源对象
     */
    @Bean
    public DynamicDataSource dynamicDataSource() {
        //获取动态数据库的实例（单例方式）
        DynamicDataSource dynamicDataSource = DynamicDataSource.getInstance();
        //创建默认数据库连接对象
        DruidDataSource defaultDataSource = createDataSource("default");
        //创建db_mall数据库连接对象
        DruidDataSource mallDataSource = createDataSource("dbMall");

        Map<Object, Object> map = new HashMap<>();
        //自定义数据源key值，将创建好的数据源对象，赋值到targetDataSources中,用于切换数据源时指定对应key即可切换
        map.put("default", defaultDataSource);
        map.put("dbMall", mallDataSource);
        dynamicDataSource.setTargetDataSources(map);
        //设置默认数据源
        dynamicDataSource.setDefaultTargetDataSource(defaultDataSource);

        return dynamicDataSource;
    }

    /**
     * 　配置mybatis的sqlSession连接动态数据源
     *
     * @param dynamicDataSource
     * @return
     * @throws Exception
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory(
            @Qualifier("dynamicDataSource") DataSource dynamicDataSource)
            throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dynamicDataSource);
        bean.setMapperLocations(mybatisProperties.resolveMapperLocations());
        bean.setTypeAliasesPackage(mybatisProperties.getTypeAliasesPackage());
        bean.setConfiguration(mybatisProperties.getConfiguration());
        return bean.getObject();
    }

    @Bean(name = "sqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(
            @Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory)
            throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    /**
     * 将动态数据源添加到事务管理器中，并生成新的bean
     *
     * @return the platform transaction manager
     */
    @Bean
    public PlatformTransactionManager transactionManager(DynamicDataSource dynamicDataSource) {
        return new DataSourceTransactionManager(dynamicDataSource);
    }


    @Bean
    @ConfigurationProperties(value = "first.datasource")
    public DataSourceProperties firstDataSourceProperties() {
        return new DataSourceProperties();
    }

    /**
     * 因为关闭了{@link DataSourceAutoConfiguration} 导致无法自动执行schema.sql/data.sql
     * 因此在自定义{@link DataSource}实例化时，同时获取资源，并执行sql
     *
     * @return
     */
    @Bean(name = "firstDataSource")
    @Primary
    public DataSource firstDataSource() {
        DataSourceProperties firstDataSourceProperties = firstDataSourceProperties();
        firstDataSourceProperties.setSqlScriptEncoding(StandardCharsets.UTF_8);
        firstDataSourceProperties.setSchema(Arrays.asList("classpath:schema.sql"));
        firstDataSourceProperties.setData(Arrays.asList("classpath:data.sql"));
        DataSource dataSource = firstDataSourceProperties.initializeDataSourceBuilder().build();
        {
            ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
            try {
                SortedResourcesFactoryBean factory = new SortedResourcesFactoryBean(
                        Collections.singletonList("classpath:schema.sql"));
                factory.afterPropertiesSet();
                populator.addScripts(Objects.requireNonNull(factory.getObject()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            DatabasePopulatorUtils.execute(populator, dataSource);
        }
        return dataSource;
    }

    @Bean
    public PlatformTransactionManager firstTxManager() {
        return new DataSourceTransactionManager(firstDataSource());
    }

    @Bean(name = "firstJdbcTemplate")
    @Primary
    JdbcTemplate firstJdbcTemplate() {
        return new JdbcTemplate(firstDataSource());
    }

    @Bean
    @ConfigurationProperties(value = "second.datasource")
    public DataSourceProperties secondDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource secondDataSource() {
        DataSourceProperties secondDataSourceProperties = secondDataSourceProperties();
        secondDataSourceProperties.setSqlScriptEncoding(StandardCharsets.UTF_8);
        return secondDataSourceProperties.initializeDataSourceBuilder().build();
    }

    @Bean
    public PlatformTransactionManager secondTxManager() {
        return new DataSourceTransactionManager(secondDataSource());
    }

    @Bean(value = "secondJdbcTemplate")
    JdbcTemplate secondJdbcTemplate() {
        return new JdbcTemplate(secondDataSource());
    }

    @Bean
    public ServletRegistrationBean servletRegistrationBean() {
        DefaultExports.initialize();
        return new ServletRegistrationBean(new MetricsServlet(), "/metrics");
    }
}
