package ThreadPool;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程池七大核心参数详解：
 * 1. corePoolSize     — 核心线程数（常驻）
 * 2. maximumPoolSize  — 最大线程数（核心+临时）
 * 3. keepAliveTime    — 临时线程空闲存活时间
 * 4. unit             — 存活时间单位
 * 5. workQueue        — 阻塞队列（存储等待任务）
 * 6. threadFactory    — 线程工厂（命名、守护）
 * 7. handler          — 拒绝策略（队列满+线程满）
 */
public class ThreadPoolSevenParamsDemo {

    private static final AtomicInteger threadNum = new AtomicInteger(1);

    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
            2,                // corePoolSize: 常驻 2 个核心线程
            4,                // maximumPoolSize: 最多 4 个线程
            30L,              // keepAliveTime: 临时线程空闲 30s 回收
            TimeUnit.SECONDS, // unit: 秒
            new LinkedBlockingQueue<>(5), // workQueue: 有界队列容量 5
            r -> {            // threadFactory: 自定义命名
                Thread t = new Thread(r, "biz-" + threadNum.getAndIncrement());
                t.setDaemon(false);
                return t;
            },
            new ThreadPoolExecutor.AbortPolicy() // handler: 拒绝抛异常
        );

        System.out.println("=== 七大参数 ===");
        System.out.println("corePoolSize: " + executor.getCorePoolSize());
        System.out.println("maxPoolSize: " + executor.getMaximumPoolSize());
        System.out.println("keepAliveTime: " + executor.getKeepAliveTime(TimeUnit.SECONDS) + "s");
        System.out.println("workQueue: LinkedBlockingQueue capacity=5");
        System.out.println("threadFactory: custom naming biz-1,biz-2...");
        System.out.println("handler: AbortPolicy\n");

        // 提交 7 个任务（core=2, queue=5 → 全部进队列/核心）
        for (int i = 1; i <= 7; i++) {
            int taskId = i;
            executor.execute(() -> {
                System.out.println("task " + taskId + " on " + Thread.currentThread().getName());
                sleep(500);
            });
        }
        System.out.println("poolSize: " + executor.getPoolSize() + " queueSize: " + executor.getQueue().size());

        // 第 8 个任务触发创建临时线程（core=2, queue=5, 活跃线程=2）
        executor.execute(() -> {
            System.out.println("overflow task on " + Thread.currentThread().getName());
            sleep(500);
        });
        System.out.println("after overflow poolSize: " + executor.getPoolSize());

        executor.shutdown();
        try { executor.awaitTermination(3, TimeUnit.SECONDS); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }

        // prestartAllCoreThreads 预热
        ThreadPoolExecutor warm = new ThreadPoolExecutor(
            3, 6, 30L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(10),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.AbortPolicy()
        );
        warm.prestartAllCoreThreads();
        System.out.println("\nprestart core threads: " + warm.getPoolSize());
        warm.shutdown();
    }

    static void sleep(long ms) { try { Thread.sleep(ms); } catch (InterruptedException e) { Thread.currentThread().interrupt(); } }
}
