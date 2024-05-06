package lang;

/**
 * StringBuilder 线程不安全 vs StringBuffer 线程安全
 * 单线程用 StringBuilder，多线程共享用 StringBuffer
 */
public class ThreadSafeStringBufferDemo {

    public static void main(String[] args) throws InterruptedException {
        // StringBuilder 线程不安全
        StringBuilder sb = new StringBuilder();
        Thread t1 = new Thread(() -> { for (int i = 0; i < 1000; i++) sb.append("a"); });
        Thread t2 = new Thread(() -> { for (int i = 0; i < 1000; i++) sb.append("b"); });
        t1.start(); t2.start();
        t1.join(); t2.join();
        System.out.println("StringBuilder len=" + sb.length() + " (expected 2000)");

        // StringBuffer 线程安全
        StringBuffer sbf = new StringBuffer();
        Thread t3 = new Thread(() -> { for (int i = 0; i < 1000; i++) sbf.append("a"); });
        Thread t4 = new Thread(() -> { for (int i = 0; i < 1000; i++) sbf.append("b"); });
        t3.start(); t4.start();
        t3.join(); t4.join();
        System.out.println("StringBuffer len=" + sbf.length() + " (expected 2000)");

        // StringBuilder appendInt
        StringBuilder sb2 = new StringBuilder();
        sb2.append(42).append(" = ").append(Integer.toHexString(42));
        System.out.println("appendInt: " + sb2);
    }
}
