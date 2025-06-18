package util;

import java.util.Optional;

public class OptionalDemo {

    public static void main(String[] args) {
        Optional<String> name = Optional.of("hello");
        System.out.println(name.map(String::toUpperCase).orElse("default"));

        Optional<String> empty = Optional.empty();
        System.out.println(empty.map(String::toUpperCase).orElse("default"));

        Optional<String> filtered = Optional.of("hello");
        filtered.filter(s -> s.length() > 3)
                .ifPresent(s -> System.out.println("filtered: " + s));

        Optional<String> noValue = Optional.empty();
        System.out.println("orElseGet: " + noValue.orElseGet(() -> "computed"));
    }
}
