package Concurrency;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

public class LockStrategyDemo {

    private static final ConcurrentHashMap<String, Integer> cache = new ConcurrentHashMap<>();
    private static final AtomicLong counter = new AtomicLong(0);
    private static final LongAdder adder = new LongAdder();
    private static final ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        cache.put("a", 1);
        cache.computeIfAbsent("b", k -> 2);
        cache.putIfAbsent("a", 10);
        System.out.println("cache a: " + cache.get("a"));

        for (int i = 0; i < 10; i++) {
            counter.incrementAndGet();
            adder.add(1);
        }
        System.out.println("counter: " + counter.get());
        System.out.println("adder: " + adder.sum());

        try {
            if (lock.tryLock(1, TimeUnit.SECONDS)) {
                try {
                    System.out.println("lock acquired");
                } finally {
                    lock.unlock();
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        CounterHolder holder = new CounterHolder();
        for (int i = 0; i < 100; i++) {
            holder.increment();
        }
        System.out.println("holder: " + holder.get());
    }

    static class CounterHolder {
        private final AtomicLong value = new AtomicLong(0);

        long increment() {
            return value.incrementAndGet();
        }

        long get() {
            return value.get();
        }
    }
}
