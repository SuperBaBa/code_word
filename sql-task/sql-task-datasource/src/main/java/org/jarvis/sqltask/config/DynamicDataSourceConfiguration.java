package org.jarvis.sqltask.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.jarvis.sqltask.datasource.DataSourceBuilder;
import org.jarvis.sqltask.datasource.DataSourceProperties;
import org.jarvis.sqltask.datasource.DataSourceProperty;
import org.jarvis.sqltask.datasource.DynamicDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 启用资源配置文件{@code DataSourceProperties},将其注册到{@code ApplicationContext}中
 * 用于我们在初始化数据源是自动装配
 *
 * @author marcus
 * @date 2020/11/13-16:46
 */
@Configuration
@EnableConfigurationProperties(value = DataSourceProperties.class)
@Slf4j
public class DynamicDataSourceConfiguration {
    /**
     * 数据源资源文件,内存储了多条key与数据源映射关系
     * 后续会通过key获取数据源，达到切换的目的
     */
    private final DataSourceProperties dataSourceProperties;

    public DynamicDataSourceConfiguration(DataSourceProperties dataSourceProperties) {
        this.dataSourceProperties = dataSourceProperties;
    }

    @Bean
    public DynamicDataSource dynamicDataSource() {
        //获取动态数据库的实例（单例方式）
        DynamicDataSource dynamicDataSource = DynamicDataSource.getInstance();
        //设置默认数据源
        dynamicDataSource.setPrimary(dataSourceProperties.getPrimary());
        dynamicDataSource.initDataSourceCollection(loadDataSourceMap());
        return dynamicDataSource;
    }

    public Map<String, DataSource> loadDataSourceMap() {
        Map<String, DataSourceProperty> dataSourceMap = dataSourceProperties.getDataSource();
        Map<String, DataSource> resultDataSourceMap = new HashMap(dataSourceMap.size());
        for (Map.Entry<String, DataSourceProperty> dsItem : dataSourceMap.entrySet()) {
            String dsUniqueName = dsItem.getKey();
            DataSourceProperty dynamicDataSourceProperty = dsItem.getValue();
            DataSourceBuilder builder = new DataSourceBuilder();
            BeanUtils.copyProperties(dynamicDataSourceProperty, builder);
            try {
                DataSource dataSource = builder
                        .url(dynamicDataSourceProperty.getUrl())
                        .username(dynamicDataSourceProperty.getUsername())
                        .password(dynamicDataSourceProperty.getPassword())
                        .type(dynamicDataSourceProperty.getType().getDataSourceReferenceName())
                        .maxActive(dynamicDataSourceProperty.getMaxActive())
                        .validationQuery(dynamicDataSourceProperty.getValidationQuery())
                        .maxWait(dynamicDataSourceProperty.getMaxWait())
                        .build();
                resultDataSourceMap.put(dsUniqueName, dataSource);
            } catch (ClassNotFoundException e) {
                log.error("{}数据源类未找到,请检查DataSource依赖", dynamicDataSourceProperty.getType().getDataSourceReferenceName());
            }
        }
        return resultDataSourceMap;
    }

    /**
     * 　配置mybatis的sqlSession连接动态数据源
     *
     * @param dynamicDataSource
     * @return
     * @throws Exception
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dynamicDataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dynamicDataSource);
        return bean.getObject();
    }

    @Bean(name = "sqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
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

//    @Bean
//    @ConfigurationProperties(value = "first.datasource")
//    public DataSourceProperties firstDataSourceProperties() {
//        return new DataSourceProperties();
//    }
//
//    /**
//     * 因为关闭了{@link DataSourceAutoConfiguration} 导致无法自动执行schema.sql/data.sql
//     * 因此在自定义{@link DataSource}实例化时，同时获取资源，并执行sql
//     *
//     * @return
//     */
//    @Bean(name = "firstDataSource")
//    @Primary
//    public DataSource firstDataSource() {
//        DataSourceProperties firstDataSourceProperties = firstDataSourceProperties();
//        firstDataSourceProperties.setSqlScriptEncoding(StandardCharsets.UTF_8);
//        firstDataSourceProperties.setSchema(Arrays.asList("classpath:schema.sql"));
//        firstDataSourceProperties.setData(Arrays.asList("classpath:data.sql"));
//        DataSource dataSource = firstDataSourceProperties.initializeDataSourceBuilder().build();
//        {
//            ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
//            try {
//                SortedResourcesFactoryBean factory = new SortedResourcesFactoryBean(
//                        Collections.singletonList("classpath:schema.sql"));
//                factory.afterPropertiesSet();
//                populator.addScripts(Objects.requireNonNull(factory.getObject()));
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            DatabasePopulatorUtils.execute(populator, dataSource);
//        }
//        return dataSource;
//    }
//
//    @Bean
//    public PlatformTransactionManager firstTxManager() {
//        return new DataSourceTransactionManager(firstDataSource());
//    }
//
//    @Bean(name = "firstJdbcTemplate")
//    @Primary
//    JdbcTemplate firstJdbcTemplate() {
//        return new JdbcTemplate(firstDataSource());
//    }
//
//    @Bean
//    @ConfigurationProperties(value = "second.datasource")
//    public DataSourceProperties secondDataSourceProperties() {
//        return new DataSourceProperties();
//    }
//
//    @Bean
//    public DataSource secondDataSource() {
//        DataSourceProperties secondDataSourceProperties = secondDataSourceProperties();
//        secondDataSourceProperties.setSqlScriptEncoding(StandardCharsets.UTF_8);
//        return secondDataSourceProperties.initializeDataSourceBuilder().build();
//    }
//
//    @Bean
//    public PlatformTransactionManager secondTxManager() {
//        return new DataSourceTransactionManager(secondDataSource());
//    }
//
//    @Bean(value = "secondJdbcTemplate")
//    JdbcTemplate secondJdbcTemplate() {
//        return new JdbcTemplate(secondDataSource());
//    }

}
