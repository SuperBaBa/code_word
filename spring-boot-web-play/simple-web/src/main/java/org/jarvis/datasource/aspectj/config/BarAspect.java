package org.jarvis.consumer.aspectj.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * @author marcus
 * @date 2020/9/6-16:24
 */
@Aspect
@Slf4j
public class BarAspect {
    @Around(value = "bean(testBean*)")
    public void round() {
        log.info("advance test bean");
    }
}
