import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Test {
    public static void main(String[] args) {
        //查看hashMap底层实现
        Map<String, Object> map = new HashMap<>();
        map.put("1", 1);
        map.put("2", 2);
        map.put("3", 3);

        map.putIfAbsent("1", 100);
        map.computeIfAbsent("4", k -> Integer.valueOf(k));
        map.forEach((k, v) -> System.out.println(k + " -> " + v));

        System.out.println(map.getOrDefault("5", 0));
        map.replace("2", 20);

        Set<String> keys = map.keySet();
        for (String key : keys) {
            System.out.println("key: " + key);
        }

        Collection<Object> values = map.values();
        for (Object val : values) {
            System.out.println("val: " + val);
        }

        System.out.println("containsKey 1: " + map.containsKey("1"));
        System.out.println("containsKey 5: " + map.containsKey("5"));

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }
    }
}
