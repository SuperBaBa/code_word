package org.jarvis.consumer;

import io.r2dbc.h2.H2ConnectionConfiguration;
import io.r2dbc.h2.H2ConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;
import org.jarvis.consumer.converter.MoneyReadConverter;
import org.jarvis.consumer.converter.MoneyWriteConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.convert.CustomConversions;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.dialect.Dialect;
import org.springframework.data.r2dbc.function.convert.R2dbcCustomConversions;

import java.util.Arrays;

/**
 * initialize customized {@link R2dbcCustomConversions} and {@link ConnectionFactory}
 * 集成抽象配置类，gain sql Dialect Object by invoke method getDialect
 * @author marcus @date 2020/8/6
 **/
@Configuration
public class R2dbcConfiguration extends AbstractR2dbcConfiguration {
    @Override
    @Bean
    public ConnectionFactory connectionFactory() {
        return new H2ConnectionFactory(
                H2ConnectionConfiguration.builder()
                        .inMemory("testdb")
                        .username("sa")
                        .build());
    }

    /**
     * 自定义{@link org.joda.money.Money} 对象存储或读取的转换器，需要放入{@link R2dbcCustomConversions}
     *
     * @return Customized convertor of R2dbc
     */
    @Override
    @Bean
    public R2dbcCustomConversions r2dbcCustomConversions() {
        Dialect dialect = getDialect(connectionFactory());
        CustomConversions.StoreConversions storeConversions =
                CustomConversions.StoreConversions.of(dialect.getSimpleTypeHolder());
        return new R2dbcCustomConversions(storeConversions,
                Arrays.asList(new MoneyReadConverter(), new MoneyWriteConverter()));
    }
}
