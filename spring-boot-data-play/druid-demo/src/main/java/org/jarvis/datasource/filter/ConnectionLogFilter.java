package org.jarvis.datasource.filter;

import com.alibaba.druid.filter.FilterChain;
import com.alibaba.druid.filter.FilterEventAdapter;
import com.alibaba.druid.proxy.jdbc.ConnectionProxy;
import lombok.extern.slf4j.Slf4j;

import java.util.Properties;

/**
 * 继承{@link FilterEventAdapter}并重写{@link FilterEventAdapter#connection_connectBefore(FilterChain, Properties)}
 * 可以实现对druid建立{@link java.sql.Connection}之前或之后进行拦截，做一些处理，比如对数据库密码进行解密或加密
 * author:tennyson date:2020/7/4
 **/
@Slf4j
public class ConnectionLogFilter extends FilterEventAdapter {
    @Override
    public void connection_connectAfter(ConnectionProxy connection) {
        log.info("AFTER CONNECTION!链接建立完毕");
    }

    @Override
    public void connection_connectBefore(FilterChain chain, Properties info) {
        log.info("BEFORE CONNECTION!这是建立连接之前");
    }
}
