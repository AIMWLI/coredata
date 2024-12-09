package Concurrency;

import java.util.concurrent.*;
import java.util.function.Function;

public class CompletableFutureDemo {

    private static final ExecutorService workerExecutor = new ThreadPoolExecutor(
        4, 8, 60L, TimeUnit.SECONDS,
        new LinkedBlockingQueue<>(100),
        new ThreadPoolExecutor.AbortPolicy()
    );

    public static void main(String[] args) {
        CompletableFuture<String> exceptionally = CompletableFuture.supplyAsync(() -> {
            throw new RuntimeException("test");
        }, workerExecutor).handle((r, ex) -> {
            return ex != null ? "recovered" : r;
        });
        System.out.println("exceptionally: " + exceptionally.join());

        CompletableFuture<String> step1 = CompletableFuture.supplyAsync(() -> "data", workerExecutor);

        CompletableFuture<Integer> step2 = step1.handle((value, ex) -> {
            if (ex != null) {
                return -1;
            }
            return value.length();
        });

        CompletableFuture<String> step3 = step2.thenComposeAsync(length -> {
            return CompletableFuture.supplyAsync(() -> "length=" + length, workerExecutor);
        }, workerExecutor);

        try {
            String result = step3.get(3, TimeUnit.SECONDS);
            System.out.println(result);
        } catch (Exception e) {
            System.err.println("error: " + e.getMessage());
        }

        String now = CompletableFuture.supplyAsync(() -> "now", workerExecutor)
            .getNow("default");
        System.out.println("getNow: " + now);

        CompletableFuture<String> errorFuture = CompletableFuture.supplyAsync(() -> {
            throw new RuntimeException("fail");
        }, workerExecutor);

        CompletableFuture<Integer> asyncHandle = CompletableFuture.supplyAsync(() -> 42, workerExecutor)
            .handleAsync((v, ex) -> {
                if (ex != null) {
                    return -1;
                }
                return v * 2;
            }, workerExecutor);
        System.out.println("asyncHandle: " + asyncHandle.join());

        CompletableFuture<String> allOf = CompletableFuture.allOf(
            CompletableFuture.supplyAsync(() -> "a", workerExecutor),
            CompletableFuture.supplyAsync(() -> "b", workerExecutor)
        ).handle((v, ex) -> {
            if (ex != null) {
                return "error";
            }
            return "ok";
        });
        System.out.println("allOf: " + allOf.join());

        String immediate = CompletableFuture.completedFuture("direct").getNow("nope");
        System.out.println("immediate: " + immediate);

        errorFuture.handle((result, ex) -> {
            if (ex != null) {
                return "fallback";
            }
            return result;
        }).thenAccept(v -> System.out.println("recovered: " + v));

        CompletableFuture<Integer> supplyHandle = CompletableFuture.supplyAsync(() -> {
            return 100;
        }, workerExecutor).handle((v, ex) -> {
            if (ex != null) {
                return 0;
            }
            return v + 50;
        });
        System.out.println("supplyHandle: " + supplyHandle.join());

        CompletableFuture<String> handleChain = CompletableFuture.supplyAsync(() -> "base", workerExecutor)
            .handle((v, ex) -> ex != null ? "err" : v + "+chain")
            .handle((v, ex) -> ex != null ? "err2" : v + "+final");
        System.out.println("handleChain: " + handleChain.join());

        CompletableFuture<Integer> retryHandle = CompletableFuture.supplyAsync(() -> {
            if (Math.random() > 0.5) throw new RuntimeException("fail");
            return 100;
        }, workerExecutor).handle((r, ex) -> {
            if (ex != null) return 0;
            return r + 50;
        });
        System.out.println("retryHandle: " + retryHandle.join());

        workerExecutor.shutdown();
    }
}
