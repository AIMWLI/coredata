package algorithm;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCacheAlgo<K, V> extends LinkedHashMap<K, V> {

    private final int maxSize;

    public LRUCacheAlgo(int maxSize) {
        super(16, 0.75f, true);
        this.maxSize = maxSize;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > maxSize;
    }

    public static void main(String[] args) {
        LRUCacheAlgo<Integer, String> cache = new LRUCacheAlgo<>(3);
        cache.put(1, "one");
        cache.put(2, "two");
        cache.put(3, "three");
        cache.get(1);
        cache.put(4, "four");
        System.out.println("cache: " + cache.keySet());
    }
}
// total
public static int total() { return 0; }
