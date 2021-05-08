package org.jarvis.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.jarvis.annotation.ChangeDS;
import org.jarvis.util.DynamicDataSourceContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

/**
 * 生命切点,即被{@code ChangeDS}标记的方法,根据注解中的value设置当前线程锁需要使用数据源
 *
 * @author marcus
 * @date 2020/11/16-16:37
 */
@Aspect
@EnableAspectJAutoProxy
@Component
public class DynamicDataSourceAnnotationProcess {
    private final static Logger LOG = LoggerFactory.getLogger(DynamicDataSourceAnnotationProcess.class);

    @Pointcut(value = "@annotation(org.jarvis.sqltask.annotation.ChangeDS)")
    public void changeDSPointCut() {
    }

    @Around(value = "changeDSPointCut()")
    public Object aroundChangeDSPointCut(ProceedingJoinPoint point) {
        MethodSignature signature = null;
        ChangeDS changeDSAnnotation;
        String dsKey;
        try {
            signature = (MethodSignature) point.getSignature();
            changeDSAnnotation = signature.getMethod().getAnnotation(ChangeDS.class);
            dsKey = changeDSAnnotation.value();
            LOG.info("当前线程准备使用{}数据源", dsKey);
            DynamicDataSourceContextHolder.setDataSourceLookupKey(dsKey);
            return point.proceed();
        } catch (Throwable throwable) {
            LOG.error("{}方法代理失败", signature.getMethod(), throwable);
            throwable.printStackTrace();
        }
        return null;
    }
}
