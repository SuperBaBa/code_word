package org.jarvis.task;

import java.util.concurrent.ScheduledFuture;

/**
 * TaskScheduler返回结果封装，用于存储在内存，对任务进行取消
 *
 * @author marcus
 * @date 2020/11/12-23:01
 */
public class ScheduledFutureTask {

    volatile ScheduledFuture<?> scheduledFuture;

    /**
     * 由异步Future知道，cancel可能存在中断风险,暂时忽略后续探究
     */
    public void cancel() {
        ScheduledFuture<?> future = this.scheduledFuture;
        if (future != null) {
            future.cancel(Boolean.TRUE);
        }
    }
}
