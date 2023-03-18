package vip.paolu.wm.common;

/**
 * Description: 基于ThreadLocal封装工具类，用户保存和获取当前登录用户id
 * User: Pan
 * Date: 2023-03-12-21:27
 */
public class BaseContext {
    public static ThreadLocal<Long> threadLocal = new ThreadLocal<Long>();

    /**
     * 设置共享变量
     *
     * @param id
     */
    public static void setCurrentId(Long id) {
        threadLocal.set(id);
    }

    /**
     * 返回共享变量
     *
     * @return
     */
    public static Long getCurrentId() {
        return threadLocal.get();
    }

}
