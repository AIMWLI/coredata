package Concurrency;

import java.util.concurrent.*;
import java.util.concurrent.locks.*;

/**
 * 锁策略（替代 synchronized）：
 * - ReentrantLock 支持超时与可中断
 * - 严禁在锁内执行 I/O 或远程调用
 * - 优先 tryLock(timeout) 避免死锁
 */
public class ReentrantLockAdvancedDemo {

    private static final ReentrantLock fairLock = new ReentrantLock(true);
    private static final ReentrantLock lock = new ReentrantLock();
    private static int sharedCounter = 0;

    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(4);

        for (int i = 0; i < 8; i++) {
            pool.execute(() -> {
                try {
                    if (lock.tryLock(500, TimeUnit.MILLISECONDS)) {
                        try {
                            int v = ++sharedCounter;
                            System.out.println(Thread.currentThread().getName() + " counter=" + v);
                        } finally {
                            lock.unlock();
                        }
                    } else {
                        System.out.println(Thread.currentThread().getName() + " lock timeout");
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        pool.shutdown();
        try { pool.awaitTermination(2, TimeUnit.SECONDS); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        System.out.println("final counter=" + sharedCounter);
    }
}
