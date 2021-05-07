package org.jarvis.datasource.dao;

import lombok.extern.slf4j.Slf4j;
import org.jarvis.datasource.entity.Fist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * 查询和批量操作
 * author:tennyson date:2020/7/4
 **/
@Repository
@Slf4j
public class FirstDao {
    @Autowired
    @Qualifier("firstJdbcTemplate")
    private JdbcTemplate jdbcTemplate;
    @Autowired
    @Qualifier("secondJdbcTemplate")
    private JdbcTemplate secondJdbcTemplate;
    @Autowired
    private SimpleJdbcInsert simpleJdbcInsert;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * 批量执行sql语句
     */
    public void batchInsert() {
        //使用jdbcTemplate批量新增
        jdbcTemplate.batchUpdate("INSERT INTO `FIRST` (`USERNAME`) VALUES (?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setString(1, "b-" + i);
                    }

                    @Override
                    public int getBatchSize() {
                        return 2;
                    }
                });

        //使用namedParameterJdbcTemplate进行批量新增
        List<Fist> list = new ArrayList<>();
        list.add(Fist.builder().id(100).username("b-100").build());
        list.add(Fist.builder().id(101).username("b-101").build());
        namedParameterJdbcTemplate
                .batchUpdate("INSERT INTO `FIRST` (ID, `USERNAME`) VALUES (:id, :username)",
                        SqlParameterSourceUtils.createBatch(list));
    }

    public void insertData() {
        //新增多条记录
        Arrays.asList("tom", "siri").forEach(username -> {
            jdbcTemplate.update("INSERT INTO `FIRST` (`USERNAME`) VALUES (?)", username);
        });
        //新增一行记录，username=jerry
        HashMap<String, String> row = new HashMap<>();
        row.put("username", "jerry");
        Number id = simpleJdbcInsert.executeAndReturnKey(row);
        log.info("ID of jerry: {}", id.longValue());
    }

    public void listData() {
        //查询记录总数
        log.info("Count: {}",jdbcTemplate.queryForObject("SELECT COUNT(*) FROM `FIRST` ", Long.class));
        //查询所有数据的username，
        // 并遍历
        List<String> list = jdbcTemplate.queryForList("SELECT USERNAME FROM `FIRST`", String.class);
        list.forEach(s -> log.info("USERNAME: {}", s));
        //查询并返回结果集
        List<Fist> fooList = jdbcTemplate.query("SELECT * FROM `FIRST` ", (rs, rowNum) -> Fist.builder()
                .id(rs.getInt(1))
                .username(rs.getString(2))
                .build());
        fooList.forEach(f -> log.info("Fist: {}", f));
    }
}
