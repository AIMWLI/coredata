package Concurrency;

import java.util.concurrent.*;

/**
 * ThreadLocal 生产规范：
 * 1. 绝对禁止线程池场景下使用 ThreadLocal 不 remove() → Entry 无法回收 → 内存泄漏
 * 2. 必须在 finally 块显式 remove()
 * 3. 跨方法上下文传递用方法参数显式传入
 * 4. 线程池透传场景用 TransmittableThreadLocal（TTL）
 */
public class ThreadLocalLeakDemo {

    private static final ExecutorService pool = Executors.newFixedThreadPool(2);
    private static final ThreadLocal<String> userContext = new ThreadLocal<>();

    public static void main(String[] args) {
        for (int i = 0; i < 6; i++) {
            int userId = i;
            pool.execute(() -> processUser(userId));
        }
        pool.shutdown();
        try { pool.awaitTermination(1, TimeUnit.SECONDS); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        System.out.println("thread local demo done");
    }

    static void processUser(int userId) {
        try {
            userContext.set("user-" + userId);
            String ctx = userContext.get();
            System.out.println(Thread.currentThread().getName() + " ctx=" + ctx + " userId=" + userId);
            doBusiness(ctx);
        } finally {
            userContext.remove();
        }
    }

    static void doBusiness(String ctx) {
        System.out.println("  business: " + ctx);
    }
}
