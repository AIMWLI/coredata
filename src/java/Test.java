import java.util.HashMap;
import java.util.Map;

public class Test {
    public static void main(String[] args) {
        //查看hashMap底层实现
        Map<String, Object> map = new HashMap<>();
        map.put("1", 1);
        map.put("2", 2);
        map.put("3", 3);
    }
}
