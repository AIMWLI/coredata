package ThreadPool;

import java.util.concurrent.*;

/**
 * 四种工作队列 + 四种拒绝策略对比
 * 队列：LinkedBlockingQueue, ArrayBlockingQueue, SynchronousQueue, PriorityBlockingQueue
 * 拒绝：AbortPolicy, CallerRunsPolicy, DiscardPolicy, DiscardOldestPolicy
 */
public class ThreadPoolWorkQueueRejectDemo {

    public static void main(String[] args) {
        // 1. LinkedBlockingQueue 有界队列
        ThreadPoolExecutor linked = new ThreadPoolExecutor(
            1, 2, 0L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(3),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.AbortPolicy()
        );
        submitTasks(linked, "LinkedBlockingQueue");
        linked.shutdown();

        // 2. SynchronousQueue 直接传递（不存储任务）
        ThreadPoolExecutor sync = new ThreadPoolExecutor(
            1, 3, 0L, TimeUnit.SECONDS,
            new SynchronousQueue<>(),
            Executors.defaultThreadFactory(),
            (r, e) -> System.out.println("SynchronousQueue rejected")
        );
        submitTasks(sync, "SynchronousQueue");
        sync.shutdown();

        // 3. CallerRunsPolicy 调用者运行
        ThreadPoolExecutor caller = new ThreadPoolExecutor(
            1, 1, 0L, TimeUnit.SECONDS,
            new SynchronousQueue<>(),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.CallerRunsPolicy()
        );
        caller.execute(() -> {
            System.out.println("callerRuns task on " + Thread.currentThread().getName());
            sleep(100);
        });
        caller.execute(() -> {
            System.out.println("callerRuns task2 runs on caller: " + Thread.currentThread().getName());
        });
        caller.shutdown();

        // 4. DiscardPolicy + DiscardOldestPolicy
        ThreadPoolExecutor discard = new ThreadPoolExecutor(
            1, 1, 0L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(2),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.DiscardPolicy()
        );
        discard.execute(() -> { sleep(100); System.out.println("discard task1"); });
        discard.execute(() -> System.out.println("discard task2 (may be discarded)"));
        discard.execute(() -> System.out.println("discard task3 (may be discarded)"));
        discard.shutdown();

        try { discard.awaitTermination(1, TimeUnit.SECONDS); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        System.out.println("DiscardPolicy done");
    }

    static void submitTasks(ThreadPoolExecutor e, String name) {
        System.out.println("--- " + name + " ---");
        for (int i = 1; i <= 4; i++) {
            int id = i;
            try {
                e.execute(() -> {
                    System.out.println(name + " task" + id + " on " + Thread.currentThread().getName());
                    sleep(50);
                });
            } catch (Exception ex) {
                System.out.println(name + " task" + id + " " + ex.getClass().getSimpleName());
            }
        }
    }

    static void sleep(long ms) { try { Thread.sleep(ms); } catch (InterruptedException e) { Thread.currentThread().interrupt(); } }
}
