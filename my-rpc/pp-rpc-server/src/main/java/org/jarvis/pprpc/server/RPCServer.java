package org.jarvis.pprpc.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.jarvis.pprpc.register.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

/**
 * @author Marcus
 * @date 2021/2/16-13:22
 * @description 此类将用于注册当前所有需要暴露的服务,
 * 接口{@code ApplicationContextAware}实现是为了在初始化时
 * 调用该Bean的{@code ApplicationContextAware#setApplicationContext(ApplicationContext applicationContext)}方法
 * 会将容器本身作为参数传给该方法——该方法中的实现部分将Spring传入的参数（容器本身）赋给该类对象的applicationContext实例变量
 * <p>
 * 接口{@code InitializingBean}是为了在Bean初始化执行该方法,与init-method指定，两种方式可以同时使用
 * 实现InitializingBean接口是直接调用afterPropertiesSet方法
 * 比通过反射调用init-method指定的方法效率要高一点，但是init-method方式消除了对spring的依赖。
 */
public class RPCServer implements ApplicationContextAware, InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(RPCServer.class);

    private ApplicationContext context;

    public RPCServer(ApplicationContext context) {
        this.context = context;
    }

    private String serviceAddress;

    private ServiceRegistry serviceRegistry;

    /**
     * 存放 服务名 与 服务对象 之间的映射关系
     */
    private final Map<String, Object> handlerMap = new HashMap<>();

    public RPCServer(String serviceAddress, ServiceRegistry serviceRegistry) {
        this.serviceAddress = serviceAddress;
        this.serviceRegistry = serviceRegistry;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
        // 扫描带有 PPService 注解的类并初始化 handlerMap 对象
        Map<String, Object> serviceBeanMap = applicationContext.getBeansWithAnnotation(PPService.class);
        LOGGER.debug("获取到需要暴露的服务共计{}个", serviceBeanMap.size());
        if (!serviceBeanMap.isEmpty()) {
            for (Object serviceBean : serviceBeanMap.values()) {
                // 获取已打上标记的类中注解的 serviceName 和 version
                PPService ppService = serviceBean.getClass().getAnnotation(PPService.class);
                StringJoiner strJoiner = new StringJoiner("-");
                strJoiner.add(ppService.value().getName())
                        .add(ppService.version());
                // 拼接完成服务名后放置Map中留存映射关系
                handlerMap.put(strJoiner.toString(), serviceBean);
            }
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //boss线程监听端口，worker线程负责数据读写
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            // 创建并初始化 Netty 服务端 Bootstrap 对象
            ServerBootstrap bootstrap = new ServerBootstrap();
            //设置线程池
            bootstrap.group(bossGroup, workerGroup);
            //设置socket工厂
            bootstrap.channel(NioServerSocketChannel.class);
            //设置管道工厂
            bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel channel) throws Exception {
                    //获取管道
                    ChannelPipeline pipeline = channel.pipeline();
                    ;
                    //字符串解码器
                    pipeline.addLast(new StringDecoder());
                    //字符串编码器
                    pipeline.addLast(new StringEncoder());
                    //处理类
                    pipeline.addLast(new RPCServiceHandler(handlerMap));
                }
            });
            //设置TCP参数
            //1.链接缓冲池的大小（ServerSocketChannel的设置）
            bootstrap.option(ChannelOption.SO_BACKLOG, 1024);
            //维持链接的活跃，清除死链接(SocketChannel的设置)
            bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
            //关闭延迟发送
            bootstrap.childOption(ChannelOption.TCP_NODELAY, true);
            // 获取 RPC 服务器的 IP 地址与端口号
            String[] addressArray = StringUtils.split(serviceAddress, ":");
            String ip = addressArray[0];
            int port = Integer.parseInt(addressArray[1]);
            // 启动 RPC 服务器
            ChannelFuture future = bootstrap.bind(ip, port).sync();
            // 注册 RPC 服务地址
            if (serviceRegistry != null) {
                for (String interfaceName : handlerMap.keySet()) {
                    serviceRegistry.register(interfaceName, serviceAddress);
                    LOGGER.debug("register service: {} => {}", interfaceName, serviceAddress);
                }
            }
            LOGGER.debug("server started on port {}", port);
            // 关闭 RPC 服务器
            future.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }

    }
}
