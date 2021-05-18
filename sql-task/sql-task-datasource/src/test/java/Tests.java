/**
 * @author marcus
 * @date 2020/11/14-19:27
 */
public class Tests {
    public static void main(String[] args) {
        try {
            Class.forName("java.lang.eo");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
