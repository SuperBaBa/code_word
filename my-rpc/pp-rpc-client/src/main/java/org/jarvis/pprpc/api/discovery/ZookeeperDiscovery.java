package org.jarvis.pprpc.api.discovery;

import org.I0Itec.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Marcus
 * @date 2021/2/16-17:43
 * @description this is function
 */
public class ZookeeperDiscovery implements ServiceDiscovery {
    private final Logger LOGGER = LoggerFactory.getLogger(ZookeeperDiscovery.class);
    private final String zkAddress;
    private final String ZK_SERVICE_PATH_PREFIX = "/pp/service-registry-center";

    public ZookeeperDiscovery(String zkAddress) {
        this.zkAddress = zkAddress;
    }

    @Override
    public String discover(String serviceName) {
        // 创建 ZooKeeper 客户端
        ZkClient zkClient = new ZkClient(zkAddress, 5000, 1000);
        try {
            LOGGER.debug("connect zookeeper");
            // 获取 service 节点
            String servicePath = ZK_SERVICE_PATH_PREFIX + "/" + serviceName;
            if (zkClient.exists(servicePath)) {
                throw new RuntimeException(String.format("can not find any service node on path: %s", servicePath));
            }
            //如果存在则获取下方的临时节点
            List<String> addressList = zkClient.getChildren(servicePath);
            if (addressList.isEmpty()) {
                throw new RuntimeException(String.format("can not find any address node on path: %s", servicePath));
            }
            // 获取 address 节点
            String address;
            int size = addressList.size();
            if (size == 1) {
                // 若只有一个地址，则获取该地址
                address = addressList.get(0);
                LOGGER.debug("get only address node: {}", address);
            } else {
                // 若存在多个地址，则随机获取一个地址
                //TODO 此处可以使用算法做到负载策略
                address = addressList.get(ThreadLocalRandom.current().nextInt(size));
                LOGGER.debug("get random address node: {}", address);
            }
            // 获取 address 节点的值
            String addressPath = servicePath + "/" + address;
            return zkClient.readData(addressPath);
        } finally {
            zkClient.close();
        }
    }
}
