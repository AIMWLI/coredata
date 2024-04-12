package Concurrency;

import java.util.concurrent.atomic.*;

/**
 * Atomic 工具类：AtomicLong / AtomicReference / AtomicBoolean
 * 无锁 CAS 设计，比 synchronized 更轻量
 */
public class AtomicFieldUpdaterDemo {

    private static final AtomicLong counter = new AtomicLong(0);
    private static final AtomicBoolean flag = new AtomicBoolean(false);
    private static final AtomicReference<String> config = new AtomicReference<>("init");

    public static void main(String[] args) {
        // AtomicLong
        counter.addAndGet(10);
        counter.incrementAndGet();
        counter.compareAndSet(11, 20);
        System.out.println("counter: " + counter.get());

        // AtomicBoolean
        flag.compareAndSet(false, true);
        System.out.println("flag: " + flag.get());

        // AtomicReference
        String old = config.getAndSet("updated");
        System.out.println("config old=" + old + " new=" + config.get());

        // updateAndGet / accumulateAndGet
        counter.updateAndGet(x -> x + 5);
        System.out.println("updateAndGet: " + counter.get());

        counter.accumulateAndGet(3, (x, y) -> x * y);
        System.out.println("accumulateAndGet: " + counter.get());

        // getAndUpdate
        long prev = counter.getAndUpdate(x -> x - 10);
        System.out.println("prev=" + prev + " now=" + counter.get());
    }
}
