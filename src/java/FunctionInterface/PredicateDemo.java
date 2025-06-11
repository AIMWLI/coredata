package FunctionInterface;

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
    }
}
