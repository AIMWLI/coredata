package ThreadPool;

import java.util.concurrent.*;

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
