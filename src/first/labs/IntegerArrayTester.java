package first.labs;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;


final class IntegerArray {

    private final int[] a;

    public IntegerArray(int[] a) {
        this.a = Arrays.copyOf(a, a.length);
    }

    public int length() {
        return a.length;
    }

    public int getElementAt(int i) {
        // го враќа елементот на позиција i,
        // може да претпоставите дека индекост i секогаш ќе има вредност во интервалот
        return a[i];
    }

    public int sum() {
        int sum = 0;
        for (int i : a) {
            sum += i;
        }
        return sum;
    }

    public double average() {
        return (double) sum() / length();
    }

    public IntegerArray getSorted() {
        //враќа нов објект од истата класа кој ги содржи истите вредности од тековниот објект,
        // но сортирани во растечки редослед
        int[] sort = Arrays.copyOf(a, a.length); //ja kopira
        Arrays.sort(sort);
        IntegerArray sorted = new IntegerArray(sort);
        return sorted;
    }

    public IntegerArray concat(IntegerArray ia) {
        //враќа нов објект од истата класа во кој се содржат сите елементи од this објектот и по
        // нив сите елементи од ia објектот притоа запазувајќи го нивниот редослед
        int[] array = new int[this.length() + ia.length()];

        for (int i = 0; i < this.length(); i++) {
            array[i] = this.a[i];
        }

        for (int i = this.length(); i < array.length; i++) {
            array[i] = ia.getElementAt(i - this.length());
        }
        IntegerArray array1 = new IntegerArray(array);
        return array1;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < this.length() - 1; i++) {
            sb.append(a[i]).append(", ");
        }
        sb.append(a[this.length() - 1]).append("]");
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (getClass() != o.getClass()) return false;

        IntegerArray other = (IntegerArray) o;
        if (this.length() != ((IntegerArray) o).length()) return false;
        for (int i = 0; i < length(); i++) {
            if (a[i] != ((IntegerArray) o).getElementAt(i)) return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(a);
    }
}

class ArrayReader {

    public static IntegerArray readIntegerArray(InputStream input) {
        Scanner scanner = new Scanner(input);
        int n = scanner.nextInt();
        int[] array = new int[n];
        for (int i = 0; i < n; i++) {
            array[i] = scanner.nextInt();
        }
        IntegerArray integerArray = new IntegerArray(array);
        return integerArray;
    }

}

public class IntegerArrayTester {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        IntegerArray ia = null;
        switch (s) {
            case "testSimpleMethods":
                ia = new IntegerArray(generateRandomArray(scanner.nextInt()));
                testSimpleMethods(ia);
                break;
            case "testConcat":
                testConcat(scanner);
                break;
            case "testEquals":
                testEquals(scanner);
                break;
            case "testSorting":
                testSorting(scanner);
                break;
            case "testReading":
                testReading(new ByteArrayInputStream(scanner.nextLine().getBytes()));
                break;
            case "testImmutability":
                int a[] = generateRandomArray(scanner.nextInt());
                ia = new IntegerArray(a);
                testSimpleMethods(ia);
                testSimpleMethods(ia);
                IntegerArray sorted_ia = ia.getSorted();
                testSimpleMethods(ia);
                testSimpleMethods(sorted_ia);
                sorted_ia.getSorted();
                testSimpleMethods(sorted_ia);
                testSimpleMethods(ia);
                a[0] += 2;
                testSimpleMethods(ia);
                ia = ArrayReader.readIntegerArray(new ByteArrayInputStream(integerArrayToString(ia).getBytes()));
                testSimpleMethods(ia);
                break;
        }
        scanner.close();
    }

    static void testReading(InputStream in) {
        IntegerArray read = ArrayReader.readIntegerArray(in);
        System.out.println(read);
    }

    static void testSorting(Scanner scanner) {
        int[] a = readArray(scanner);
        IntegerArray ia = new IntegerArray(a);
        System.out.println(ia.getSorted());
    }

    static void testEquals(Scanner scanner) {
        int[] a = readArray(scanner);
        int[] b = readArray(scanner);
        int[] c = readArray(scanner);
        IntegerArray ia = new IntegerArray(a);
        IntegerArray ib = new IntegerArray(b);
        IntegerArray ic = new IntegerArray(c);
        System.out.println(ia.equals(ib));
        System.out.println(ia.equals(ic));
        System.out.println(ib.equals(ic));
    }

    static void testConcat(Scanner scanner) {
        int[] a = readArray(scanner);
        int[] b = readArray(scanner);
        IntegerArray array1 = new IntegerArray(a);
        IntegerArray array2 = new IntegerArray(b);
        IntegerArray concatenated = array1.concat(array2);
        System.out.println(concatenated);
    }

    static void testSimpleMethods(IntegerArray ia) {
        System.out.print(integerArrayToString(ia));
        System.out.println(ia);
        System.out.println(ia.sum());
        System.out.printf("%.2f\n", ia.average());
    }


    static String integerArrayToString(IntegerArray ia) {
        StringBuilder sb = new StringBuilder();
        sb.append(ia.length()).append('\n');
        for (int i = 0; i < ia.length(); ++i)
            sb.append(ia.getElementAt(i)).append(' ');
        sb.append('\n');
        return sb.toString();
    }

    static int[] readArray(Scanner scanner) {
        int n = scanner.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; ++i) {
            a[i] = scanner.nextInt();
        }
        return a;
    }


    static int[] generateRandomArray(int k) {
        Random rnd = new Random(k);
        int n = rnd.nextInt(8) + 2;
        int a[] = new int[n];
        for (int i = 0; i < n; ++i) {
            a[i] = rnd.nextInt(20) - 5;
        }
        return a;
    }

}

