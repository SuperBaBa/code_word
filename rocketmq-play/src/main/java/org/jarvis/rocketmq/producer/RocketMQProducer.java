package org.jarvis.rocketmq.producer;

import com.sun.istack.internal.NotNull;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.UtilAll;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.jarvis.rocketmq.config.RocketMQProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;


/**
 * rocketmq生产者封装
 * 提供消息发送，封装topicName和tags在对象中
 *
 * @author Marcus
 * @date 2021/5/6-10:34
 */
public class RocketMQProducer {
    private final Logger log = LoggerFactory.getLogger(RocketMQProducer.class);
    /*topicName和tags因为在buildMessage时可能需要用到，所以可以自定义*/
    private String topicName;
    private String tags;
    private DefaultMQProducer producer = new DefaultMQProducer();
    private RocketMQProperty property;
    private final static String INSTANCE_NAME = "basesrv-waybillcenter-";

    public RocketMQProducer(RocketMQProperty property) {
        this(property, property.getTopicName(), property.getTags());
    }

    public RocketMQProducer(RocketMQProperty property, String tags) {
        this(property, property.getTopicName(), tags);
    }

    public RocketMQProducer(RocketMQProperty property, String topicName, String tags) {
        this.property = property;
        this.topicName = topicName;
        this.tags = tags;
        log.debug("设置mq的groupName为:{}", property.getGroupName());
        producer.setProducerGroup(property.getGroupName());

        log.debug("设置mq的namesrvAddr为:{}", property.getNamesrvAddr());
        producer.setNamesrvAddr(property.getNamesrvAddr());

        log.debug("设置mq的最大消息大小为:{}", property.getNamesrvAddr());
        producer.setMaxMessageSize(property.getMaxMessageSize());

        log.debug("设置mq的发送超时时间为:{}", property.getSendMsgTimeout());
        producer.setSendMsgTimeout(property.getSendMsgTimeout());

        log.debug("设置重试次数为:{}", property.getRetryTimesWhenSendFailed());
        producer.setRetryTimesWhenSendFailed(property.getRetryTimesWhenSendFailed());
        String instanceName = topicName + UtilAll.getPid();

        log.debug("设置mqClient实例名为:{}", instanceName);
        producer.setInstanceName(instanceName);

    }

    public void start() throws MQClientException {
        this.producer.start();
    }

    public void shutdown() {
        log.info("开始关闭RocketMQProducer");
        this.producer.shutdown();
    }

    public SendResult sendMessage(@NotNull String body, String key) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        return sendMessage(body, this.tags, key);
    }

    public SendResult sendMessage(@NotNull String body, String tags, String key) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        return sendMessage(body, this.topicName, tags, key);
    }

    public SendResult sendMessage(@NotNull String body, String topicName, String tags, String key) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        Message message = new Message(topicName, tags, key, body.getBytes(StandardCharsets.UTF_8));
        return producer.send(message);
    }

    public void sendMessageAsync(@NotNull String body, @NotNull SendCallback sendCallback) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        sendMessageAsync(body, "", sendCallback);
    }

    public void sendMessageAsync(@NotNull String body, String key, @NotNull SendCallback sendCallback) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        sendMessageAsync(body, tags, key, sendCallback);
    }

    public void sendMessageAsync(@NotNull String body, String tags, String key, @NotNull SendCallback sendCallback) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        sendMessageAsync(body, "DEFAULT", tags, key, sendCallback);
    }

    public void sendMessageAsync(@NotNull String body, String topicName, String tags, String key, @NotNull SendCallback sendCallback) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        Message message = new Message(topicName, tags, key, body.getBytes(StandardCharsets.UTF_8));
        producer.send(message, sendCallback);
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public DefaultMQProducer getProducer() {
        return producer;
    }

    public void setProducer(DefaultMQProducer producer) {
        this.producer = producer;
    }

    public RocketMQProperty getProperty() {
        return property;
    }

    public void setProperty(RocketMQProperty property) {
        this.property = property;
    }
}
