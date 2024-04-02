package Concurrency;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * CountDownLatch（一次性门闩） vs CyclicBarrier（循环屏障）
 * CountDownLatch: 主线程等待多个子任务完成
 * CyclicBarrier: 多个线程互相等待到齐后继续
 */
public class CountDownLatchCyclicBarrierDemo {

    private static final int N = 4;

    public static void main(String[] args) throws Exception {
        // CountDownLatch: 等待所有任务完成
        CountDownLatch latch = new CountDownLatch(N);
        ExecutorService pool = Executors.newFixedThreadPool(N);
        AtomicInteger sum = new AtomicInteger(0);

        for (int i = 0; i < N; i++) {
            int v = i + 1;
            pool.execute(() -> {
                try { Thread.sleep(50); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                sum.addAndGet(v * 10);
                latch.countDown();
            });
        }
        latch.await(2, TimeUnit.SECONDS);
        System.out.println("CountDownLatch sum=" + sum.get());

        // CyclicBarrier: 重复使用
        CyclicBarrier barrier = new CyclicBarrier(N, () ->
            System.out.println("=== barrier tripped ===")
        );

        for (int round = 0; round < 2; round++) {
            int r = round;
            for (int i = 0; i < N; i++) {
                pool.execute(() -> {
                    try {
                        Thread.sleep((long) (Math.random() * 100));
                        System.out.println(Thread.currentThread().getName() + " round" + r + " waiting");
                        barrier.await(2, TimeUnit.SECONDS);
                        System.out.println(Thread.currentThread().getName() + " round" + r + " passed");
                    } catch (Exception e) {
                        Thread.currentThread().interrupt();
                    }
                });
            }
            Thread.sleep(500);
        }

        pool.shutdown();
    }
}
