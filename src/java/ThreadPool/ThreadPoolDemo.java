package ThreadPool;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPoolDemo {

    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
            2,
            4,
            60L,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(10),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.AbortPolicy()
        );

        for (int i = 0; i < 10; i++) {
            int taskId = i;
            executor.execute(() -> {
                System.out.println(Thread.currentThread().getName() + " task " + taskId);
            });
        }

        executor.shutdown();

        ThreadPoolExecutor executor2 = new ThreadPoolExecutor(
            2, 4, 60L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(10),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.AbortPolicy()
        );

        AtomicInteger threadNum = new AtomicInteger(1);
        ThreadPoolExecutor namedPool = new ThreadPoolExecutor(
            2, 4, 30L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(10),
            r -> new Thread(r, "worker-" + threadNum.getAndIncrement()),
            new ThreadPoolExecutor.AbortPolicy()
        );
        namedPool.execute(() -> System.out.println(Thread.currentThread().getName()));
        namedPool.shutdown();

        ThreadPoolExecutor singlePool = new ThreadPoolExecutor(
            1, 1, 0L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.AbortPolicy()
        );
        singlePool.execute(() -> System.out.println("single pool task"));
        singlePool.shutdown();

        ThreadPoolExecutor cachedPool = new ThreadPoolExecutor(
            0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS,
            new SynchronousQueue<>(),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.AbortPolicy()
        );
        cachedPool.execute(() -> System.out.println("cached pool task"));
        cachedPool.shutdown();

        ThreadPoolExecutor syncQueue = new ThreadPoolExecutor(
            1, 2, 0L, TimeUnit.SECONDS,
            new SynchronousQueue<>(),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.AbortPolicy()
        );
        syncQueue.execute(() -> System.out.println("sync queue task"));
        syncQueue.shutdown();

        ThreadPoolExecutor namedExecutor = new ThreadPoolExecutor(
            2, 4, 30L, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(5),
            r -> {
                Thread t = new Thread(r);
                t.setName("custom-" + t.getId());
                t.setDaemon(true);
                return t;
            },
            new ThreadPoolExecutor.AbortPolicy()
        );
        System.out.println("named exec pool size: " + namedExecutor.getPoolSize());
        namedExecutor.execute(() -> System.out.println(Thread.currentThread().getName()));
        namedExecutor.shutdown();

        ThreadPoolExecutor withStats = new ThreadPoolExecutor(
            2, 4, 60L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(20),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.AbortPolicy()
        );
        withStats.execute(() -> System.out.println(Thread.currentThread().getName()));
        withStats.shutdown();

        System.out.println("active: " + executor.getActiveCount());
        System.out.println("completed: " + executor.getCompletedTaskCount());

        executor.prestartAllCoreThreads();
        System.out.println("pool size after prestart: " + executor.getPoolSize());

        System.out.println("largest pool size: " + executor.getLargestPoolSize());

        System.out.println("task count: " + executor.getTaskCount());

        Callable<String> callableTask = () -> Thread.currentThread().getName();
        Future<String> future = executor2.submit(callableTask);
        try {
            System.out.println("future: " + future.get(1, TimeUnit.SECONDS));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        executor2.shutdown();

        ThreadPoolExecutor abortPolicy = new ThreadPoolExecutor(
            1, 2, 0L, TimeUnit.SECONDS,
            new SynchronousQueue<>(),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.AbortPolicy()
        );
        abortPolicy.shutdown();

        ThreadPoolExecutor discardPolicy = new ThreadPoolExecutor(
            1, 2, 0L, TimeUnit.SECONDS,
            new SynchronousQueue<>(),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.DiscardPolicy()
        );
        discardPolicy.shutdown();

        ThreadPoolExecutor callerRuns = new ThreadPoolExecutor(
            1, 2, 0L, TimeUnit.SECONDS,
            new SynchronousQueue<>(),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.CallerRunsPolicy()
        );
        callerRuns.shutdown();

        ThreadPoolExecutor maxCore = new ThreadPoolExecutor(
            5, 10, 60L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(50),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.AbortPolicy()
        );
        maxCore.prestartAllCoreThreads();
        System.out.println("maxCore pool: " + maxCore.getPoolSize());
        maxCore.shutdown();

        System.out.println("corePoolSize: " + executor.getCorePoolSize());
        System.out.println("maxPoolSize: " + executor.getMaximumPoolSize());
        System.out.println("keepAliveTime: " + executor.getKeepAliveTime(TimeUnit.SECONDS));

        ThreadPoolExecutor rejectCounter = new ThreadPoolExecutor(
            1, 1, 0L, TimeUnit.SECONDS,
            new SynchronousQueue<>(),
            Executors.defaultThreadFactory(),
            (r, e) -> System.out.println("rejected: " + r.toString())
        );
        rejectCounter.execute(() -> System.out.println("rc task"));
        rejectCounter.shutdown();

        System.out.println("queue remaining: " + executor.getQueue().remainingCapacity());

        ThreadPoolExecutor watchPool = new ThreadPoolExecutor(
            2, 4, 60L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(5),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.AbortPolicy()
        );
        watchPool.execute(() -> System.out.println("watch pool task"));
        System.out.println("watch active: " + watchPool.getActiveCount());
        System.out.println("watch pool: " + watchPool.getPoolSize());
        watchPool.shutdown();

        ThreadPoolExecutor stealPool = (ThreadPoolExecutor) Executors.newWorkStealingPool(2);
        stealPool.execute(() -> System.out.println("steal pool task"));
        System.out.println("steal pool parallelism: " + stealPool.getPoolSize());
        stealPool.shutdown();

        ThreadPoolExecutor dynamicPool = new ThreadPoolExecutor(1, 4, 30L, TimeUnit.SECONDS,
            new SynchronousQueue<>(), Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.CallerRunsPolicy());
        dynamicPool.setCorePoolSize(2);
        dynamicPool.setMaximumPoolSize(6);
        dynamicPool.execute(() -> System.out.println("dynamic pool task"));
        System.out.println("dynamic core: " + dynamicPool.getCorePoolSize());
        dynamicPool.shutdown();
    }
}

// worker
public static void work() {}
