package lists.aud;

import java.io.*;
import java.util.Scanner;
import java.util.function.Consumer;

class FileConsumer implements Consumer<String> { //string -> edna linija , Consumer se koristi za .forEach()

    int lines = 0, words = 0, characters = 0;

    @Override
    public void accept(String s) {
        lines++;
        words += s.split("\\s+").length;
        characters += s.length();
    }

    @Override
    public String toString() {
        return String.format("Lines: %d, Words: %d, Characters: %d", lines, words, characters);
    }
}

class LineCounter {

    int lines = 0, words = 0, characters = 0;

    public LineCounter(int lines, int words, int characters) {
        this.lines = lines;
        this.words = words;
        this.characters = characters;
    }

    public LineCounter(String line) {
        this.lines = 1;
        this.words = line.split("\\s+").length;
        this.characters = line.length();
    }

    public LineCounter add(LineCounter c) { //suma na 2 counteri
        return new LineCounter(lines + c.lines, words + c.words, characters + c.characters);
    }

    @Override
    public String toString() {
        return String.format("Lines: %d, Words: %d, Characters: %d\n", lines, words, characters);
    }
}


class WordCounter {

    public static void bufferedReaderAndMapReduceReading(InputStream in) {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        LineCounter result = br.lines().map(line -> new LineCounter(line)).reduce(
                new LineCounter(0, 0, 0), //konstruktor
//                (left,right)->{   // 2 vlezni argumenti (oznacuva kako da sobereme 2 elementi)
//                    return left.add(right);
//                }
                (left, right) -> left.add(right) //lambda
        );
        System.out.println(result);
    }

    public static void bufferedReaderAndConsumerReading(InputStream in) {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        FileConsumer fileConsumer = new FileConsumer();
        br.lines().forEach(fileConsumer);
        System.out.println(fileConsumer);
    }

    public static void bufferedReaderReading(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        int lines = 0, words = 0, characters = 0;

        String line;
        while ((line = br.readLine()) != null) {
            lines++;
            words += line.split("\\s+").length;
            characters += line.length();
        }
        br.close();
        System.out.printf("Lines: %d, Words: %d, Characters: %d\n", lines, words, characters);
    }

    public static void scannerReading(InputStream in) {
        Scanner scanner = new Scanner(in);
        int lines = 0, words = 0, characters = 0;

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            lines++;
            words += line.split("\\s+").length;
            characters += line.length();
        }
        scanner.close();
        System.out.printf("Lines: %d, Words: %d, Characters: %d\n", lines, words, characters);
    }
}

public class WordCount {
    public static void main(String[] args) {
        File file = new File("C:\\Users\\Danilo\\Desktop\\napredno\\src\\lists\\aud\\data");

        try {
            WordCounter.scannerReading(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            WordCounter.bufferedReaderReading(new FileInputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            WordCounter.bufferedReaderAndConsumerReading(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            WordCounter.bufferedReaderAndMapReduceReading(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
