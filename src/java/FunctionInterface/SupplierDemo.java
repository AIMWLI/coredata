package FunctionInterface;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

/**
 * 无参数， 返回一个结果
 */
public class SupplierDemo {
    public static void main(String[] args) {
/*
        Supplier<String> supplier = new Supplier<String>() {
            @Override
            public String get() {
                return "供给型函数接口，无参数，有返回";
            }
        };
*/
        Supplier<String> supplier = () -> UUID.randomUUID().toString().replaceAll("-", "");
        System.out.println("supplier.get() = " + supplier.get());
        System.out.println("supplier.get() = " + supplier.get());

        Supplier<Long> timeSupplier = () -> System.currentTimeMillis();
        System.out.println("timeSupplier.get() = " + timeSupplier.get());

        Supplier<Double> randomSupplier = () -> Math.random();
        System.out.println("randomSupplier.get() = " + randomSupplier.get());

        Supplier<String> propertySupplier = () -> System.getProperty("java.version");
        System.out.println("java.version: " + propertySupplier.get());

        Supplier<Integer> hashCodeSupplier = () -> "hello".hashCode();
        System.out.println("hashCode: " + hashCodeSupplier.get());

        Supplier<String> osNameSupplier = () -> System.getProperty("os.name");
        System.out.println("os.name: " + osNameSupplier.get());

        Supplier<Long> maxLongSupplier = () -> Long.MAX_VALUE;
        System.out.println("maxLong: " + maxLongSupplier.get());

        Supplier<String> upperSupplier = () -> "hello".toUpperCase();
        System.out.println("upper: " + upperSupplier.get());

        Supplier<String> threadNameSupplier = () -> Thread.currentThread().getName();
        System.out.println("thread: " + threadNameSupplier.get());

        Supplier<String> userDirSupplier = () -> System.getProperty("user.dir");
        System.out.println("user.dir: " + userDirSupplier.get());

        Supplier<String> defaultSupplier = () -> "default value";
        String result = Optional.ofNullable((String) null).orElseGet(defaultSupplier);
        System.out.println("orElseGet: " + result);
    }
}
