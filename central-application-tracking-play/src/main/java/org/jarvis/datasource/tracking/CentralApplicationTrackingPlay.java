package org.jarvis.consumer.tracking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author marcus
 * @date 2020/9/23-15:36
 */
@SpringBootApplication
@EnableAspectJAutoProxy
public class CentralApplicationTrackingPlay {
    public static void main(String[] args) {
        SpringApplication.run(CentralApplicationTrackingPlay.class, args);
    }
}
