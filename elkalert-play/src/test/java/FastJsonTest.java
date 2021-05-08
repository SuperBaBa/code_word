import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.sql.Timestamp;

/**
 * author:tennyson date:2020/6/19
 **/
public class FastJsonTest {
    public static void main(String[] args) {
        try {
            String time = "1970-01-01 00:00:00.000000000.000000";
            com.alibaba.fastjson.JSONObject jsonObject = new com.alibaba.fastjson.JSONObject();
            jsonObject.put("time", time);

            Timestamp timestamp = jsonObject.getTimestamp("time");
            System.out.println("time:" + timestamp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加配置文件信息
     *
     * @param filePath
     * @param content
     */
    public static void addMessageFile(String filePath, String content) {
        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(filePath, "rw");
            raf.seek(raf.length());
            raf.write(content.getBytes());
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                raf.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }
}
