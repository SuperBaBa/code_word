package org.jarvis.task;

import org.jarvis.entity.TaskCron;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author marcus
 * @date 2020/11/12-23:37
 */
public class ScheduledTaskRunnable implements Runnable {
    private Logger log = LoggerFactory.getLogger(ScheduledTaskRunnable.class);
    /**
     * 封装定时任务请求,其中较为重要的则是cron表达式
     */
    private TaskCron taskCron;

    @Override
    public void run() {
        log.info("{}正在执行任务", Thread.currentThread().getName());
    }

    public TaskCron getCronExpression() {
        return taskCron;
    }

    public TaskCron getTaskCron() {
        return taskCron;
    }

    public void setTaskCron(TaskCron taskCron) {
        this.taskCron = taskCron;
    }

    public ScheduledTaskRunnable(TaskCron taskCron) {
        this.taskCron = taskCron;
    }
}
