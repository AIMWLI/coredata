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

        Optional<String> mapped = Optional.of("  hello  ");
        Optional<String> trimmed = mapped.map(String::trim);
        trimmed.ifPresent(s -> System.out.println("trimmed: " + s));

        Optional<String> optionalMap = Optional.of("test");
        optionalMap.map(String::toUpperCase).ifPresent(s -> System.out.println("mapped: " + s));

        Optional<String> optFlat = Optional.of("hello");
        optFlat.flatMap(s -> Optional.of(s.toUpperCase())).ifPresent(s -> System.out.println("flatMap: " + s));

        Optional<String> optIf = Optional.of("hello");
        optIf.filter(s -> s.contains("ell")).ifPresent(s -> System.out.println("contains: " + s));

        Optional<String> ofNullable = Optional.ofNullable(null);
        System.out.println("ofNullable: " + ofNullable.orElse("default"));

        Optional<Integer> optInt = Optional.of(42);
        optInt.filter(v -> v > 10).ifPresent(v -> System.out.println("optInt: " + v));

        Optional<String> filteredOpt = Optional.of("abc");
        filteredOpt.filter(s -> s.length() == 3).ifPresent(s -> System.out.println("filtered: " + s));

        Optional<String> throwIfEmpty = Optional.empty();
        try {
            throwIfEmpty.orElseThrow(() -> new RuntimeException("no value"));
        } catch (RuntimeException e) {
            System.out.println("caught: " + e.getMessage());
        }
    }
}
