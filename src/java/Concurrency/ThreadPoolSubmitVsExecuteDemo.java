package Concurrency;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * submit vs execute 对比：
 * - execute(Runnable): void，异常在线程内部消化
 * - submit(Callable/Runnable): Future，异常可通过 Future.get() 捕获
 * 生产推荐 submit + Future.get(timeout)
 */
public class ThreadPoolSubmitVsExecuteDemo {

    public static void main(String[] args) {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(
            2, 4, 30L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(10),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.AbortPolicy()
        );

        AtomicInteger counter = new AtomicInteger(0);

        // execute: 无返回值
        pool.execute(() -> {
            counter.incrementAndGet();
            System.out.println("execute task done");
        });

        // submit Runnable: Future 可获知完成状态
        Future<?> future1 = pool.submit(() -> counter.incrementAndGet());
        try {
            future1.get(1, TimeUnit.SECONDS);
            System.out.println("submit Runnable done");
        } catch (Exception e) {
            System.out.println("submit error: " + e.getMessage());
        }

        // submit Callable: 有返回值
        Future<Integer> future2 = pool.submit(() -> {
            return counter.incrementAndGet();
        });
        try {
            Integer r = future2.get(1, TimeUnit.SECONDS);
            System.out.println("submit Callable result: " + r);
        } catch (Exception e) {
            System.out.println("submit error: " + e.getMessage());
        }

        // submit 异常可捕获 vs execute 异常静默
        Future<?> errorFuture = pool.submit(() -> { throw new RuntimeException("hidden"); });
        try {
            errorFuture.get(1, TimeUnit.SECONDS);
        } catch (Exception e) {
            System.out.println("submit exception caught: " + e.getCause().getMessage());
        }

        pool.shutdown();
        System.out.println("final counter: " + counter.get());
    }
}
