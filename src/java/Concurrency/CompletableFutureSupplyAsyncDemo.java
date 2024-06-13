package Concurrency;

import java.util.concurrent.*;

/**
 * supplyAsync / runAsync 最佳实践
 * - supplyAsync + handle 为生产标准模式
 * - 强制指定业务线程池，禁止依赖 ForkJoinPool
 */
public class CompletableFutureSupplyAsyncDemo {

    private static final ExecutorService worker = new ThreadPoolExecutor(
        4, 8, 60L, TimeUnit.SECONDS,
        new LinkedBlockingQueue<>(100),
        new ThreadPoolExecutor.AbortPolicy()
    );

    public static void main(String[] args) throws Exception {
        // supplyAsync + handle 标准模式
        CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> "hello", worker)
            .handle((r, ex) -> ex != null ? "err" : r + " world");
        System.out.println("supplyAsync handle: " + f1.get(1, TimeUnit.SECONDS));

        // runAsync 无返回值
        CompletableFuture<Void> f2 = CompletableFuture.runAsync(() ->
            System.out.println("runAsync on " + Thread.currentThread().getName()), worker
        ).handle((v, ex) -> {
            System.out.println("runAsync done" + (ex != null ? " ex=" + ex.getMessage() : ""));
            return null;
        });
        f2.get(1, TimeUnit.SECONDS);

        // 多 supplyAsync 组合
        CompletableFuture<Double> price = CompletableFuture.supplyAsync(() -> 100.50, worker);
        CompletableFuture<Integer> quantity = CompletableFuture.supplyAsync(() -> 3, worker);

        price.thenCombineAsync(quantity, (p, q) -> p * q, worker)
            .handle((r, ex) -> {
                System.out.println("total price: " + (ex != null ? "error" : r));
                return null;
            }).get(1, TimeUnit.SECONDS);

        worker.shutdown();
    }
}
