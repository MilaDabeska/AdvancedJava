package lists.labs;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class ArrayIndexOutOfBoundsException extends Exception {
    public ArrayIndexOutOfBoundsException() {
    }
}

class IntegerList {

    private List<Integer> list;

    public IntegerList() {
        list = new ArrayList<>();
    }

    public IntegerList(Integer... numbers) {
        // конструктор коj креира листа што ги содржи елементите numbers во истиот
        // редослед во кој тие се појавуваат во низата
        list = new ArrayList<>(Arrays.asList(numbers));
    }

    public void add(int el, int idx) {
        //Доколку idx е поголемо од сегашната големина на листата ја зголемуваме листата и
        // сите нови елементи ги иницијалираме на нула (освен тој на позиција idx кој го поставуваме на el).
        if (idx > list.size()) {
//            for (int i = list.size(); i < idx; i++) {
//                list.add(0);
//            }
            IntStream.range(list.size(), idx)
                    .forEach(i -> list.add(0));
        }
        list.add(idx, el);
    }

    public void remove(int idx) throws ArrayIndexOutOfBoundsException {
        if (idx < 0 || idx >= list.size()) throw new ArrayIndexOutOfBoundsException();
        list.remove(idx);
    }

    public void set(int el, int idx) throws ArrayIndexOutOfBoundsException {
        if (idx < 0 || idx >= list.size()) throw new ArrayIndexOutOfBoundsException();
        list.set(el, idx);
    }

    public int get(int idx) throws ArrayIndexOutOfBoundsException {
        if (idx < 0 || idx >= list.size()) throw new ArrayIndexOutOfBoundsException();
        return list.get(idx);
    }

    public int size() {
        return list.size();
    }

    public int count(int el) {
//        return Collections.frequency(list,el);

//        long count = 0L;
//        for (Integer integer : list) {
//            if (integer.equals(el)) {
//                count++;
//            }
//        }
//        return (int) count;

        return (int) list.stream()
                .filter(integer -> integer.equals(el))
                .count();
    }

    public void removeDuplicates() {
        //врши отстранување на дупликат елементите од листата.
        // Доколку некој елемент се сретнува повеќе пати во листата ја оставаме само
        // последната копија од него
        Collections.reverse(list);
//        List<Integer> result = new ArrayList<>();
//        Set<Integer> uniqueValues = new HashSet<>();
//        for (Integer integer : list) {
//            if (uniqueValues.add(integer)) {
//                result.add(integer);
//            }
//        }
//        list= result;
        list = list.stream().distinct().collect(Collectors.toList());
        Collections.reverse(list);
    }

    public int sumFirst(int k) {
        //ја дава сумата на последните k елементи
//        int sum = 0;
//        long limit = k;
//        for (Integer integer : list) {
//            if (limit-- == 0) break;
//            int i = integer;
//            sum += i;
//        }
//        return sum;
        return list.stream().limit(k).mapToInt(integer -> integer).sum();
    }

    public int sumLast(int k) {
//        int sum = 0;
//        long toSkip = list.size() - k;
//        for (Integer i : list) {
//            if (toSkip > 0) {
//                toSkip--;
//                continue;
//            }
//            int i1 = i;
//            sum += i1;
//        }
//        return sum;
        return list.stream().skip(list.size() - k).mapToInt(i -> i).sum();
    }

    public void shiftRight(int idx, int k) {
        //го поместува елементот на позиција idx за k места во десно.
        // При поместувањето листата ја третираме како да е кружна.
        int nexIdx = (idx + k) % size();
        int num = list.remove(idx);
        list.add(nexIdx, num);
    }

    public void shiftLeft(int idx, int k) {
        //аналогно на shiftRight
        int newIdx = idx - (k % size());
        if (newIdx < 0) newIdx += size();
        int el = list.remove(idx);
        list.add(newIdx, el);
    }

    public IntegerList addValue(int value) {
//        List<Integer> result = new ArrayList<>();
//        for (Integer i : list) {
//            Integer integer = i + value;
//            result.add(integer);
//        }
//        return new IntegerList(result.toArray(new Integer[0]));
        return new IntegerList(list.stream().map(i -> i + value).toArray(Integer[]::new));
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
