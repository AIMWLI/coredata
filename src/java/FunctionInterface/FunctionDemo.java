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

    }

}
