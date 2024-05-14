package Concurrency;

import java.util.concurrent.*;

public class ThreadPoolConfigDemo {

    public static void main(String[] args) {
        ThreadPoolExecutor ioPool = new ThreadPoolExecutor(
            8, 16, 120L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(500),
            r -> {
                Thread t = new Thread(r, "io-" + r.hashCode());
                t.setDaemon(true);
                return t;
            },
            (r, e) -> {
                System.out.println("io pool rejected: " + r.toString());
            }
        );

        for (int i = 0; i < 3; i++) {
            int taskId = i;
            ioPool.execute(() -> {
                System.out.println("io task " + taskId + " on " + Thread.currentThread().getName());
            });
        }

        System.out.println("io pool active: " + ioPool.getActiveCount());
        ioPool.shutdown();

        ScheduledExecutorService schedulePool = Executors.newScheduledThreadPool(2);
        schedulePool.schedule(() -> System.out.println("scheduled task"), 100, TimeUnit.MILLISECONDS);
        schedulePool.shutdown();

        System.out.println("io pool core: " + ioPool.getCorePoolSize());
        System.out.println("io pool max: " + ioPool.getMaximumPoolSize());
        System.out.println("keepAlive: " + ioPool.getKeepAliveTime(TimeUnit.SECONDS) + "s");
        System.out.println("io largest: " + ioPool.getLargestPoolSize());

        ThreadPoolExecutor scheduledWrap = new ThreadPoolExecutor(
            2, 4, 30L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(20),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.AbortPolicy()
        );
        scheduledWrap.execute(() -> System.out.println("scheduledWrap task"));
        System.out.println("scheduledWrap active: " + scheduledWrap.getActiveCount());
        scheduledWrap.shutdown();
    }
}
