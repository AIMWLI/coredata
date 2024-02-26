package Concurrency;

import java.util.concurrent.*;

/**
 * 超时策略（JDK8 默认环境）：
 * - 禁止裸 join() / 裸 get()：阻塞业务线程，高并发下资源浪费与雪崩
 * - 唯一允许阻塞：get(timeout, TimeUnit) 有界阻塞
 * - 非阻塞快速尝试：getNow(defaultValue) 仅限特定场景
 * - 禁止 completeOnTimeout()：不取消任务导致资源泄漏
 */
public class CompletableFutureTimeoutDemo {

    private static final ExecutorService worker = new ThreadPoolExecutor(
        4, 8, 60L, TimeUnit.SECONDS,
        new LinkedBlockingQueue<>(100),
        new ThreadPoolExecutor.AbortPolicy()
    );

    public static void main(String[] args) {
        // 正确：get(timeout, TimeUnit) 有界阻塞
        CompletableFuture<String> fast = CompletableFuture.supplyAsync(() -> "ok", worker);
        try {
            String r = fast.get(1, TimeUnit.SECONDS);
            System.out.println("get with timeout: " + r);
        } catch (TimeoutException e) {
            System.out.println("timeout: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("error: " + e.getMessage());
        }

        // getNow 非阻塞快速尝试
        String now = CompletableFuture.supplyAsync(() -> {
            sleep(100); return "slow";
        }, worker).getNow("default");
        System.out.println("getNow: " + now);

        // handle + get(timeout) 完整模式
        CompletableFuture<String> guarded = CompletableFuture.supplyAsync(() -> {
            sleep(50); return "data";
        }, worker).handle((r, ex) -> ex != null ? "timeout-fallback" : r);

        try {
            String r = guarded.get(2, TimeUnit.SECONDS);
            System.out.println("guarded: " + r);
        } catch (Exception e) {
            System.out.println("guard error: " + e.getMessage());
        }

        worker.shutdown();
    }

    static void sleep(long ms) { try { Thread.sleep(ms); } catch (InterruptedException e) { Thread.currentThread().interrupt(); } }
}
