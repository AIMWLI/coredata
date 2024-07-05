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
        namedExecutor.execute(() -> System.out.println(Thread.currentThread().getName()));
        namedExecutor.shutdown();

        System.out.println("active: " + executor.getActiveCount());
        System.out.println("completed: " + executor.getCompletedTaskCount());

        executor.prestartAllCoreThreads();
        System.out.println("pool size after prestart: " + executor.getPoolSize());

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
    }
}
