package org.jarvis.controller;

import org.jarvis.entity.TaskCron;
import org.jarvis.task.CronTaskRegistrar;
import org.jarvis.task.ScheduledTaskRunnable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author marcus
 * @date 2020/11/12-23:54
 */
@RestController
@RequestMapping(value = "cron")
public class CronController {

    @Autowired
    private CronTaskRegistrar taskRegistrar;

    @PostMapping(value = "/save")
    public void saveCorn(@RequestParam(value = "cronExpression") String cronExpression, Integer id) {
        TaskCron taskCron1 = new TaskCron();
        taskCron1.setCronStr(cronExpression);
        taskCron1.setStatus(1);
        taskCron1.setId(id);
        if (taskCron1.getStatus() == 1) {
            ScheduledTaskRunnable task = new ScheduledTaskRunnable(taskCron1);
//            taskRegistrar.removeCronTask(taskCron.getId());
            taskRegistrar.addCronTask(taskCron1.getId(), task, taskCron1.getCronStr());
        }
    }

    @DeleteMapping(value = "remove")
    public void removeCorn(Integer id) {
        taskRegistrar.removeCronTask(id);
    }
}
