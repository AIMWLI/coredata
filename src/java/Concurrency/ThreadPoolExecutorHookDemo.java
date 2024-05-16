package Concurrency;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * ThreadPoolExecutor 钩子方法：beforeExecute / afterExecute
 * 用于：监控、日志、上下文传递
 */
public class ThreadPoolExecutorHookDemo {

    private static final AtomicLong taskTime = new AtomicLong(0);

    public static void main(String[] args) {
        ThreadPoolExecutor hookPool = new ThreadPoolExecutor(
            2, 4, 30L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(10),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.AbortPolicy()
        ) {
            @Override
            protected void beforeExecute(Thread t, Runnable r) {
                taskTime.set(System.nanoTime());
                System.out.println("before: " + t.getName());
            }

            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                long elapsed = System.nanoTime() - taskTime.get();
                System.out.println("after: " + elapsed / 1_000 + "us" + (t != null ? " error=" + t.getMessage() : ""));
            }
        };

        hookPool.execute(() -> {
            System.out.println("  hook task running");
            try { Thread.sleep(50); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        });
        hookPool.shutdown();
        try { hookPool.awaitTermination(1, TimeUnit.SECONDS); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }

        System.out.println("hookPool completed: " + hookPool.getCompletedTaskCount());
    }
}
