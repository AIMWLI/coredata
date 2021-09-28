package Concurrency;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

/**
 * 无锁设计：
 * - 优先 ConcurrentHashMap、AtomicLong、LongAdder
 * - 必须加锁时用 ReentrantLock 替代 synchronized
 * - 严禁锁内执行 I/O 或远程调用
 */
public class LockFreeDesignDemo {

    private static final ConcurrentHashMap<String, LongAdder> visitCounter = new ConcurrentHashMap<>();
    private static final AtomicLong totalVisits = new AtomicLong(0);

    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(4);
        String[] pages = {"/home", "/about", "/api/users", "/home", "/home", "/about"};

        for (String page : pages) {
            String p = page;
            pool.execute(() -> {
                visitCounter.computeIfAbsent(p, k -> new LongAdder()).increment();
                totalVisits.incrementAndGet();
            });
        }

        pool.shutdown();
        try { pool.awaitTermination(1, TimeUnit.SECONDS); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }

        System.out.println("total: " + totalVisits.get());
        visitCounter.forEach((k, v) -> System.out.println("  " + k + "=" + v.sum()));

        // LongAdder 高并发计数器 vs AtomicLong
        LongAdder highFreq = new LongAdder();
        AtomicLong atomic = new AtomicLong(0);

        long start = System.nanoTime();
        for (int i = 0; i < 100_000; i++) highFreq.increment();
        long t1 = System.nanoTime() - start;

        start = System.nanoTime();
        for (int i = 0; i < 100_000; i++) atomic.incrementAndGet();
        long t2 = System.nanoTime() - start;

        System.out.println("LongAdder=" + highFreq.sum() + " time=" + t1 / 1_000 + "us");
        System.out.println("AtomicLong=" + atomic.get() + " time=" + t2 / 1_000 + "us");

        ConcurrentHashMap<String, Integer> scoreMap = new ConcurrentHashMap<>();
        scoreMap.put("alice", 90);
        scoreMap.computeIfPresent("alice", (k, v) -> v + 5);
        scoreMap.putIfAbsent("bob", 85);
        System.out.println("score alice: " + scoreMap.get("alice"));
    }
}

// push
public static void push() {}
