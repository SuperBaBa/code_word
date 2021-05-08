import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * author:tennyson date:2020/6/19
 **/
public class PropertiesLoad {
    public static void main(String[] args) throws IOException {
        Properties prop = new Properties();
        InputStream is = PropertiesLoad.class.getResourceAsStream("/application.properties");//假设当前这个方法是在CommonUtils类下面
        prop.load(is);
        System.out.println(get("springboot.name"));
    }

    public static String get(String key) {
        Properties prop = new Properties();
        try {
            prop.load(PropertiesLoad.class.getResourceAsStream("/application.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop.getProperty(key);
    }

}
