package FunctionInterface;

import java.util.function.BiFunction;

public class BiFunctionDemo {

    public static void main(String[] args) {
        BiFunction<Integer, Integer, Integer> add = (a, b) -> a + b;
        System.out.println("add: " + add.apply(3, 5));

        BiFunction<Integer, Integer, String> toString = (a, b) -> a + " + " + b;
        System.out.println("toString: " + toString.apply(3, 5));
    }
}
