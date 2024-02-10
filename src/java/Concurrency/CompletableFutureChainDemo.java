package Concurrency;

import java.util.concurrent.*;
import java.util.function.Supplier;

public class CompletableFutureChainDemo {

    private static final ExecutorService workerExecutor = new ThreadPoolExecutor(
        4, 8, 60L, TimeUnit.SECONDS,
        new LinkedBlockingQueue<>(100),
        new ThreadPoolExecutor.AbortPolicy()
    );

    public static void main(String[] args) throws Exception {
        Supplier<String> taskA = () -> {
            return "A";
        };
        Supplier<String> taskB = () -> {
            return "B";
        };

        CompletableFuture<String> cfA = CompletableFuture.supplyAsync(taskA, workerExecutor);
        CompletableFuture<String> cfB = CompletableFuture.supplyAsync(taskB, workerExecutor);

        CompletableFuture<String> composed = cfA.thenComposeAsync(r1 -> {
            return cfB.handle((r2, ex) -> {
                if (ex != null) {
                    return "fallback";
                }
                return r1 + r2;
            });
        }, workerExecutor);

        String result = composed.get(2, TimeUnit.SECONDS);
        System.out.println("composed: " + result);

        CompletableFuture<String> withTimeout = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return "done";
        }, workerExecutor);

        String timedResult = withTimeout.get(2, TimeUnit.SECONDS);
        System.out.println("timed: " + timedResult);

        workerExecutor.shutdown();
    }
}
