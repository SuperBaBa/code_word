import org.jarvis.sqltask.DataSourceApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author marcus
 * @date 2020/11/13-16:55
 */
@SpringBootTest(classes = {DataSourceApplication.class})
@RunWith(SpringRunner.class)
public class DataSourceApplicationTests {
    @Test(expected = ExceptionInInitializerError.class)
    public void givenInitializingClass_whenUsingForName_thenInitializationError() throws ClassNotFoundException {
        Class.forName("java.lang.Integer");
    }


}
