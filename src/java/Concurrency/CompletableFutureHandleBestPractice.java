package Concurrency;

import java.util.concurrent.*;
import java.util.function.BiFunction;

/**
 * CompletableFuture handle 最佳实践：
 * - handle 是工业级生产首选：统一错误处理，链式不中断
 * - thenApply/thenAccept 致命局限：上游异常时短路跳过
 * - handle 纯内存计算用同步；阻塞 I/O 用 handleAsync + 业务线程池
 */
public class CompletableFutureHandleBestPractice {

    private static final ExecutorService worker = new ThreadPoolExecutor(
        4, 8, 60L, TimeUnit.SECONDS,
        new LinkedBlockingQueue<>(100),
        new ThreadPoolExecutor.AbortPolicy()
    );

    public static void main(String[] args) {
        // handle 统一处理成功与异常
        CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> {
            if (Math.random() > 0.5) throw new RuntimeException("simulated failure");
            return "success";
        }, worker).handle((r, ex) -> ex != null ? "recovered: " + ex.getMessage() : r);
        System.out.println("handle result: " + f1.join());

        // handleAsync 用于含阻塞 I/O 的回调，必须指定线程池
        CompletableFuture<Integer> f2 = CompletableFuture.supplyAsync(() -> 42, worker)
            .handleAsync((v, ex) -> {
                if (ex != null) return -1;
                return v * 2;
            }, worker);
        System.out.println("handleAsync result: " + f2.join());

        // thenApply 缺陷：上游异常直接短路，不会执行
        CompletableFuture<String> bad = CompletableFuture.supplyAsync(() -> {
            throw new RuntimeException("err");
        }, worker).thenApply(v -> v + "+extra").handle((r, ex) -> {
            return ex != null ? "caught by handle" : r;
        });
        System.out.println("thenApply defect: " + bad.join());

        // handle 双保险链
        CompletableFuture<String> chain = CompletableFuture.supplyAsync(() -> "data", worker)
            .handle((r, ex) -> ex != null ? "fallback" : r + "_checked")
            .handle((r, ex) -> ex != null ? "err" : r + "_final");
        System.out.println("handle chain: " + chain.join());

        worker.shutdown();
    }
}
