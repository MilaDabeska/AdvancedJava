package generics.labs;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class ArrayIndexOutOfBoundsException extends Exception {
    public ArrayIndexOutOfBoundsException() {
    }
}

class IntegerList {
    private List<Integer> integers;

    public IntegerList() {
        integers = new ArrayList<>();
    }

    public IntegerList(Integer... numbers) {
        integers = new ArrayList<>(Arrays.asList(numbers));
    }

    public void add(int el, int idx) {
        if (idx > integers.size()) {
            int length = idx - integers.size();
            IntStream.range(0, length).forEach(i -> integers.add(0));
            integers.add(el);
        } else {
            integers.add(idx, el);
        }
    }

    public void remove(int idx) throws ArrayIndexOutOfBoundsException {
        if (idx < 0 || idx >= integers.size()) {
            throw new ArrayIndexOutOfBoundsException();
        }
        integers.remove(idx);
    }

    public void set(int el, int idx) throws ArrayIndexOutOfBoundsException {
        if (idx < 0 || idx >= integers.size()) {
            throw new ArrayIndexOutOfBoundsException();
        }
        integers.set(el, idx);
    }

    public int get(int idx) throws ArrayIndexOutOfBoundsException {
        if (idx < 0 || idx >= integers.size()) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return integers.get(idx);
    }

    public int size() {
        return integers.size();
    }

    public int count(int el) {
//        int count = 0;
//        for (Integer integer : integers) {
//            if (integer.equals(el))
//                count++;
//        }
//        return count;
        return (int) integers.stream().filter(integer -> integer.equals(el)).count();
    }

    public void removeDuplicates() {
        Collections.reverse(integers);
//        ArrayList<Integer> result = new ArrayList<>();
//        Set<Integer> uniqueValues = new HashSet<>();
//        for (Integer integer : integers) {
//            if (uniqueValues.add(integer)) {
//                result.add(integer);
//            }
//        }
//        integers= result;
        integers = integers.stream().distinct().collect(Collectors.toCollection(() -> new ArrayList<Integer>()));
        Collections.reverse(integers);
    }

    public int sumFirst(int k) {
//        int sum = 0;
//        long limit = k;
//        for (Integer i : integers) {
//            if (limit-- == 0) break;
//            int i1 = i;
//            sum += i1;
//        }
//        return sum;
        return integers.stream().limit(k).mapToInt(i -> i).sum();
    }

    public int sumLast(int k) {
//        int sum = 0;
//        long toSkip = integers.size() - k;
//        for (Integer i : integers) {
//            if (toSkip > 0) {
//                toSkip--;
//                continue;
//            }
//            int i1 = i;
//            sum += i1;
//        }
//        return sum;
        return integers.stream().skip(integers.size() - k).mapToInt(i -> i).sum();
    }

    public void shiftRight(int idx, int k) {
        //го поместува елементот на позиција idx за k места во десно
        int newIdx = (idx + k) % integers.size();
        int number = integers.remove(idx);
        integers.add(newIdx, number);
    }

    public void shiftLeft(int idx, int k) {
        int size = integers.size();
        int number = integers.remove(idx);
        if (k >= size) {
            k -= size * (k / size);
        }
        int newPosition = (idx + size - k) % size;
        integers.add(newPosition, number);
    }

    public IntegerList addValue(int value) {
//        List<Integer> result=new ArrayList<>();
//        for (int i=0;i< integers.size();i++){
//            int integer=i+value;
//            result.add(integer);
//        }
//        return new IntegerList(result.toArray(new Integer[0]));

//        return new IntegerList(IntStream.range(0, integers.size()).map(i -> i + value).boxed().toArray(Integer[]::new));

//        List<Integer> list = new ArrayList<>();
//        for (Integer i : integers) {
//            Integer integer = i + value;
//            list.add(integer);
//        }
//        return new IntegerList(list.toArray(new Integer[0]));

        return new IntegerList(integers.stream().map(i -> i + value).toArray(Integer[]::new));
    }
}

public class IntegerListTest {

    public static void main(String[] args) throws ArrayIndexOutOfBoundsException {
        Scanner jin = new Scanner(System.in);
        int k = jin.nextInt();
        if (k == 0) { //test standard methods
            int subtest = jin.nextInt();
            if (subtest == 0) {
                IntegerList list = new IntegerList();
                while (true) {
                    int num = jin.nextInt();
                    if (num == 0) {
                        list.add(jin.nextInt(), jin.nextInt());
                    }
                    if (num == 1) {
                        list.remove(jin.nextInt());
                    }
                    if (num == 2) {
                        print(list);
                    }
                    if (num == 3) {
                        break;
                    }
                }
            }
            if (subtest == 1) {
                int n = jin.nextInt();
                Integer a[] = new Integer[n];
                for (int i = 0; i < n; ++i) {
                    a[i] = jin.nextInt();
                }
                IntegerList list = new IntegerList(a);
                print(list);
            }
        }
        if (k == 1) { //test count,remove duplicates, addValue
            int n = jin.nextInt();
            Integer a[] = new Integer[n];
            for (int i = 0; i < n; ++i) {
                a[i] = jin.nextInt();
            }
            IntegerList list = new IntegerList(a);
            while (true) {
                int num = jin.nextInt();
                if (num == 0) { //count
                    System.out.println(list.count(jin.nextInt()));
                }
                if (num == 1) {
                    list.removeDuplicates();
                }
                if (num == 2) {
                    print(list.addValue(jin.nextInt()));
                }
                if (num == 3) {
                    list.add(jin.nextInt(), jin.nextInt());
                }
                if (num == 4) {
                    print(list);
                }
                if (num == 5) {
                    break;
                }
            }
        }
        if (k == 2) { //test shiftRight, shiftLeft, sumFirst , sumLast
            int n = jin.nextInt();
            Integer a[] = new Integer[n];
            for (int i = 0; i < n; ++i) {
                a[i] = jin.nextInt();
            }
            IntegerList list = new IntegerList(a);
            while (true) {
                int num = jin.nextInt();
                if (num == 0) { //count
                    list.shiftLeft(jin.nextInt(), jin.nextInt());
                }
                if (num == 1) {
                    list.shiftRight(jin.nextInt(), jin.nextInt());
                }
                if (num == 2) {
                    System.out.println(list.sumFirst(jin.nextInt()));
                }
                if (num == 3) {
                    System.out.println(list.sumLast(jin.nextInt()));
                }
                if (num == 4) {
                    print(list);
                }
                if (num == 5) {
                    break;
                }
            }
        }
    }

    public static void print(IntegerList il) throws ArrayIndexOutOfBoundsException {
        if (il.size() == 0) System.out.print("EMPTY");
        for (int i = 0; i < il.size(); ++i) {
            if (i > 0) System.out.print(" ");
            System.out.print(il.get(i));
        }
        System.out.println();
    }

}
