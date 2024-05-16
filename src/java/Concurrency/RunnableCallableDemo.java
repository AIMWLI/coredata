package Concurrency;

import java.util.concurrent.*;

/**
 * Runnable vs Callable 对比
 * Runnable: void run() 无返回值，不能抛受检异常
 * Callable: V call() 有返回值，能抛受检异常
 */
public class RunnableCallableDemo {

    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(2);

        // Runnable 无返回值
        pool.execute(() -> System.out.println("runnable task on " + Thread.currentThread().getName()));

        // Callable 有返回值
        Future<Integer> future = pool.submit(() -> {
            Thread.sleep(50);
            return 42;
        });

        try {
            Integer r = future.get(1, TimeUnit.SECONDS);
            System.out.println("callable result: " + r);
        } catch (Exception e) {
            System.out.println("error: " + e.getMessage());
        }

        // Runnable 转 Callable
        Future<String> wrapped = pool.submit(() -> System.out.println("wrapped"), "success");
        try {
            System.out.println("wrapped result: " + wrapped.get());
        } catch (Exception e) {
            System.out.println("wrapped error: " + e.getMessage());
        }

        pool.shutdown();
    }
}
