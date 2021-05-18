package ThreadPool;

import java.util.concurrent.*;

public class ThreadPoolAsyncDemo {

    private static final ExecutorService worker = new ThreadPoolExecutor(
        2, 4, 30L, TimeUnit.SECONDS,
        new LinkedBlockingQueue<>(50),
        new ThreadPoolExecutor.AbortPolicy()
    );

    public static void main(String[] args) throws Exception {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            return "async result";
        }, worker).handle((r, ex) -> ex != null ? "error" : r);

        System.out.println(future.get(1, TimeUnit.SECONDS));
        worker.shutdown();
    }
}
