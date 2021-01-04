package ThreadPool;

import java.util.concurrent.*;

public class ThreadPoolTimedDemo {

    public static void main(String[] args) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);

        scheduler.schedule(() -> {
            System.out.println("delayed task at " + System.currentTimeMillis());
        }, 200, TimeUnit.MILLISECONDS);

        scheduler.scheduleAtFixedRate(() -> {
            System.out.println("periodic task on " + Thread.currentThread().getName());
        }, 0, 500, TimeUnit.MILLISECONDS);

        try {
            Thread.sleep(1200);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        scheduler.shutdown();
    }
}
