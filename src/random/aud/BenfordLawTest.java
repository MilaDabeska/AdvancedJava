package random.aud;

import java.io.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

interface NumbersReader {
    List<Integer> read(InputStream inputStream);
}

class LineNumbersReader implements NumbersReader {
    @Override
    public List<Integer> read(InputStream inputStream) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {

            return br.lines()
                    .filter(line -> !line.isEmpty())
                    .map(line -> Integer.parseInt(line.trim()))
                    .collect(Collectors.toList());

        } catch (IOException e) {
            System.out.println(e.getMessage());
            return Collections.emptyList();
        }
    }
}

class CountVisualizer {
    private final int n;

    public CountVisualizer(int n) {
        this.n = n;
    }

    public void visualize(OutputStream out, int[] counts) {
        PrintWriter pw = new PrintWriter(out);
        for (Integer count : counts) {
            while (count > 0) {
                pw.write("*");
                count -= n;
            }
            pw.write("\n");
        }
        pw.flush();
    }
}

public class BenfordLawTest {
    public static String INPUT_FILE = "C:\\Users\\Danilo\\Desktop\\napredno\\src\\random\\aud\\library_books.txt";

    public static int firstDigit(int num) {
        while (num >= 10) {
            num /= 10;
        }
        return num;
    }

    public static int[] counts(List<Integer> numbers) {
        int[] result = new int[10];
        for (Integer number : numbers) {
            int digit = firstDigit(number);
            result[digit]++;
        }
        return result;
    }

    public static int[] countsFunctional(List<Integer> numbers) {
        return numbers.stream()
                .map(BenfordLawTest::firstDigit)
                .map(x->{
                    int[] result=new int[10];
                    result[x]++;
                    return result;
                }).reduce(new int[10],(left,right)->{ //left -> prethoden rezultat , right -> tekoven element
                    Arrays.setAll(left,i->left[i]+right[i]);
                    return left;
                });
    }

    public static void main(String[] args) {
        NumbersReader numbersReader = new LineNumbersReader();
        try {
            List<Integer> numbers = numbersReader.read(new FileInputStream(INPUT_FILE));
            CountVisualizer visualizer = new CountVisualizer(1);
            visualizer.visualize(System.out, counts(numbers));
            visualizer.visualize(System.out, countsFunctional(numbers));
        } catch (FileNotFoundException f) {
            System.out.println(f.getMessage());
        }
    }
}
