package org.jarvis.controller;

import lombok.extern.slf4j.Slf4j;
import org.jarvis.dao.TableStatusDao;
import org.jarvis.dto.ResponseResult;
import org.jarvis.entity.TableStatus;
import org.jarvis.vo.LineChartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author marcus
 * @date 2020/11/4-12:49
 */
@RestController
@RequestMapping(value = "graph")
@Slf4j
public class GraphController {
    @Autowired
    private TableStatusDao tableStatusDao;

    @GetMapping(value = "fetchAllData")
    public Object fetchAllData() {
        List<TableStatus> tableStatusList = tableStatusDao.queryAll();
        List<LineChartVO> lineChartVOList = tableStatusList.stream().map(tableStatus -> {
//            String formatedTime = LocalDateTime.ofInstant(tableStatus.getCreateTime().toInstant(), ZoneId.systemDefault()).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            return LineChartVO.builder()
                    .createTime(Long.toString(tableStatus.getCreateTime().getTime()))
                    .tableCount(tableStatus.getTableCount()).build();
        }).collect(Collectors.toList());
//        return tableStatusList.stream().map(tableStatus -> LineChartVO.builder()
//                .tableCount(tableStatus.getTableCount())
//                .createTime(LocalDateTime
//                        .ofInstant(tableStatus.getCreateTime()
//                                .toInstant(), ZoneId.systemDefault())
//                        .format(DateTimeFormatter.ofPattern("HH:mm:ss"))).build()
//        ).collect(Collectors.toList());
//        tableStatusDao.queryAll().stream().map(tableStatus -> BeanUtils.copyProperties(tableStatus,));
        ResponseResult.builder().data(tableStatusList).build();

        return lineChartVOList;
    }

    @GetMapping(value = "getNewestDataByStartTime")
    public Object getNewestDataByStartTime(String timestamp) {
        log.info("传入时间戳:{}", timestamp);
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime startLocalDateTime = Instant.ofEpochMilli(Long.parseLong(timestamp)).atZone(zoneId).toLocalDateTime().minus(5L, ChronoUnit.SECONDS);
        Date startTime = Date.from(startLocalDateTime.atZone(zoneId).toInstant());
        List<TableStatus> tableStatusList = tableStatusDao.queryListByTime(startTime, new Date());
        List<LineChartVO> lineChartVOList = tableStatusList.stream().map(tableStatus -> {
//            String formatedTime = LocalDateTime.ofInstant(tableStatus.getCreateTime().toInstant(), ZoneId.systemDefault()).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            return LineChartVO.builder()
                    .createTime(Long.toString(tableStatus.getCreateTime().getTime()))
                    .tableCount(tableStatus.getTableCount()).build();
        }).collect(Collectors.toList());
        return lineChartVOList;

    }
}
