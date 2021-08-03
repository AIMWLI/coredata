package util;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Map compute/merge/putIfAbsent 增删改查进阶
 * ConcurrentHashMap 无锁 CRUD 操作
 */
public class MapMergeComputeCrudDemo {

    public static void main(String[] args) {
        ConcurrentHashMap<String, LongAdder> freq = new ConcurrentHashMap<>();

        // Create: computeIfAbsent 原子初始化
        freq.computeIfAbsent("apple", k -> new LongAdder()).increment();
        freq.computeIfAbsent("apple", k -> new LongAdder()).increment();
        freq.computeIfAbsent("banana", k -> new LongAdder()).increment();
        System.out.println("freq: " + freq);

        // Read
        System.out.println("apple=" + freq.get("apple").sum());
        System.out.println("banana=" + freq.getOrDefault("banana", null));

        // Update: compute 原子更新
        ConcurrentHashMap<String, String> cache = new ConcurrentHashMap<>();
        cache.put("config", "v1");
        cache.compute("config", (k, v) -> v == null ? "default" : v + "_updated");
        cache.computeIfPresent("config", (k, v) -> v.toUpperCase());
        System.out.println("cache: " + cache);

        // Merge
        cache.merge("key1", "val1", (old, val) -> old + "," + val);
        cache.merge("key1", "val2", (old, val) -> old + "," + val);
        System.out.println("merged: " + cache);

        // Delete
        cache.remove("key1");
        cache.remove("config", "V1_UPDATED");
        System.out.println("after remove: " + cache);

        // 计数器场景
        ConcurrentHashMap<String, AtomicLong> stats = new ConcurrentHashMap<>();
        for (String s : new String[]{"hit", "miss", "hit", "hit", "miss"}) {
            stats.computeIfAbsent(s, k -> new AtomicLong()).incrementAndGet();
        }
        System.out.println("stats: " + stats);
        System.out.println("hit=" + stats.get("hit").get() + " miss=" + stats.get("miss").get());

        ConcurrentHashMap<String, StringBuilder> sbMap = new ConcurrentHashMap<>();
        sbMap.computeIfAbsent("log", k -> new StringBuilder()).append("start");
        sbMap.computeIfAbsent("log", k -> new StringBuilder()).append(" -> end");
        System.out.println("sbMap log: " + sbMap.get("log"));
    }
}
