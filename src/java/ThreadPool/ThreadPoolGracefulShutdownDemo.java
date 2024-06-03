package ThreadPool;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程池优雅关闭：
 * 1. shutdown() 停止接收新任务，执行完已提交
 * 2. awaitTermination() 等待完成
 * 3. shutdownNow() 强制关闭（返回未执行列表）
 */
public class ThreadPoolGracefulShutdownDemo {

    private static final AtomicInteger counter = new AtomicInteger(0);

    public static void main(String[] args) {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(
            2, 4, 30L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(10),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.AbortPolicy()
        );

        for (int i = 0; i < 10; i++) {
            pool.execute(() -> {
                try { Thread.sleep(100); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                counter.incrementAndGet();
            });
        }

        pool.shutdown();
        try {
            if (!pool.awaitTermination(2, TimeUnit.SECONDS)) {
                pool.shutdownNow();
            }
        } catch (InterruptedException e) {
            pool.shutdownNow();
            Thread.currentThread().interrupt();
        }

        System.out.println("completed tasks: " + pool.getCompletedTaskCount());
        System.out.println("counter: " + counter.get());

        // shutdownNow 返回未执行任务
        ThreadPoolExecutor quick = new ThreadPoolExecutor(
            1, 1, 0L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(5),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.AbortPolicy()
        );
        quick.execute(() -> { try { Thread.sleep(500); } catch (InterruptedException e) { } });
        for (int i = 0; i < 3; i++) {
            int id = i;
            quick.execute(() -> System.out.println("queued task " + id));
        }

        try { Thread.sleep(50); } catch (InterruptedException e) { }
        java.util.List<Runnable> remaining = quick.shutdownNow();
        System.out.println("shutdownNow remaining: " + remaining.size());
    }
}
