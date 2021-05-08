package org.jarvis.rocketmq.config;

import lombok.extern.slf4j.Slf4j;
import org.jarvis.rocketmq.producer.RocketMQProducer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Jared
 */
@Configuration
@Slf4j
@EnableConfigurationProperties({MQProducerProperties.class})
public class MqConfiguration {
    private final MQProducerProperties mqYTOProducerProperties;

    public MqConfiguration(MQProducerProperties mqYTOProducerProperties) {
        this.mqYTOProducerProperties = mqYTOProducerProperties;
    }

    @Bean(value = "ytoMQProducer", initMethod = "start", destroyMethod = "shutdown")
    public RocketMQProducer ytoMQProducer() {
        RocketMQProperty property = mqYTOProducerProperties.getPropertyMap().get("yto");
        return new RocketMQProducer(property, property.getTopicName(), property.getTags());
    }

    @Bean(value = "vasMQProducer", initMethod = "start", destroyMethod = "shutdown")
    public RocketMQProducer vasMQProducer() {
        RocketMQProperty property = mqYTOProducerProperties.getPropertyMap().get("vas");
        return new RocketMQProducer(property, property.getTopicName(), property.getTags());
    }

}
