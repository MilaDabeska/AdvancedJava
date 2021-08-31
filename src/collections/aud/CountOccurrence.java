package collections.aud;

import java.util.Collection;

public class CountOccurrence {
    public static int count(Collection<Collection<String>> c, String str) {
        int count = 0;
        for (Collection<String> subc : c) {
            for (String s : subc) {
                if (s.equalsIgnoreCase(str))
                    count++;
            }
        }
        return count;
    }

    public static int countFunctional(Collection<Collection<String>> c, String str) {
        return (int) c.stream().flatMap(sub -> sub.stream())
                .filter(s -> s.equalsIgnoreCase(str))
                .count();

//        return c.stream().flatMapToInt(sub -> sub.stream()
//                .mapToInt(s -> s.equalsIgnoreCase(str) ? 1 : 0))
//                .sum();
    }

    public static void main(String[] args) {

    }
}
