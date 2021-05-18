package org.jarvis.sqltask.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.jarvis.sqltask.annotation.ChangeDS;
import org.jarvis.sqltask.util.DynamicDataSourceContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

/**
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
        try {
            MethodSignature signature = (MethodSignature) point.getSignature();
            ChangeDS changeDSAnnotation = signature.getMethod().getAnnotation(ChangeDS.class);
            String dsKey = changeDSAnnotation.value();
            LOG.info("当前线程准备使用{}数据源", dsKey);
            DynamicDataSourceContextHolder.setDataSourceLookupKey(dsKey);
            return point.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;
    }
}
