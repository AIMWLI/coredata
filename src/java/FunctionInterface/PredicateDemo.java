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

    }
}
