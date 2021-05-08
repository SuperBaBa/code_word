package org.jarvis.pprpc.register;

/**
 * @author Marcus
 * @date 2021/2/16-14:28
 * @description this is function
 */
public interface ServiceRegistry {
    /**
     * 将需要暴露的服务注册到注册中心上
     *
     * @param serviceName
     * @param serviceAddress
     */
    void register(String serviceName, String serviceAddress);
}
