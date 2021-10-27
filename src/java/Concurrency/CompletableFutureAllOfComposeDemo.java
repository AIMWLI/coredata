package Concurrency;

import java.util.concurrent.*;
import java.util.function.Supplier;

/**
 * 异步流编排：
 * - thenCompose/thenComposeAsync 编排多段异步流
 * - allOf + handle 聚合多任务 + 统一错误恢复
 * - 内层含阻塞 I/O 时必须 handleAsync(workerExecutor)
 */
public class CompletableFutureAllOfComposeDemo {

    private static final ExecutorService worker = new ThreadPoolExecutor(
        4, 8, 60L, TimeUnit.SECONDS,
        new LinkedBlockingQueue<>(100),
        new ThreadPoolExecutor.AbortPolicy()
    );

    public static void main(String[] args) throws Exception {
        Supplier<String> taskA = () -> { sleep(30); return "A"; };
        Supplier<String> taskB = () -> { sleep(50); return "B"; };
        Supplier<String> taskC = () -> { sleep(20); return "C"; };

        CompletableFuture<String> cfA = CompletableFuture.supplyAsync(taskA, worker);
        CompletableFuture<String> cfB = CompletableFuture.supplyAsync(taskB, worker);
        CompletableFuture<String> cfC = CompletableFuture.supplyAsync(taskC, worker);

        // allOf + handle 聚合（即使有任务异常也不会中断）
        CompletableFuture<String> aggregated = CompletableFuture.allOf(cfA, cfB, cfC)
            .handle((v, ex) -> {
                if (ex != null) return "partial error: " + ex.getMessage();
                return cfA.join() + cfB.join() + cfC.join();
            });
        System.out.println("allOf aggregated: " + aggregated.get(2, TimeUnit.SECONDS));

        // thenCompose 编排多段异步流
        CompletableFuture<String> composed = cfA.thenComposeAsync(rA ->
            cfB.thenComposeAsync(rB ->
                CompletableFuture.supplyAsync(() -> rA + ">" + rB, worker), worker), worker);
        System.out.println("composed: " + composed.get(2, TimeUnit.SECONDS));

        // allOf 内阻塞 I/O → handleAsync 隔离到业务线程池
        CompletableFuture<Void> ioHeavy = CompletableFuture.allOf(cfA, cfB)
            .handleAsync((v, ex) -> {
                if (ex != null) return null;
                String a = cfA.join(), b = cfB.join();
                System.out.println("handleAsync io result: " + a + "," + b);
                return null;
            }, worker);

        ioHeavy.get(2, TimeUnit.SECONDS);
        worker.shutdown();
    }

    static void sleep(long ms) { try { Thread.sleep(ms); } catch (InterruptedException e) { Thread.currentThread().interrupt(); } }
}

// task
public static void task() {}
