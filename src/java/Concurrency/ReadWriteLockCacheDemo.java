package Concurrency;

import java.util.concurrent.locks.*;
import java.util.concurrent.*;

/**
 * ReadWriteLock 读写锁：读读不互斥、写写互斥、读写互斥
 * 适用于读多写少场景，如本地缓存
 */
public class ReadWriteLockCacheDemo {

    private static final ConcurrentHashMap<String, String> cache = new ConcurrentHashMap<>();
    private static final ReadWriteLock rwLock = new ReentrantReadWriteLock();
    private static int loadCount = 0;

    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(6);
        for (int i = 0; i < 10; i++) {
            String key = "key" + (i % 3);
            pool.execute(() -> {
                String val = get(key);
                System.out.println(Thread.currentThread().getName() + " " + key + "=" + val);
            });
        }
        pool.shutdown();
        try { pool.awaitTermination(2, TimeUnit.SECONDS); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        System.out.println("total loads: " + loadCount);

        // ReentrantReadWriteLock 降级：写锁 → 读锁
        ReentrantReadWriteLock demo = new ReentrantReadWriteLock();
        demo.writeLock().lock();
        try {
            System.out.println("write lock acquired");
            demo.readLock().lock();
        } finally {
            demo.writeLock().unlock();
        }
        try {
            System.out.println("read lock after write unlock");
        } finally {
            demo.readLock().unlock();
        }

        System.out.println("cache contents: " + cache);
    }

    static String get(String key) {
        // 读锁：允许多线程并发读
        rwLock.readLock().lock();
        try {
            String val = cache.get(key);
            if (val != null) return val;
        } finally {
            rwLock.readLock().unlock();
        }

        // 写锁：只允许一个线程加载
        rwLock.writeLock().lock();
        try {
            String val = cache.get(key);
            if (val != null) return val;
            loadCount++;
            String v = "loaded-" + key;
            cache.put(key, v);
            return v;
        } finally {
            rwLock.writeLock().unlock();
        }
    }
}
