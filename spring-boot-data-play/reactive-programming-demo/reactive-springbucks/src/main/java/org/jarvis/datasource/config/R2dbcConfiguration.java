package org.jarvis.consumer.config;

import io.r2dbc.h2.H2ConnectionConfiguration;
import io.r2dbc.h2.H2ConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;
import org.jarvis.consumer.convertor.MoneyReadConverter;
import org.jarvis.consumer.convertor.MoneyWriteConverter;
import org.jarvis.consumer.model.Coffee;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.convert.CustomConversions;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.dialect.Dialect;
import org.springframework.data.r2dbc.function.convert.R2dbcCustomConversions;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.Arrays;

import static org.springframework.data.convert.CustomConversions.StoreConversions.of;
import static org.springframework.data.redis.serializer.RedisSerializationContext.newSerializationContext;

/**
 * @author tennyson
 * @date 2020/8/8-23:06
 */
@Configuration
public class R2dbcConfiguration extends AbstractR2dbcConfiguration {
    @Bean
    @Override
    public ConnectionFactory connectionFactory() {
        return new H2ConnectionFactory(
                H2ConnectionConfiguration.builder()
                        .inMemory("springbucks")
                        .username("jarvis")
                        .build());
    }
    @Bean
    @Override
    public R2dbcCustomConversions r2dbcCustomConversions() {
        Dialect dialect = getDialect(connectionFactory());
        CustomConversions.StoreConversions storeConversions = of(dialect.getSimpleTypeHolder());
        return new R2dbcCustomConversions(storeConversions,
                Arrays.asList(new MoneyReadConverter(), new MoneyWriteConverter()));
    }

    @Bean
    public ReactiveRedisTemplate<String, Coffee> reactiveRedisTemplate(ReactiveRedisConnectionFactory factory) {
        StringRedisSerializer keySerializer = new StringRedisSerializer();
        Jackson2JsonRedisSerializer<Coffee> valueSerializer = new Jackson2JsonRedisSerializer<>(Coffee.class);

        RedisSerializationContext.RedisSerializationContextBuilder<String, Coffee> builder = newSerializationContext(keySerializer);

        RedisSerializationContext<String, Coffee> context = builder.value(valueSerializer).build();

        return new ReactiveRedisTemplate<>(factory, context);
    }
}
