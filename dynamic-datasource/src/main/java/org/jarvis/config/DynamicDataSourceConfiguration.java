package org.jarvis.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.jarvis.datasource.DataSourceBuilder;
import org.jarvis.datasource.DataSourceProperties;
import org.jarvis.datasource.DataSourceProperty;
import org.jarvis.datasource.DynamicDataSource;
import org.jarvis.driver.DatabaseDriverEnum;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.config.SortedResourcesFactoryBean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 启用资源配置文件{@code DataSourceProperties},将其注册到{@code ApplicationContext}中
 * 用于我们在初始化数据源是自动装配
 *
 * @author marcus
 * @date 2020/11/13-16:46
 */
@Configuration
@EnableConfigurationProperties(value = DataSourceProperties.class)
@Order(value = Integer.MAX_VALUE)
public class DynamicDataSourceConfiguration {
    private final static Logger log = LoggerFactory.getLogger(DynamicDataSourceConfiguration.class);
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
                if (builder.judgeDriverClassName(DatabaseDriverEnum.H2)) {
                    ResourceDatabasePopulator populator = new ResourceDatabasePopulator();

                    SortedResourcesFactoryBean factory = new SortedResourcesFactoryBean(
                            Collections.singletonList("classpath:schema.sql"));
                    factory.afterPropertiesSet();
                    populator.addScripts(Objects.requireNonNull(factory.getObject()));

                    DatabasePopulatorUtils.execute(populator, dataSource);
                }
                resultDataSourceMap.put(dsUniqueName, dataSource);
            } catch (ClassNotFoundException e) {
                log.error("{}数据源类未找到,请检查DataSource依赖", dynamicDataSourceProperty.getType().getDataSourceReferenceName());
            } catch (Exception e) {
                e.printStackTrace();
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
}
