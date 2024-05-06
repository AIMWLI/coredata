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

        Function<String, String> trimFunc = String::trim;
        Function<String, String> upperFunc = String::toUpperCase;
        System.out.println("trim upper: " + trimFunc.andThen(upperFunc).apply("  hello  "));

        Function<String, Integer> parseIntFunc = Integer::parseInt;
        System.out.println("parseInt: " + parseIntFunc.apply("42"));

        Function<String, String> concatStr = a -> a + a;
        System.out.println("concatStr: " + concatStr.apply("ab"));

        Function<String, Character> firstChar = s -> s.charAt(0);
        System.out.println("firstChar: " + firstChar.apply("hello"));

        Function<String, Integer> strLen = String::length;
        System.out.println("strLen hello: " + strLen.apply("hello"));

        Function<Object, Object> identity = Function.identity();
        System.out.println("identity: " + identity.apply("test"));

        Function<Integer, Integer> constant = x -> 42;
        System.out.println("constant: " + constant.apply(100));

        Function<Integer, Integer> add1 = x -> x + 1;
        Function<Integer, Integer> mul2 = x -> x * 2;
        System.out.println("add1 then mul2: " + add1.andThen(mul2).apply(3));
        System.out.println("mul2 then add1: " + mul2.andThen(add1).apply(3));

        Function<String, String> substring = s -> s.substring(0, Math.min(s.length(), 3));
        System.out.println("substring: " + substring.apply("hello"));

        Function<String, String> toUpperCase = String::toUpperCase;
        System.out.println("upper: " + toUpperCase.apply("hello"));

        Function<String, String> reverseStr = s -> new StringBuilder(s).reverse().toString();
        System.out.println("reverse: " + reverseStr.apply("abc"));

        Function<String, String> removeSpace = s -> s.replaceAll("\\s+", "");
        System.out.println("removeSpace: " + removeSpace.apply("a b c"));

        Function<Integer, String> grade = x -> {
            if (x >= 90) {
                return "A";
            }
            if (x >= 80) {
                return "B";
            }
            if (x >= 70) {
                return "C";
            }
            return "D";
        };
        System.out.println("grade 85: " + grade.apply(85));
        System.out.println("grade 60: " + grade.apply(60));

        Function<String, String> appendDot = s -> s + ".";
        System.out.println("appendDot: " + appendDot.apply("end"));

        Function<String, String> trimThenUpper = ((Function<String, String>) String::trim).andThen(String::toUpperCase);
        System.out.println("trimUpper: " + trimThenUpper.apply("  test  "));

        Function<Integer, Integer> square = x -> x * x;
        System.out.println("square 6: " + square.apply(6));

        Function<Integer, String> binaryStr = Integer::toBinaryString;
        System.out.println("binary 42: " + binaryStr.apply(42));
    }

}
