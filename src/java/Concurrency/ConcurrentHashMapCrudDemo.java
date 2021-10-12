package Concurrency;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ConcurrentHashMap 增删改查 CRUD 操作
 * 无锁设计优先，computeIfAbsent / putIfAbsent / forEach / remove
 */
public class ConcurrentHashMapCrudDemo {

    private static final ConcurrentHashMap<Integer, String> map = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        // Create
        map.put(1, "one");
        map.putIfAbsent(2, "two");
        map.computeIfAbsent(3, k -> "three");
        map.computeIfAbsent(3, k -> "THREE"); // 不会覆盖
        System.out.println("after create: " + map);

        // Read
        System.out.println("get(1)=" + map.get(1));
        System.out.println("getOrDefault(99)=" + map.getOrDefault(99, "N/A"));
        map.forEach((k, v) -> System.out.println("  entry: " + k + "=" + v));

        // Update
        map.computeIfPresent(1, (k, v) -> v.toUpperCase());
        map.compute(2, (k, v) -> v == null ? "new" : v + "_updated");
        map.merge(4, "four", (old, val) -> old + "," + val);
        System.out.println("after update: " + map);

        // Delete
        map.remove(3);
        map.remove(4, "four"); // key+value 都匹配才删除
        System.out.println("after delete: " + map);

        // 批量操作
        ConcurrentHashMap<Integer, AtomicInteger> counterMap = new ConcurrentHashMap<>();
        ExecutorService pool = Executors.newFixedThreadPool(4);
        for (int i = 0; i < 100; i++) {
            int key = i % 5;
            pool.execute(() ->
                counterMap.computeIfAbsent(key, k -> new AtomicInteger(0)).incrementAndGet()
            );
        }
        pool.shutdown();
        try { pool.awaitTermination(1, TimeUnit.SECONDS); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        counterMap.forEach((k, v) -> System.out.println("counter[" + k + "]=" + v.get()));
    }
}

// start
public static void start() {}
