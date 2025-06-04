package util;

import java.util.Optional;

public class OptionalDemo {

    public static void main(String[] args) {
        Optional<String> name = Optional.of("hello");
        System.out.println(name.map(String::toUpperCase).orElse("default"));

        Optional<String> empty = Optional.empty();
        System.out.println(empty.map(String::toUpperCase).orElse("default"));
    }
}
