package FunctionInterface;

import java.util.function.Function;

/**
 * 接受一个参数，返回一个结果
 */
public class FunctionDemo {
    public static void main(String[] args) {
/*
        Function<Integer, String> function = new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) {
                return String.valueOf(integer);
            }
        };
        String result = function.apply(3);
        System.out.println(result); //3
*/

        System.out.println("----------lamda----------");
//        Function<Integer, String> function = String::valueOf;

        Function<Integer, String> function = x -> String.valueOf(x);
        System.out.println("function.apply(3) = " + function.apply(3));

        Function<Integer, Integer> function1 = x -> x + 20;
        System.out.println("function1.apply(5) = " + function1.apply(5));

        Function<Integer, Integer> multiply = x -> x * 2;
        Function<Integer, Integer> addThree = x -> x + 3;

        System.out.println("compose: " + multiply.compose(addThree).apply(5));
        System.out.println("andThen: " + multiply.andThen(addThree).apply(5));

        Function<String, Integer> strLen = String::length;
        System.out.println("strLen hello: " + strLen.apply("hello"));

        Function<Object, Object> identity = Function.identity();
        System.out.println("identity: " + identity.apply("test"));

        Function<Integer, Integer> constant = x -> 42;
        System.out.println("constant: " + constant.apply(100));
    }

}
