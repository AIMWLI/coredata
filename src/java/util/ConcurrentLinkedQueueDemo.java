package util;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ConcurrentLinkedQueue 非阻塞无界队列（CAS 实现）
 * 适用：高并发生产者-消费者模型，无锁高性能
 */
public class ConcurrentLinkedQueueDemo {

    private static final ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
    private static final AtomicInteger consumed = new AtomicInteger(0);

    public static void main(String[] args) throws Exception {
        ExecutorService producers = Executors.newFixedThreadPool(2);
        ExecutorService consumers = Executors.newFixedThreadPool(2);

        // 生产者
        for (int i = 0; i < 5; i++) {
            int id = i;
            producers.execute(() -> {
                queue.offer("msg-" + id);
                System.out.println("produced: msg-" + id);
            });
        }

        // 消费者
        for (int i = 0; i < 5; i++) {
            consumers.execute(() -> {
                String msg = queue.poll();
                if (msg != null) {
                    consumed.incrementAndGet();
                    System.out.println("consumed: " + msg);
                }
            });
        }

        producers.shutdown();
        consumers.shutdown();
        producers.awaitTermination(1, TimeUnit.SECONDS);
        consumers.awaitTermination(1, TimeUnit.SECONDS);

        System.out.println("consumed: " + consumed.get());
        System.out.println("queue remaining: " + queue.size());

        // peek 不移除
        queue.offer("last");
        System.out.println("peek: " + queue.peek() + " size: " + queue.size());
    }
}
