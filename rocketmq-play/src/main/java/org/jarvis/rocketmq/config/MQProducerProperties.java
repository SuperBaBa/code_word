package org.jarvis.rocketmq.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * <一句话概述功能>
 *
 * @author Marcus
 * @date 2021/5/6-9:33
 * @description this is function description
 */
@ConfigurationProperties(prefix = "rocketmq.producer")
@Data
public class MQProducerProperties {
    /**
     * 组名与配置映射关系
     */
    private Map<String, RocketMQProperty> propertyMap = new HashMap<>();
    /**
     * 客户端组名称，默认:WAYBILL-CENTER-DEFAULT
     */
    private String groupName = "WAYBILL-CENTER-DEFAULT";
    /**
     * rocketmq的nameServer地址=>{ip:port}
     */
    private String namesrvAddr;
    /**
     * 消息最大大小，默认4M
     */
    private int maxMessageSize = 1024 * 1024 * 4; // 4M
    /**
     * 消息发送超时时间，默认3秒
     */
    private int sendMsgTimeout = 3000;
    /**
     * 消息发送失败重试次数，默认2次
     */
    private int retryTimesWhenSendFailed = 2;
    /**
     * 客户端创建的队列最大不超过topic中设置的队列数
     */
    private volatile int defaultTopicQueueNums = 4;
    private int compressMsgBodyOverHowmuch = 1024 * 4;
    private int retryTimesWhenSendAsyncFailed = 2;
    private boolean retryAnotherBrokerWhenNotStoreOK = false;
}
