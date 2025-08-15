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

        System.out.println("capacity: " + sb2.capacity());
        sb2.trimToSize();
        System.out.println("after trim: " + sb2.capacity());

        StringBuilder reverseSb = new StringBuilder("abcdef");
        System.out.println("reverse: " + reverseSb.reverse().toString());

        StringBuilder search = new StringBuilder("hello world");
        System.out.println("indexOf: " + search.indexOf("world"));
        System.out.println("substring: " + search.substring(0, 5));
    }
}
