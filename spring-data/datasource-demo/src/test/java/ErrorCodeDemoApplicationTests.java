import lombok.extern.slf4j.Slf4j;
import org.jarvis.datasource.DataSourceDemoApplication;
import org.jarvis.datasource.exception.CustomizeDuplicatedKeyException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

/**
 * author:tennyson date:2020/7/7
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = DataSourceDemoApplication.class)
@Slf4j
public class ErrorCodeDemoApplicationTests {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 测试根据内存数据库自定义主键冲突异常
     * 期望捕获到一个CustomizeDuplicatedKeyException异常
     * 因此result是没有值的，也不会有日志输出
     */
    @Test(expected = CustomizeDuplicatedKeyException.class)
    public void testThrowingCustomException() {
        jdbcTemplate.execute("INSERT INTO FOO (ID, BAR) VALUES (1, 'a')");
        jdbcTemplate.execute("INSERT INTO FOO (ID, BAR) VALUES (1, 'b')");
        List<Map<String, Object>> result = jdbcTemplate.queryForList("SELECT * FROM FOO");
        result.forEach(map -> log.info("{}", map));
    }
}
