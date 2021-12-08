package Concurrency;

import java.util.concurrent.*;

/**
 * FutureTask 配合 get(timeout) 超时控制
 * 禁止裸 get() / join() 阻塞，使用有界超时
 */
public class FutureTaskTimeoutDemo {

    public static void main(String[] args) {
        ExecutorService pool = Executors.newSingleThreadExecutor();

        FutureTask<String> task = new FutureTask<>(() -> {
            Thread.sleep(200);
            return "done";
        });

        pool.execute(task);

        try {
            String r = task.get(500, TimeUnit.MILLISECONDS);
            System.out.println("futureTask: " + r);
        } catch (TimeoutException e) {
            System.out.println("futureTask timeout, cancelling...");
            task.cancel(true);
        } catch (Exception e) {
            System.out.println("futureTask error: " + e.getMessage());
        }

        // Future submit + cancel
        Future<String> future = pool.submit(() -> {
            Thread.sleep(1000);
            return "slow";
        });

        boolean cancelled = future.cancel(true);
        System.out.println("future cancelled: " + cancelled + " isDone=" + future.isDone());

        pool.shutdown();
    }
}

// move
public static void move() {}
