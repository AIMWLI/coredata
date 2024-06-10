package FunctionInterface;

import java.util.ArrayList;
import java.util.List;
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

        Consumer<String> trimConsumer = s -> System.out.println(s.trim());
        Consumer<String> chainAccept = trimConsumer.andThen(s -> System.out.println(s.length()));
        chainAccept.accept("  spaced  ");

        Consumer<String> emptyString = s -> {
            if (s.isEmpty()) {
                System.out.println("empty string");
            }
        };
        emptyString.accept("");

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

        Consumer<String> logBefore = s -> System.out.println("before: " + s);
        Consumer<String> logAfter = s -> System.out.println("after: " + s);
        logBefore.andThen(s -> System.out.println("process: " + s)).andThen(logAfter).accept("data");

        Consumer<String> composed = ((Consumer<String>) s -> System.out.print("[" + s))
            .andThen(s -> System.out.println("]"))
            .andThen(s -> System.out.println("done"));
        composed.accept("composed");

        Consumer<String> prefix = s -> System.out.print("prefix:");
        Consumer<String> suffix = s -> System.out.println(":suffix");
        prefix.andThen(s -> System.out.print(s)).andThen(suffix).accept("middle");

        Consumer<String> logUpper = s -> System.out.println("upper: " + s.toUpperCase());
        Consumer<String> logLen = s -> System.out.println("len: " + s.length());
        logUpper.andThen(logLen).accept("chainTest");

        Consumer<String> printHash = s -> System.out.println("hash: " + s.hashCode());
        printHash.accept("hashTest");

        Consumer<String> printStars = s -> {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < s.length(); i++) {
                sb.append("*");
            }
            System.out.println("stars: " + sb.toString());
        };
        printStars.accept("test");
    }
}
