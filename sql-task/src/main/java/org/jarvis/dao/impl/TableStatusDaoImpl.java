package org.jarvis.dao.impl;

import org.jarvis.dao.TableStatusDao;
import org.jarvis.entity.TableStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author marcus
 * @date 2020/11/4-13:03
 */
@Repository
public class TableStatusDaoImpl implements TableStatusDao {
    @Autowired
    @Qualifier(value = "firstJdbcTemplate")
    private JdbcTemplate firstJdbcTemplate;

    @Override
    public int save(TableStatus tableStatus) {
        return firstJdbcTemplate.update("INSERT INTO db_status (table_name,cost_time,table_count,create_time,modify_time) VALUES (?,?,?,?,?)",
                tableStatus.getTableCount(), tableStatus.getCostTimes(), tableStatus.getTableCount(), new Date(), new Date());

    }

    @Override
    public List<TableStatus> queryListByTime(Date startTime, Date endTime) {
        return firstJdbcTemplate.query("SELECT * FROM `db_status` WHERE `create_time` > ?", (rs, rowNum) -> TableStatus.builder()
                .id(rs.getInt("id"))
                .tableCount(rs.getInt("table_count"))
                .tableName(rs.getString("table_name"))
                .costTimes(rs.getLong("cost_time"))
                .createTime(rs.getTimestamp("create_time"))
                .modifyTime(rs.getTimestamp("modify_time"))
                .build(), startTime);
    }

    @Override
    public List<TableStatus> queryAll() {
        return firstJdbcTemplate.query("SELECT * FROM `db_status`", (rs, rowNum) -> TableStatus.builder()
                .id(rs.getInt("id"))
                .tableCount(rs.getInt("table_count"))
                .tableName(rs.getString("table_name"))
                .costTimes(rs.getLong("cost_time"))
                .createTime(rs.getTimestamp("create_time"))
                .modifyTime(rs.getTimestamp("modify_time"))
                .build());
    }
}
