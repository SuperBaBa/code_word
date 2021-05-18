package org.jarvis.config;

import org.jarvis.task.MonitorDBTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

/**
 * @author marcus
 * @date 2020/11/2-12:54
 */
@Component
public class SchedulingConfiguration implements SchedulingConfigurer {
    private String dynamicCronExpression = "0/5 * * * * ?";
    @Autowired
    private MonitorDBTask monitorDBTask;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.addTriggerTask(monitorDBTask, triggerContext -> {
            CronTrigger cronTrigger = new CronTrigger(dynamicCronExpression);
            return cronTrigger.nextExecutionTime(triggerContext);
        });
    }

    @Bean(name = "dynamicScheduler")
    public TaskScheduler generateTask() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(10);
        threadPoolTaskScheduler.initialize();
        //设置任务注册器的调度器
        threadPoolTaskScheduler.setRemoveOnCancelPolicy(true);
        //设置线程名称前缀
        threadPoolTaskScheduler.setThreadNamePrefix("dynamic-");
        return threadPoolTaskScheduler;
    }
}
