package org.jarvis.task;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.config.CronTask;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 定时任务注册类，用于定时任务的启动和删除
 *
 * @author marcus
 * @date 2020/11/12-23:28
 */
@Component
public class CronTaskRegistrar implements DisposableBean {
    private final Map<Integer, ScheduledFutureTask> scheduledTasks = new ConcurrentHashMap<>(16);
    @Autowired
    @Qualifier(value = "dynamicScheduler")
    private TaskScheduler scheduler;

    public void addCronTask(Integer cronId, Runnable task, String cronExpression) {
        addCronTask(cronId, new CronTask(task, cronExpression));
    }

    public void addCronTask(Integer cronId, CronTask cronTask) {
        if (cronTask != null) {
            if (this.scheduledTasks.containsKey(cronId)) {
                removeCronTask(cronId);
            }
            this.scheduledTasks.put(cronId, scheduleCronTask(cronTask));
        }
    }

    public void removeCronTask(Integer cronId) {
        ScheduledFutureTask scheduledFutureTask = this.scheduledTasks.remove(cronId);
        if (scheduledFutureTask != null) {
            scheduledFutureTask.cancel();
        }
    }

    public ScheduledFutureTask scheduleCronTask(CronTask cronTask) {
        ScheduledFutureTask scheduledTask = new ScheduledFutureTask();
        scheduledTask.scheduledFuture = this.scheduler.schedule(cronTask.getRunnable(), cronTask.getTrigger());
        return scheduledTask;
    }


    @Override
    public void destroy() {
        for (ScheduledFutureTask task : this.scheduledTasks.values()) {
            task.cancel();
        }
        this.scheduledTasks.clear();
    }
}
