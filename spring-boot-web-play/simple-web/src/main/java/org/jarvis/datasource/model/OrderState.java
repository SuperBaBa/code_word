package org.jarvis.consumer.model;

/**
 * 初始状态/已支付状态/酝酿状态/制作完成/已带走状态/取消状态
 *
 * @author marcus
 * @date 2020/8/24-11:35
 */
public enum OrderState {
    INIT, PAID, BREWING, BREWED, TAKEN, CANCELLED
}
