package Concurrency;

import java.util.concurrent.locks.*;
import java.util.concurrent.*;

/**
 * Condition：ReentrantLock 的条件等待/通知
 * 替代 Object.wait/notify，支持多个条件队列
 */
public class LockConditionDemo {

    private static final ReentrantLock lock = new ReentrantLock();
    private static final Condition notEmpty = lock.newCondition();
    private static final Condition notFull = lock.newCondition();
    private static final String[] buffer = new String[5];
    private static int count = 0, putIdx = 0, takeIdx = 0;

    public static void main(String[] args) throws Exception {
        ExecutorService pool = Executors.newFixedThreadPool(3);

        pool.execute(() -> {
            for (int i = 0; i < 5; i++) {
                try { put("item-" + i); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            }
        });

        pool.execute(() -> {
            for (int i = 0; i < 5; i++) {
                try { System.out.println("  take: " + take()); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            }
        });

        pool.shutdown();
        pool.awaitTermination(2, TimeUnit.SECONDS);

        System.out.println("condition demo done, count=" + count);

        // signalAll 示例
        ReentrantLock demo = new ReentrantLock();
        Condition cond = demo.newCondition();
        System.out.println("Condition created: " + (cond != null));
    }

    static void put(String v) throws InterruptedException {
        lock.lock();
        try {
            while (count == buffer.length) notFull.await();
            buffer[putIdx] = v;
            if (++putIdx == buffer.length) putIdx = 0;
            count++;
            notEmpty.signal();
            System.out.println("put: " + v);
        } finally {
            lock.unlock();
        }
    }

    static String take() throws InterruptedException {
        lock.lock();
        try {
            while (count == 0) notEmpty.await();
            String v = buffer[takeIdx];
            if (++takeIdx == buffer.length) takeIdx = 0;
            count--;
            notFull.signal();
            return v;
        } finally {
            lock.unlock();
        }
    }
}
