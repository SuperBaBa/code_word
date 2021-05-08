import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * author:tennyson date:2020/6/19
 **/
public class LoadPropertiesUtil {
    private static final Logger logger = LoggerFactory.getLogger(LoggerFactory.class);


    /**
     * 加载文件
     *
     * @param fileName 为项目根路径下路径
     * @return
     */
    public static Properties loadProperties(String fileName) {
        Properties properties = new Properties();
        FileReader is = null;
        try {
            String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
            is = new FileReader("E:\\workspace\\marcus-spring-practice\\elasticjob_play\\src\\main\\resources\\application.properties");
            properties.load(is);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return properties;
    }

    public static String getKey(String fileName, String key) {
        String result = null;
        try {
            Properties properties = loadProperties(fileName);
            result = (String) properties.get(key);
        } catch (Exception e) {
            logger.info("加载配置文件" + fileName + "失败");
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    public static void main(String[] args) {
        loadProperties("applicaiotn.properties");

    }
}
