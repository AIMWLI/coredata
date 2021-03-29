package algorithm;

public class StringMatch {

    public static int indexOf(String text, String pattern) {
        if (pattern.isEmpty()) return 0;
        int n = text.length(), m = pattern.length();
        for (int i = 0; i <= n - m; i++) {
            int j = 0;
            while (j < m && text.charAt(i + j) == pattern.charAt(j)) j++;
            if (j == m) return i;
        }
        return -1;
    }
}
