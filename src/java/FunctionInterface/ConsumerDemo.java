package FunctionInterface;

import java.util.Scanner;
import java.util.function.Consumer;

/**
 * 接受一个输入参数并且无返回的操作
 */
public class ConsumerDemo {
    public static void main(String[] args) {
/*
        Consumer<String> consumer = new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println(s);
            }
        };
        consumer.accept("i am input");
*/
        while (true) {
            Consumer<String> consumer = System.out::println;
            Scanner scanner = new Scanner(System.in);
            consumer.accept(scanner.nextLine());
        }
    }
}
