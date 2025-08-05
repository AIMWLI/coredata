package FunctionInterface;

import java.util.function.BiFunction;
import java.util.function.BinaryOperator;

public class BiFunctionDemo {

    public static void main(String[] args) {
        BiFunction<Integer, Integer, Integer> add = (a, b) -> a + b;
        System.out.println("add: " + add.apply(3, 5));

        BiFunction<Integer, Integer, String> toString = (a, b) -> a + " + " + b;
        System.out.println("toString: " + toString.apply(3, 5));

        BiFunction<Integer, Integer, Integer> max = (a, b) -> a > b ? a : b;
        System.out.println("max: " + max.apply(7, 3));

        BinaryOperator<Integer> sum = (a, b) -> a + b;
        System.out.println("binaryOperator: " + sum.apply(10, 20));
    }
}
