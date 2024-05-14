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

        StringBuilder replace = new StringBuilder("abcdef");
        replace.replace(1, 3, "XYZ");
        System.out.println("replace: " + replace.toString());

        StringBuilder chars = new StringBuilder("abc");
        chars.setCharAt(1, 'B');
        System.out.println("setCharAt: " + chars.toString());

        StringBuilder appendChain = new StringBuilder();
        appendChain.append("a").append("b").append("c");
        System.out.println("appendChain: " + appendChain.toString());

        System.out.println("length: " + appendChain.length());
        System.out.println("charAt 1: " + appendChain.charAt(1));

        StringBuilder emptyBuilder = new StringBuilder();
        System.out.println("empty capacity: " + emptyBuilder.capacity());
        emptyBuilder.ensureCapacity(100);
        System.out.println("after ensure: " + emptyBuilder.capacity());

        StringBuilder src = new StringBuilder("abcd");
        char[] dst = new char[2];
        src.getChars(0, 2, dst, 0);
        System.out.println("getChars: " + new String(dst));

        StringBuilder lastIdx = new StringBuilder("hello world hello");
        System.out.println("lastIndexOf hello: " + lastIdx.lastIndexOf("hello"));

        StringBuilder sb3 = new StringBuilder(50);
        System.out.println("initial capacity: " + sb3.capacity());

        StringBuilder del = new StringBuilder("hello world");
        del.deleteCharAt(0);
        System.out.println("delCharAt: " + del.toString());

        StringBuilder ins = new StringBuilder("hello");
        ins.insert(1, "xxx");
        System.out.println("insert: " + ins.toString());

        StringBuilder code = new StringBuilder();
        code.append("public").append(" class").append(" Test");
        System.out.println("code: " + code.toString());

        StringBuilder sub = new StringBuilder("hello world");
        String subStr = sub.substring(3, 8);
        System.out.println("substr: " + subStr);

        StringBuilder cap = new StringBuilder(10);
        System.out.println("cap init: " + cap.capacity());
        for (int i = 0; i < 5; i++) {
            cap.append("data");
        }
        System.out.println("cap after: " + cap.capacity());
    }
}
