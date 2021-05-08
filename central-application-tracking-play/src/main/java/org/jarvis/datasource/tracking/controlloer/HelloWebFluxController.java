package org.jarvis.consumer.tracking.controlloer;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author marcus
 * @date 2020/9/24-13:16
 */
@RestController
public class HelloWebFluxController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @RequestMapping("/hello")
    public String hello() {
        Transaction t = Cat.newTransaction("URL", "pageName");
        List resultMap = jdbcTemplate.queryForList("SELECT * FROM `STUDENTS`");
        t.setStatus(Transaction.SUCCESS);
        t.complete();
        return resultMap.get(0).toString();
    }

//    @GetMapping("/user")
//    public Mono<User> getUser() {
//        User user = new User();
//        user.setName("犬小哈");
//        user.setDesc("欢迎关注我的公众号: 小哈学Java");
//        return Mono.just(user);
//    }
}
