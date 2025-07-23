package lang;

public class StringBuilderDemo {

    public static void main(String[] args) {
        String[] words = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j"};

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            sb.append(words[i]);
            if (i < words.length - 1) {
                sb.append(", ");
            }
        }
        System.out.println(sb.toString());

        StringBuilder reverse = new StringBuilder();
        for (int i = words.length - 1; i >= 0; i--) {
            reverse.append(words[i]);
        }
        System.out.println(reverse.toString());

        StringBuilder sb2 = new StringBuilder("hello world");
        sb2.insert(5, " java");
        System.out.println(sb2.toString());

        sb2.delete(5, 10);
        System.out.println(sb2.toString());
    }
}
