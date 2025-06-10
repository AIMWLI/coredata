package FunctionInterface;

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
    }
}
