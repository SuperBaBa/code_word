package org.jarvis.pprpc.register.impl;

import org.I0Itec.zkclient.ZkClient;
import org.jarvis.pprpc.register.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Marcus
 * @date 2021/2/16-14:30
 * @description zookeeper服务注册初始化
 */
public class ZooKeeperServiceRegistry implements ServiceRegistry {
    private static final Logger LOGGER = LoggerFactory.getLogger(ZooKeeperServiceRegistry.class);
    private final String ZK_REGISTRY_PATH_PREFIX = "/pp/service-registry-center";
    private final ZkClient zkClient;

    public ZooKeeperServiceRegistry(String zkAddress) {
        // 创建 ZooKeeper 客户端
        zkClient = new ZkClient(zkAddress, 5000, 1000);
        LOGGER.debug("connect zookeeper");
    }


    @Override
    public void register(String serviceName, String serviceAddress) {
        // 创建 registry 节点（持久）
        if (!zkClient.exists(ZK_REGISTRY_PATH_PREFIX)) {
            zkClient.createPersistent(ZK_REGISTRY_PATH_PREFIX);
            LOGGER.debug("create registry node: {}", ZK_REGISTRY_PATH_PREFIX);
        }
        // 创建 service 节点（持久）
        String servicePath = ZK_REGISTRY_PATH_PREFIX + "/" + serviceName;
        if (!zkClient.exists(servicePath)) {
            zkClient.createPersistent(servicePath);
            LOGGER.debug("create service node: {}", servicePath);
        }
        // 创建 address 节点（临时）
        String addressPath = servicePath + "/address-";
        String addressNode = zkClient.createEphemeralSequential(addressPath, serviceAddress);
        LOGGER.debug("create address node: {}", addressNode);
    }
}
