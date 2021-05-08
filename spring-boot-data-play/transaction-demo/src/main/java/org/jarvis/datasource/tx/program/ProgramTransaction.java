package org.jarvis.consumer.tx.program;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * author:tennyson  date:2020/7/5
 */
@Service
public class ProgramTransaction {
    private Logger log = LoggerFactory.getLogger(ProgramTransaction.class);

    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    private JdbcTemplate jdbcTemplate;


    public void transaction() {
        //先查询一番表中是否有数据,事务发生之前表中数据
        log.info("先查询一番表中是否有数据,事务发生之前表中数据\nCOUNT BEFORE TRANSACTION: {}", getCount());
        //这个应该是DataSourceTransactionManagerAutoConfiguration配置的 transactionTemplate
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                //执行事务
                jdbcTemplate.execute("INSERT INTO FOO (ID, BAR) VALUES (1, 'aaa')");
                //执行事务期间查询数据库中数据
                log.info("执行事务期间查询数据库中数据\nCOUNT IN TRANSACTION: {}", getCount());
                //回滚事务
                transactionStatus.setRollbackOnly();
            }
        });
        //回滚后查询数据库
        log.info("回滚后查询数据库\nCOUNT AFTER TRANSACTION: {}", getCount());
    }

    private long getCount() {
        return (long) jdbcTemplate.queryForList("SELECT COUNT(*) AS CNT FROM FOO").get(0).get("CNT");
    }
}
