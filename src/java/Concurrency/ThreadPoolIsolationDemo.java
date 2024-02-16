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

        workerExecutor.shutdown();
        cpuExecutor.shutdown();
    }
}
