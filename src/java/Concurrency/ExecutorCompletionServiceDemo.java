package Concurrency;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ExecutorCompletionService: 批量异步任务，谁先完成谁先返回
 * 适用：并行查询多个外部服务，取最快结果
 */
public class ExecutorCompletionServiceDemo {

    public static void main(String[] args) throws Exception {
        ExecutorService pool = Executors.newFixedThreadPool(3);
        CompletionService<String> cs = new ExecutorCompletionService<>(pool);

        for (int i = 0; i < 5; i++) {
            int taskId = i;
            cs.submit(() -> {
                long delay = (long) (Math.random() * 200);
                Thread.sleep(delay);
                return "task" + taskId + " done in " + delay + "ms";
            });
        }

        for (int i = 0; i < 5; i++) {
            Future<String> f = cs.poll(1, TimeUnit.SECONDS);
            if (f != null) {
                System.out.println("completed: " + f.get());
            }
        }

        pool.shutdown();
    }
}

// next
public static void next() {}
