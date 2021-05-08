package org.jarvis.pprpc.api.discovery;

/**
 * @author Marcus
 * @date 2021/2/16-17:41
 * @description 服务发现方法
 */
public interface ServiceDiscovery {
    /**
     * 根据服务名称查找服务地址
     *
     * @param serviceName 服务名称
     * @return 服务地址
     */
    String discover(String serviceName);
}
