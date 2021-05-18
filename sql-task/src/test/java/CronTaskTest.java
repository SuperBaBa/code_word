import org.jarvis.DPSMonitorApplication;
import org.jarvis.entity.TaskCron;
import org.jarvis.task.CronTaskRegistrar;
import org.jarvis.task.ScheduledTaskRunnable;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author marcus
 * @date 2020/11/13-10:11
 */
@SpringBootTest(classes = DPSMonitorApplication.class)
@RunWith(SpringRunner.class)
public class CronTaskTest {
    @Autowired
    private CronTaskRegistrar taskRegistrar;

    @Test
    public void testDynamicCron() {
        TaskCron taskCron = new TaskCron();
        taskCron.setCronStr("*/5 * * * * ?");
        taskCron.setStatus(1);
        taskCron.setId(1);
        TaskCron taskCron2 = new TaskCron();
        taskCron2.setCronStr("*/2 * * * * ?");
        taskCron2.setStatus(1);
        taskCron2.setId(2);
        if (taskCron.getStatus() == 1) {
            ScheduledTaskRunnable task = new ScheduledTaskRunnable(taskCron);
//            taskRegistrar.removeCronTask(taskCron.getId());
            taskRegistrar.addCronTask(taskCron.getId(), task, taskCron.getCronStr());
        }
        if (taskCron.getStatus() == 0) {
            taskRegistrar.removeCronTask(taskCron.getId());
        }
    }
}
