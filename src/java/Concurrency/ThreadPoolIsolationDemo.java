package Concurrency;

import java.util.concurrent.*;

public class ThreadPoolIsolationDemo {

    private static final ExecutorService workerExecutor = new ThreadPoolExecutor(
        4, 8, 60L, TimeUnit.SECONDS,
        new LinkedBlockingQueue<>(100),
        new ThreadPoolExecutor.AbortPolicy()
    );

    private static final ExecutorService cpuExecutor = new ThreadPoolExecutor(
        2, 4, 30L, TimeUnit.SECONDS,
        new LinkedBlockingQueue<>(100),
        new ThreadPoolExecutor.AbortPolicy()
    );

    public static void main(String[] args) {
        for (int i = 0; i < 3; i++) {
            int taskId = i;
            workerExecutor.execute(() -> {
                System.out.println("io task " + taskId + " on " + Thread.currentThread().getName());
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        for (int i = 0; i < 3; i++) {
            int taskId = i;
            cpuExecutor.execute(() -> {
                long sum = 0;
                for (int j = 0; j < 10000; j++) {
                    sum += j;
                }
                System.out.println("cpu task " + taskId + " sum=" + sum);
            });
        }

        try {
            workerExecutor.awaitTermination(1, TimeUnit.SECONDS);
            cpuExecutor.awaitTermination(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("worker pool queue: " + ((ThreadPoolExecutor) workerExecutor).getQueue().size());

        System.out.println("warn: parallel stream uses ForkJoinPool, not worker");

        ExecutorService fixedPool = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 3; i++) {
            int taskId = i;
            fixedPool.execute(() -> System.out.println("fixed pool " + taskId));
        }
        fixedPool.shutdown();
        System.out.println("worker pool active: " + ((ThreadPoolExecutor) workerExecutor).getActiveCount());
        System.out.println("cpu pool active: " + ((ThreadPoolExecutor) cpuExecutor).getActiveCount());

        CompletableFuture.runAsync(() -> System.out.println("cf on worker"), workerExecutor)
            .handle((v, ex) -> {
                System.out.println("cf done");
                return null;
            }).join();

        Future<?> submit = workerExecutor.submit(() -> System.out.println("submit task"));
        try {
            submit.get(1, TimeUnit.SECONDS);
        } catch (Exception e) {
            Thread.currentThread().interrupt();
        }

        workerExecutor.shutdown();
        cpuExecutor.shutdown();
    }
}
