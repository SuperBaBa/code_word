package org.jarvis.pprpc.api.client;

import org.jarvis.pprpc.api.common.dto.PPRpcRequest;
import org.jarvis.pprpc.api.common.dto.PPRpcResponse;
import org.jarvis.pprpc.api.discovery.ServiceDiscovery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.lang.reflect.Proxy;
import java.util.UUID;

/**
 * @author Marcus
 * @date 2021/2/17-10:57
 * @description 远程调用方法代理, 此处使用cglib进行动态代理
 */
public class RpcProxy {
    private static final Logger LOGGER = LoggerFactory.getLogger(RpcProxy.class);

    private final ServiceDiscovery serviceDiscovery;
    private String serviceAddress;

    public RpcProxy(ServiceDiscovery serviceDiscovery) {
        this.serviceDiscovery = serviceDiscovery;
    }

    @SuppressWarnings("unchecked")
    public <T> T create(final Class<?> interfaceClass, final String serviceVersion) {
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(),
                interfaceClass.getInterfaces(),
                (proxy, method, args) -> {
                    // 创建 RPC 请求对象并设置请求属性
                    PPRpcRequest request = new PPRpcRequest();
                    request.setRequestId(UUID.randomUUID().toString());
                    request.setInterfaceName(method.getDeclaringClass().getName());
                    request.setServiceVersion(serviceVersion);
                    // 获取 RPC 服务地址
                    if (serviceDiscovery != null) {
                        String serviceName = interfaceClass.getName();
                        if (!StringUtils.isEmpty(serviceVersion)) {
                            serviceName += "-" + serviceVersion;
                        }
                        serviceAddress = serviceDiscovery.discover(serviceName);
                        LOGGER.debug("discover service: {} => {}", serviceName, serviceAddress);
                        if (StringUtils.isEmpty(serviceAddress)) {
                            throw new RuntimeException("server address is empty");
                        }
                    }
                    // 从 RPC 服务地址中解析主机名与端口号
                    String[] array = StringUtils.split(serviceAddress, ":");
                    assert array != null;
                    String host = array[0];
                    int port = Integer.parseInt(array[1]);
                    // 创建 RPC 客户端对象并发送 RPC 请求
                    RpcClient client = new RpcClient(host, port);
                    long time = System.currentTimeMillis();
                    PPRpcResponse response = client.send(request);
                    LOGGER.debug("time: {}ms", System.currentTimeMillis() - time);
                    if (response == null) {
                        throw new RuntimeException("response is null");
                    }
                    // 返回 RPC 响应结果
                    if (response.getException() != null) {
                        throw response.getException();
                    } else {
                        return response.getResult();
                    }
                });
    }
}
