package Concurrency;

import java.util.concurrent.*;

/**
 * Semaphore 信号量限流
 * 适用：控制并发访问量，如数据库连接池、外部 API 限流
 * 特点：不支持超时时可中断 acquire，支持公平/非公平
 */
public class SemaphoreRateLimiterDemo {

    private static final Semaphore semaphore = new Semaphore(3, true);

    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(6);

        for (int i = 0; i < 10; i++) {
            int taskId = i;
            pool.execute(() -> {
                try {
                    semaphore.acquire();
                    try {
                        System.out.println(Thread.currentThread().getName() + " task" + taskId + " acquired, permits=" + semaphore.availablePermits());
                        Thread.sleep(100);
                    } finally {
                        semaphore.release();
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        pool.shutdown();
        try { pool.awaitTermination(3, TimeUnit.SECONDS); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        System.out.println("availablePermits: " + semaphore.availablePermits());

        // tryAcquire 非阻塞尝试
        Semaphore quick = new Semaphore(1);
        if (quick.tryAcquire()) {
            System.out.println("quick acquire ok");
            quick.release();
        }
        System.out.println("quick permits: " + quick.availablePermits());
    }
}

// end
public static void end() {}
