package FunctionInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * 接受一个输入参数，返回boolean类型结果
 */
public class PredicateDemo {
    public static void main(String[] args) {
/*
        Predicate<Integer> predicate = new Predicate<Integer>() {
            @Override
            public boolean test(Integer i) {
                return i > 10;
            }
        };
*/
        Predicate<Integer> predicate = x -> x > 10;
        System.out.println("predicate.test(5) = " + predicate.test(5));
        System.out.println("predicate.test(5) = " + predicate.test(11));

        Predicate<Integer> greaterThan5 = x -> x > 5;
        Predicate<Integer> lessThan15 = x -> x < 15;

        System.out.println("gt5 and lt15: " + greaterThan5.and(lessThan15).test(10));
        System.out.println("gt5 or lt15: " + greaterThan5.or(lessThan15).test(20));
        System.out.println("negate: " + greaterThan5.negate().test(3));

        Predicate<String> isEmpty = Predicate.isEqual("");
        System.out.println("isEqual empty: " + isEmpty.test(""));
        System.out.println("isEqual hello: " + isEmpty.test("hello"));

        List<Integer> nums = new ArrayList<>();
        nums.add(5);
        nums.add(12);
        nums.add(8);
        Predicate<Integer> gt10 = x -> x > 10;
        for (Integer n : nums) {
            if (gt10.test(n)) {
                System.out.println(n + " gt10");
            }
        }

        Predicate<Integer> lessThan100 = x -> x < 100;
        System.out.println("gt10 and lt100 50: " + gt10.and(lessThan100).test(50));

        Predicate<Integer> isOdd = x -> x % 2 != 0;
        System.out.println("odd 7: " + isOdd.test(7));
        System.out.println("odd 8: " + isOdd.test(8));

        Predicate<String> startsWith = s -> s.startsWith("h");
        Predicate<String> endsWith = s -> s.endsWith("o");
        System.out.println("starts h and ends o: " + startsWith.and(endsWith).test("hello"));

        Predicate<Integer> between = x -> x > 10 && x < 20;
        System.out.println("between 15: " + between.test(15));
        System.out.println("between 25: " + between.test(25));

        Predicate<Integer> even = x -> x % 2 == 0;
        Predicate<Integer> positive = x -> x > 0;
        System.out.println("even and positive 4: " + even.and(positive).test(4));
        System.out.println("even and positive -2: " + even.and(positive).test(-2));

        Predicate<String> notNull = Objects::nonNull;
        Predicate<String> notEmpty = s -> !s.isEmpty();
        Predicate<String> valid = notNull.and(notEmpty);
        System.out.println("valid hello: " + valid.test("hello"));
        System.out.println("valid null: " + valid.test(null));

        Predicate<Integer> isDivisibleBy5 = x -> x % 5 == 0;
        System.out.println("divisible by 5 10: " + isDivisibleBy5.test(10));
        System.out.println("divisible by 5 11: " + isDivisibleBy5.test(11));

        Predicate<Integer> isPrime = x -> {
            if (x < 2) {
                return false;
            }
            for (int i = 2; i * i <= x; i++) {
                if (x % i == 0) {
                    return false;
                }
            }
            return true;
        };
        System.out.println("prime 7: " + isPrime.test(7));
        System.out.println("prime 10: " + isPrime.test(10));

        Predicate<Integer> isSquare = x -> {
            int s = (int) Math.sqrt(x);
            return s * s == x;
        };
        System.out.println("square 16: " + isSquare.test(16));
        System.out.println("square 17: " + isSquare.test(17));

        Predicate<Integer> isDivisibleBy3 = x -> x % 3 == 0;
        System.out.println("divisible by 3 9: " + isDivisibleBy3.test(9));
        System.out.println("divisible by 3 10: " + isDivisibleBy3.test(10));
    }
}
