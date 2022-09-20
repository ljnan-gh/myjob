package myjob.core.db.mysql;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class DynamicDataSourceContextHolder {
    private static final ThreadLocal<String> LOOKUP_KEY_HOLDER = new ThreadLocal<>();
//    /**
//     * 获取当前线程数据源
//     */
//    public static String peek() {
//        return LOOKUP_KEY_HOLDER.get().peek();
//    }
//    /**
//     * 设置当前线程数据源
//     */
//    public static void push(String ds) {
//        LOOKUP_KEY_HOLDER.get().push(StringUtils.isEmpty(ds)?"":ds);
//    }
//    /**
//     * 清除当前线程数据源
//     */
//    public static void poll() {
//        Deque<String>deque = LOOKUP_KEY_HOLDER.get();
//        deque.poll();
//        if(deque.isEmpty())
//            LOOKUP_KEY_HOLDER.remove();
//    }
//
//    /**
//     * 强制清空本地线程
//     */
//    public static void clear() {
//        LOOKUP_KEY_HOLDER.remove();
//    }


    /*********************************************************/
    /**
     * 切换数据源
     *
     * @param datasourceId
     */
    public static void setDataSource(String datasourceId) {
        LOOKUP_KEY_HOLDER.set(datasourceId);
        log.info("已切换数据源：{}", datasourceId);
    }

    /**
     * 获取数据源
     */
    public static String getDataSource() {
        String datasourceId = LOOKUP_KEY_HOLDER.get();
        log.info("当前数据源：{}", datasourceId);
        return datasourceId;
    }

    /***
     * 删除数据源
     */
    public static void removeDataSource() {
        LOOKUP_KEY_HOLDER.remove();
        log.info("已切换到主数据源");
    }

    public static int getNum() {
        return LOOKUP_KEY_HOLDER.get() == null ? 0 : LOOKUP_KEY_HOLDER.get().length();
    }
}
