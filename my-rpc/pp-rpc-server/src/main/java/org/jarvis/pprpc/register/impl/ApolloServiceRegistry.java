package org.jarvis.pprpc.register.impl;

import org.jarvis.pprpc.register.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Marcus
 * @date 2021/2/16-14:31
 * @description zookeeper服务注册初始化
 */
public class ApolloServiceRegistry implements ServiceRegistry {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApolloServiceRegistry.class);

    @Override
    public void register(String serviceName, String serviceAddress) {

    }
}
