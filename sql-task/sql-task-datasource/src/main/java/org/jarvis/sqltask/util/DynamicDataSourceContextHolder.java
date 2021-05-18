package org.jarvis.sqltask.util;

import org.springframework.util.StringUtils;

/**
 * @author marcus
 * @date 2020/11/2-1:36
 */
public final class DynamicDataSourceContextHolder {
    /**
     * 为什么要用链表存储(准确的是栈)
     * <pre>
     * 为了支持嵌套切换，如ABC三个service都是不同的数据源
     * 其中A的某个业务要调B的方法，B的方法需要调用C的方法。一级一级调用切换，形成了链。
     * 传统的只设置当前线程的方式不能满足此业务需求，必须模拟栈，后进先出。
     * </pre>
     */
//    private static final ThreadLocal<Deque<String>> LOOKUP_KEY_HOLDER = new ThreadLocal() {
//        @Override
//        protected Object initialValue() {
//            return new ArrayDeque();
//        }
//    };
    private static final ThreadLocal<String> LOOKUP_KEY_HOLDER = new ThreadLocal();

    /**
     * 设置当前线程数据源
     * <p>
     * 如非必要不要手动调用，调用后确保最终清除
     * </p>
     *
     * @param dataSourceLookupKey 数据源名称
     */
    public static void setDataSourceLookupKey(String dataSourceLookupKey) {
        String ds = StringUtils.isEmpty(dataSourceLookupKey) ? "" : dataSourceLookupKey;
        LOOKUP_KEY_HOLDER.set(ds);
//        LOOKUP_KEY_HOLDER.get().push(ds);
    }

    /**
     * 获得当前线程数据源
     *
     * @return 数据源名称
     */
    public static String getDataSourceLookupKey() {
//        return LOOKUP_KEY_HOLDER.get().peek();
        return LOOKUP_KEY_HOLDER.get();
    }

    /**
     * 清空当前线程数据源
     * <p>
     * 如果当前线程是连续切换数据源
     * 只会移除掉当前线程的数据源名称
     * </p>
     */
//    public static void clearDataSourceLookupKey() {
//        Deque<String> deque = LOOKUP_KEY_HOLDER.get();
//        deque.poll();
//        if (deque.isEmpty()) {
//            LOOKUP_KEY_HOLDER.remove();
//        }
//    }
    private DynamicDataSourceContextHolder() {
    }
}
