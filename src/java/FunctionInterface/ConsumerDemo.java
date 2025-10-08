package FunctionInterface;

import java.util.ArrayList;
import java.util.List;
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
        Consumer<String> printUpperCase = s -> System.out.println(s.toUpperCase());
        Consumer<String> printLength = s -> System.out.println(s.length());

        Consumer<String> pipeline = printUpperCase.andThen(printLength);
        pipeline.accept("functional");

        List<String> items = new ArrayList<>();
        items.add("x");
        items.add("y");
        Consumer<String> printItem = System.out::println;
        for (String item : items) {
            printItem.accept(item);
        }

        Consumer<String> safePrint = s -> {
            if (s != null) {
                System.out.println("safe: " + s);
            }
        };
        safePrint.accept("not null");

        Consumer<String> wrapWithBrackets = s -> System.out.println("[" + s + "]");
        Consumer<String> log = s -> System.out.println("log: " + s);
        wrapWithBrackets.andThen(log).accept("wrapped");

        Consumer<Integer> printInt = System.out::println;
        printInt.accept(42);

        while (true) {
            Consumer<String> consumer = System.out::println;
            Scanner scanner = new Scanner(System.in);
            consumer.accept(scanner.nextLine());
        }
    }
}
