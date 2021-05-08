package org.jarvis.consumer.java8.algorithm;

/**
 * @author marcus
 * @date 2020/10/26-17:09
 */
public class StaticTest {
    private static int count = 0;
    private static int counts = 0;

    /**
     * 不会存在并发问题
     *
     * @return
     */
    public static String getTestStr() {
        String xx = Thread.currentThread().toString();
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return xx;
    }

    /**
     * 存不存在并发问题与传入的变量有关
     * 假如thread a和thread b都在操作对象a则存在
     *
     * @param user
     * @return
     */
    public static String getTestUser(org.jarvis.consumer.java8.algorithm.User user) {
        String str = "id: " + user.getId() + "name: " + user.getName();
        try {
            System.out.println(Math.random());
            Thread.sleep(100L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 存在并发问题
     *
     * @return
     */
    public static int getTestCount() {
        count++;
        count++;
        count++;
        count++;
        count++;
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        count++;
        count++;
        count++;
        count++;
        count++;
        return count;
    }

    /**
     * 不存在并发问题
     *
     * @return
     */
    public synchronized static int getTestCountS() {
        counts++;
        counts++;
        counts++;
        counts++;
        counts++;
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        counts++;
        counts++;
        counts++;
        counts++;
        counts++;
        return counts;
    }

    public static void main(String[] args) {
        org.jarvis.consumer.java8.algorithm.User user = new org.jarvis.consumer.java8.algorithm.User();
        for (int i = 0; i < 1000; i++) {
            final int finalI = i;
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    org.jarvis.consumer.java8.algorithm.User userTmp = new org.jarvis.consumer.java8.algorithm.User();
                    user.setId(finalI);
                    user.setName(Thread.currentThread().toString());
                    userTmp.setId(finalI);
                    userTmp.setName(Thread.currentThread().toString());
                    //局部变量不存在问题
//                    System.out.println("getTestStr: " + Thread.currentThread()  + StaticTest.getTestStr());
                    //与user有关
                    System.out.println("getTestUser: " + Thread.currentThread() + StaticTest.getTestUser(user));
//                    System.out.println("getTestUseS: " + Thread.currentThread()  + StaticTest.getTestUser(userTmp));

                    //线程不安全
//                    System.out.println("getTestCount: "  + Thread.currentThread() + StaticTest.getTestCount() % 10);

                    //安全但是慢需要加锁
//                    System.out.println("getTestCountS: "  + Thread.currentThread() + StaticTest.getTestCountS() % 10);
                }
            });
            thread.start();
        }


    }
}
