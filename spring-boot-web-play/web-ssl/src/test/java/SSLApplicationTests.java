import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * author:marcus date:2020/8/7
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class SSLApplicationTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TestRestTemplate testRestTemplate;

    public void controllerTest() throws Exception {
        mockMvc.perform(get("/mat-test"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Mock测试2"));
    }

    public static void main(String[] args) {
        int a = 10;
        int b = 99;
        a = a ^ b;
        b = a ^ b;
        a = a ^ b;
        System.out.println(a + "\t" + b);
    }
}
