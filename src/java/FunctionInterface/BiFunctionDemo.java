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

        BinaryOperator<Integer> min = BinaryOperator.minBy(Integer::compare);
        System.out.println("min: " + min.apply(8, 3));

        BiFunction<Integer, Integer, Integer> maxOf = (a, b) -> a >= b ? a : b;
        System.out.println("maxOf: " + maxOf.apply(10, 7));

        BiFunction<Integer, Integer, String> compare = (a, b) -> a > b ? "gt" : "lte";
        System.out.println("compare: " + compare.apply(5, 3));

        BiFunction<Integer, Integer, Integer> multiply = (a, b) -> a * b;
        System.out.println("multiply: " + multiply.apply(4, 5));

        BiFunction<Integer, Integer, Integer> subtract = (a, b) -> a - b;
        System.out.println("subtract: " + subtract.apply(10, 3));

        BiFunction<String, String, String> concat = (a, b) -> a + b;
        System.out.println("concat: " + concat.apply("a", "b"));

        BiFunction<String, String, Integer> compareTo = String::compareTo;
        System.out.println("compare a b: " + compareTo.apply("a", "b"));

        BiFunction<Integer, Integer, Boolean> isFactor = (a, b) -> b != 0 && a % b == 0;
        System.out.println("isFactor 10 2: " + isFactor.apply(10, 2));
        System.out.println("isFactor 10 3: " + isFactor.apply(10, 3));

        BiFunction<Integer, Integer, Integer> divide = (a, b) -> {
            if (b == 0) {
                return 0;
            }
            return a / b;
        };
        System.out.println("divide: " + divide.apply(10, 2));
        System.out.println("divide by 0: " + divide.apply(10, 0));

        BinaryOperator<String> stringMin = BinaryOperator.minBy(String::compareTo);
        System.out.println("stringMin: " + stringMin.apply("apple", "banana"));

        BiFunction<String, Integer, String> repeatStr = (s, n) -> {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < n; i++) {
                sb.append(s);
            }
            return sb.toString();
        };
        System.out.println("repeatStr: " + repeatStr.apply("ab", 3));
    }
}
