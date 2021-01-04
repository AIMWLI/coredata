package ThreadPool;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPoolBuilderDemo {

    private static final AtomicInteger poolNum = new AtomicInteger(1);

    public static ThreadPoolExecutor newFixedPool(int core, int max, int queueSize) {
        return new ThreadPoolExecutor(
            core, max, 60L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(queueSize),
            r -> {
                Thread t = new Thread(r, "pool-" + poolNum.getAndIncrement());
                t.setDaemon(true);
                return t;
            },
            new ThreadPoolExecutor.AbortPolicy()
        );
    }

    public static void main(String[] args) {
        ThreadPoolExecutor pool = newFixedPool(2, 4, 100);
        for (int i = 0; i < 5; i++) {
            int id = i;
            pool.execute(() -> {
                System.out.println(Thread.currentThread().getName() + " task " + id);
            });
        }
        System.out.println("active: " + pool.getActiveCount());
        System.out.println("core: " + pool.getCorePoolSize());
        pool.shutdown();
    }
}
