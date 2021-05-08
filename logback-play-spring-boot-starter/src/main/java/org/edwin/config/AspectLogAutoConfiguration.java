package org.edwin.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.edwin.annotation.EnableAspectLog;
import org.edwin.service.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.annotation.Order;

import java.util.Arrays;
import java.util.Date;

import static net.logstash.logback.argument.StructuredArguments.v;

/**
 * @author marcus
 * @date 2020/8/11-15:43
 */
@Aspect
@EnableAspectJAutoProxy(exposeProxy = true, proxyTargetClass = true)
@Configuration
@EnableConfigurationProperties(AspectLogProperties.class)
@AutoConfigureAfter(value = AspectLogProperties.class)
@ConditionalOnProperty(prefix = "jarvis.log", name = "enable", havingValue = "true", matchIfMissing = false)
@Order
@ConditionalOnMissingBean
@ConditionalOnBean(annotation = EnableAspectLog.class)
public class AspectLogAutoConfiguration {
    private Logger logger = LoggerFactory.getLogger(AspectLogAutoConfiguration.class);
    @Autowired
    private AspectLogProperties aspectLogProperties;

    @Bean
    @ConditionalOnMissingBean
    public LogService initializeLogService() {
        logger.warn("Initializing LogService");
        return LogService.builder().name(aspectLogProperties.getAppName()).createTime(new Date()).build();
    }

    @Around(value = "@annotation(org.edwin.annotation.AspectLog)")
    public Object isOpen(ProceedingJoinPoint thisJoinPoint) throws Throwable {
        //执行方法名称
        String taskName = thisJoinPoint.getSignature().toString().substring(
                thisJoinPoint.getSignature().toString().indexOf(" "),
                thisJoinPoint.getSignature().toString().indexOf("("));
        taskName = taskName.trim();
        long time = System.currentTimeMillis();
        Object result = thisJoinPoint.proceed();
        logger.info("method={},arguments={},appName={},captain={},spend={} ms",
                v("method", taskName),
                v("arguments", Arrays.asList(thisJoinPoint.getArgs()).toString()),
                v("appName", aspectLogProperties.getAppName()),
                v("captain", aspectLogProperties.getCaptainName()),
                v("spend", System.currentTimeMillis() - time));
        return result;
    }
}
