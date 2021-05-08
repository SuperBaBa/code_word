package org.jarvis.consumer.task.config;

import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @author marcus
 * @date 2020/10/18-8:57
 */
@Component
public class ScheduleConfiguration implements SchedulingConfigurer {
    private String corn = "0/2 * * * * ?";

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        int i = 0;
        taskRegistrar.addTriggerTask(new Runnable() {
                                         @Override
                                         public void run() {
                                             // 任务逻辑
                                             System.out.println("第" + (i) + "次开始执行操作... " + "时间：【" + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS").format(new Date()) + "】");
                                             if (i > 3) {
                                                 corn = "0/8 * * * * ?";

                                             }
                                         }


                                     },
                triggerContext -> {
                    //任务触发，可修改任务的执行周期
                    CronTrigger trigger = new CronTrigger("0/2 * * * * ?");
                    Date nextExec = trigger.nextExecutionTime(triggerContext);
                    return nextExec;
                });
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        /*先进行UTF-8编码，然后再将每个字节以%XX表示
         * 中文一个字符三个字节,因此下面是8个%XX,感叹号是半角符号
         * 中的URL编码是%E4%B8%AD，文的URL编码是%E6%96%87，!虽然是ASCII字符，也要对其编码为%21。
         * URLEncoder把空格字符编码成+，而现在的URL编码标准要求空格被编码为%20
         */
        String encoded = URLEncoder.encode("中文!", String.valueOf(StandardCharsets.UTF_8));
        System.out.println(encoded);
        String decoded = URLDecoder.decode(encoded, String.valueOf(StandardCharsets.UTF_8));
        System.out.println(decoded);
    }

}
