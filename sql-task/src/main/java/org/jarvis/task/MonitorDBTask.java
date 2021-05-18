package org.jarvis.task;

import lombok.extern.slf4j.Slf4j;
import org.jarvis.dao.TableStatusDao;
import org.jarvis.entity.TableStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * @author marcus
 * @date 2020/11/3-13:46
 */
@Service
@Slf4j
public class MonitorDBTask implements Runnable {
    @Autowired
    @Qualifier(value = "secondJdbcTemplate")
    private JdbcTemplate secondJdbcTemplate;
    @Autowired
    private TableStatusDao tableStatusDao;
    private int thresholdValue;
    //    AtomicInteger atomicInteger = new AtomicInteger();
//    io.micrometer.core.instrument.Gauge gauge = io.micrometer.core.instrument.Gauge.builder("gauge", atomicInteger, AtomicInteger::get)
//            .tag("gauge", "gauge")
//            .description("gauge")
//            .register(new SimpleMeterRegistry());
    //    private final static Gauge gauge=Gauge.("delay",Integer.valueOf(0),null).register(new SimpleMeterRegistry());
    static final io.prometheus.client.Gauge inprogressRequests = io.prometheus.client.Gauge.build()
            .name("inprogress_requests").labelNames("dps").help("dps监控").register();

    @Override
    public void run() {
        Long startTime = System.currentTimeMillis();
        Integer countFromDB = secondJdbcTemplate.queryForObject("SELECT COUNT(*) FROM `data_direct_push`", Integer.class);
        Long endTime = System.currentTimeMillis();

        log.info("查询出数据库存在{}条数据,花费{}ms", countFromDB, endTime - startTime);
        TableStatus tableStatus = TableStatus.builder()
                .tableName("data_direct_push")
                .costTimes(endTime - startTime)
                .tableCount(countFromDB).build();
        tableStatusDao.save(tableStatus);
        assert countFromDB != null;
        inprogressRequests.labels("delay").set(countFromDB.doubleValue());
        inprogressRequests.labels("time").setToCurrentTime(); // Set to current unixtime.
    }
}
