package lists.aud;

import java.io.*;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PalindromeTest {

    public static boolean isPalindrome(String word) {
        String reverseWord = new StringBuilder().append(word).reverse().toString();
        return word.equals(reverseWord);
    }

    public static boolean isPalindromeBasic(String word) {
        for (int i = 0; i < word.length() / 2; i++) {
            if (word.charAt(i) != word.charAt(word.length() - i - 1)) return false;
        }
        return true;
    }

    public static boolean isPalindromeStream(String word) {
        return IntStream.range(0, word.length())
                .allMatch(i -> word.charAt(i) == word.charAt(word.length() - i - 1));
    }

    public static List<String> readWords(InputStream in) {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        return br.lines()
                .filter(word -> isPalindrome(word))
                .map(word -> word.toLowerCase())
                .collect(Collectors.toList());
    }


    public static void main(String[] args) {
        File file = new File("C:\\Users\\Danilo\\Desktop\\napredno\\src\\lists\\aud\\words.txt");

        try {
            List<String> words = readWords(new FileInputStream(file));

            String maxLength = words.get(0);

            for (String word : words) {
                if (word.length() > maxLength.length())
                    maxLength = word;
            }
            System.out.println(maxLength);

            maxLength = words.stream()
                    .max(Comparator.comparingInt(w -> w.length()))
                    .get();
            System.out.println(maxLength);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
