package org.jarvis.consumer.rocketmq.config;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqProducerConfig {
    private final String PRODUCER_GROUP = "p_paly_group";
    @Value("${rocketmq.namserv}")
    private String nameserv;


    @Bean
    public DefaultMQProducer generatProducer() {
        DefaultMQProducer producer = new DefaultMQProducer();
        producer.setProducerGroup(PRODUCER_GROUP);
        producer.setNamesrvAddr(nameserv);
        producer.setRetryAnotherBrokerWhenNotStoreOK(Boolean.TRUE);
        return producer;
    }
}
