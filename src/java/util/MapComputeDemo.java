package util;

import java.util.HashMap;
import java.util.Map;

public class MapComputeDemo {

    public static void main(String[] args) {
        Map<String, Integer> map = new HashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);

        map.computeIfPresent("a", (k, v) -> v + 10);
        map.computeIfAbsent("d", k -> 4);
        map.merge("b", 5, (oldVal, newVal) -> oldVal + newVal);
        map.replaceAll((k, v) -> v * 2);
        map.compute("a", (k, v) -> v == null ? 0 : v + 1);
        map.merge("c", 10, Integer::sum);
        map.computeIfAbsent("e", k -> 5);
        System.out.println("e: " + map.get("e"));

        map.put("f", null);
        map.computeIfPresent("f", (k, v) -> v + 1);
        System.out.println("f: " + map.get("f"));

        map.put("g", 100);
        map.compute("g", (k, v) -> v / 2);
        System.out.println("g: " + map.get("g"));

        map.put("h", 5);
        map.merge("h", 3, Integer::sum);
        System.out.println("h: " + map.get("h"));

        map.put("i", 1);
        map.compute("i", (k, v) -> v * 10);
        System.out.println("i: " + map.get("i"));

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + "=" + entry.getValue());
        }
    }
}
