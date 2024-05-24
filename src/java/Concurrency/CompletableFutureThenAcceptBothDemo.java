package Concurrency;

import java.util.concurrent.*;

/**
 * thenAcceptBoth / runAfterBoth / applyToEither
 * 两个 CompletionStage 合并消费
 */
public class CompletableFutureThenAcceptBothDemo {

    private static final ExecutorService worker = new ThreadPoolExecutor(
        2, 4, 30L, TimeUnit.SECONDS,
        new LinkedBlockingQueue<>(20),
        new ThreadPoolExecutor.AbortPolicy()
    );

    public static void main(String[] args) {
        CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> "hello", worker);
        CompletableFuture<String> f2 = CompletableFuture.supplyAsync(() -> "world", worker);

        // thenAcceptBoth: 两个都完成时消费
        f1.thenAcceptBothAsync(f2, (a, b) ->
            System.out.println("thenAcceptBoth: " + a + " " + b), worker
        ).join();

        // runAfterBoth: 两个都完成后执行
        f1.runAfterBothAsync(f2, () ->
            System.out.println("runAfterBoth: both done"), worker
        ).join();

        // applyToEither: 任意一个完成时转换
        CompletableFuture<String> fast = CompletableFuture.supplyAsync(() -> {
            sleep(20); return "fast";
        }, worker);
        CompletableFuture<String> slow = CompletableFuture.supplyAsync(() -> {
            sleep(100); return "slow";
        }, worker);

        fast.applyToEitherAsync(slow, r ->
            "winner: " + r, worker
        ).thenAccept(System.out::println);

        worker.shutdown();
    }

    static void sleep(long ms) { try { Thread.sleep(ms); } catch (InterruptedException e) { Thread.currentThread().interrupt(); } }
}
