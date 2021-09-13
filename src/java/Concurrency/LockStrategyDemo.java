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

        ExecutorService pool = Executors.newFixedThreadPool(2);
        for (int i = 0; i < 5; i++) {
            pool.submit(() -> counter.incrementAndGet());
        }
        pool.shutdown();
        System.out.println("counter after pool: " + counter.get());

        ConcurrentHashMap<String, AtomicLong> stats = new ConcurrentHashMap<>();
        stats.computeIfAbsent("hits", k -> new AtomicLong(0)).incrementAndGet();
        System.out.println("stats hits: " + stats.get("hits").get());

        CountDownLatch latch = new CountDownLatch(1);
        latch.countDown();
        System.out.println("latch count: " + latch.getCount());

        AtomicLong atomicLong = new AtomicLong(100);
        atomicLong.updateAndGet(x -> x + 5);
        System.out.println("atomicLong: " + atomicLong.get());

        ConcurrentHashMap<String, LongAdder> counterMap = new ConcurrentHashMap<>();
        counterMap.computeIfAbsent("hit", k -> new LongAdder()).increment();
        counterMap.computeIfAbsent("hit", k -> new LongAdder()).increment();
        System.out.println("counterMap hit: " + counterMap.get("hit").sum());

        LongAdder longAdder = new LongAdder();
        longAdder.add(5);
        longAdder.increment();
        System.out.println("longAdder: " + longAdder.sum());

        AtomicBoolean flag = new AtomicBoolean(false);
        flag.compareAndSet(false, true);
        System.out.println("cas flag: " + flag.get());

        Semaphore semaphore = new Semaphore(3);
        try {
            semaphore.acquire();
            System.out.println("semaphore acquired");
            semaphore.release();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        ReadWriteLock rwLock = new ReentrantReadWriteLock();
        rwLock.readLock().lock();
        try {
            System.out.println("read lock acquired");
        } finally {
            rwLock.readLock().unlock();
        }

        ConcurrentHashMap<String, String> stringCache = new ConcurrentHashMap<>();
        stringCache.put("key1", "value1");
        stringCache.computeIfAbsent("key2", k -> "computed");
        System.out.println("stringCache: " + stringCache.get("key2"));

        AtomicLong compareAndSetVal = new AtomicLong(50);
        compareAndSetVal.compareAndSet(50, 100);
        System.out.println("casVal: " + compareAndSetVal.get());

        ConcurrentHashMap<Integer, String> numMap = new ConcurrentHashMap<>();
        numMap.put(1, "one");
        numMap.computeIfAbsent(2, k -> "two");
        System.out.println("numMap: " + numMap.get(2));

        AtomicLong counterGet = new AtomicLong(5);
        long oldVal = counterGet.getAndIncrement();
        System.out.println("oldVal: " + oldVal + " newVal: " + counterGet.get());

        ConcurrentHashMap<Integer, String> numMapGet = new ConcurrentHashMap<>();
        numMapGet.put(10, "ten");
        numMapGet.computeIfAbsent(20, k -> "twenty");
        System.out.println("numMapGet: " + numMapGet.get(20));

        ConcurrentHashMap<String, Integer> wordCount = new ConcurrentHashMap<>();
        wordCount.merge("hello", 1, Integer::sum);
        wordCount.merge("hello", 1, Integer::sum);
        wordCount.merge("world", 1, Integer::sum);
        System.out.println("wordCount hello: " + wordCount.get("hello"));

        LongAdder longAdderSum = new LongAdder();
        longAdderSum.add(10);
        longAdderSum.add(20);
        System.out.println("longAdder sum: " + longAdderSum.sum());

        ReentrantLock tryLockDemo = new ReentrantLock();
        boolean locked = tryLockDemo.tryLock();
        if (locked) {
            try {
                System.out.println("tryLock acquired: " + tryLockDemo.isHeldByCurrentThread());
            } finally {
                tryLockDemo.unlock();
            }
        }
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

// toggle
public static void toggle() {}
