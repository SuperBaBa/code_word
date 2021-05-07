import com.fasterxml.jackson.databind.ObjectMapper;
import org.jarvis.consumer.concurrent.ConcurrentApplication;
import org.jarvis.consumer.concurrent.spring.dto.Course;
import org.jarvis.consumer.concurrent.spring.service.TaskService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


/**
 * author:marcus date:2020/7/28
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConcurrentApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class TaskServiceExecutorTests {
    private Logger logger = LoggerFactory.getLogger(TaskServiceExecutorTests.class);
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private TaskService taskService;
    @Autowired
    @Qualifier("springExecutor")
    private Executor executor;
    @Autowired
    private MockMvc mockMvc;


    @Test
    public void testController() {
        logger.info("this server port is {}", port);
        assertThat(restTemplate.getForObject("http://localhost:" + port + "/hello", String.class), containsString("hello"));

    }

    @Test(/*expected = Exception.class*/)
    public void asyncTest() {
        try {
            for (int i = 0; i < 10; i++) {
                taskService.asyncPrintString();
            }
        }catch (Exception e){
            logger.error("this is runtimeException",e);
        }

    }

    @Test
    public void executeAsync() throws ExecutionException, InterruptedException {
        for (int i = 0; i < 10; i++) {
            System.out.println("jiuhenyihuo => " + i);
            System.out.println(taskService.doAsync(i).get());
        }

    }

    @Test
    public void restfulControllerTest() throws Exception {
        String json = "{\"name\":\"Mock测试\",\"classHour\":\"4课时\",\"id\":\"rn:practice:Course:5af27fa5d34f435e581e5bbf\"}";
        //将json格式字符串转换成Course对象里的属性值
        ObjectMapper mapper = new ObjectMapper();
        Course course = mapper.readValue(json, Course.class);
        //perform,执行一个RequestBuilders请求，会自动执行SpringMVC的流程并映射到相应的控制器执行处理
        mockMvc.perform(
                //构造一个post请求
                MockMvcRequestBuilders.post("/course")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        //使用writeValueAsString()方法来获取对象的JSON字符串表示
                        .content(mapper.writeValueAsString(course)))
                //andExpect，添加ResultMathcers验证规则，验证控制器执行完成后结果是否正确，【这是一个断言】
                .andExpect(MockMvcResultMatchers.status().is(200))

                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))

                //假定返回的结果中，"name" 值为 "Mock测试2",如果不是的话，会抛出异常java.lang.AssertionError，并给出期望值和实际值
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Mock测试2"))

                //添加ResultHandler结果处理器，比如调试时 打印结果(print方法)到控制台
                .andDo(print())

                //返回相应的MvcResult
                .andReturn();
    }

}
