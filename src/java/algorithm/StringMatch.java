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

    public static int lastIndexOf(String text, String pattern) {
        if (pattern.isEmpty()) return text.length() - 1;
        for (int i = text.length() - pattern.length(); i >= 0; i--) {
            int j = 0;
            while (j < pattern.length() && text.charAt(i + j) == pattern.charAt(j)) j++;
            if (j == pattern.length()) return i;
        }
        return -1;
    }
}

// calc
public static int add(int a, int b) { return a + b; }
