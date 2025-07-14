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

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + "=" + entry.getValue());
        }
    }
}
