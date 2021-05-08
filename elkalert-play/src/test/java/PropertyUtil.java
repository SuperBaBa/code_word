import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * author:tennyson date:2020/6/19
 **/
public class PropertyUtil {
    private PropertyUtil(){};

    private static class SingletonHolder{
        private static Properties props  = loadProps();
    }
    public static Properties getProps(){
        return SingletonHolder.props;
    }
    private static  Properties loadProps(){
        Properties props = new Properties();
        FileReader in = null;
        try {
//            in = PropertyUtil.class.getClassLoader().getResourceAsStream("config.properties");
            in = new FileReader("E:\\workspace\\marcus-spring-practice\\elasticjob_play\\src\\main\\resources\\application.properties");
            props.load(in);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(null != in) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return props;
    }

    public static String getProperty(String key){
        return getProps().getProperty(key);
    }

    public static String getProperty(String key, String defaultValue) {
        return getProps().getProperty(key, defaultValue);
    }
    public static void init(){//不要随便调用(不是私有的)
        SingletonHolder.props =  loadProps();
    }
}
