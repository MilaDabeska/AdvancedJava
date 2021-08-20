package lists.labs;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class SuperString {

    private LinkedList<String> list;
    private Stack<String> stack;
    private boolean reversed;

    public SuperString() {
        list = new LinkedList<>();
        stack = new Stack<>();
        reversed = false;
    }

    public void append(String s) {
        list.addLast(s);
        stack.push(s);
    }

    public void insert(String s) {
        list.addFirst(s);
        stack.push(s);
    }

    public boolean contains(String s) {
        // враќа true доколку стрингот s се наоѓа во супер-стрингот
        return toString().contains(s);
    }

    public void reverse() {
        LinkedList<String> newList = new LinkedList<>();

//        for (String word : list) {
//            newList.addFirst(word);
//        }
        list.forEach(e -> newList.addFirst(e));

//        List<String> reversedList = new ArrayList<>();
//        for (String word : newList) {
//            String string = new StringBuilder(word).reverse().toString();
//            reversedList.add(string);
//        }
        List<String> reversedList = newList.stream()
                .map(word -> new StringBuilder(word).reverse().toString())
                .collect(Collectors.toList());

//        List<String> reversedStack = new ArrayList<>();
//        for (String s : stack) {
//            String string = new StringBuilder(s).reverse().toString();
//            reversedStack.add(string);
//        }

        List<String> reversedStack = stack.stream()
                .map(word -> new StringBuilder(word).reverse().toString())
                .collect(Collectors.toList());

        list = new LinkedList<>(reversedList);
        stack = new Stack<>();
        stack.addAll(reversedStack);
    }

    @Override
    public String toString() {
        return list.stream().collect(Collectors.joining());
    }

    public void removeLast(int k) {
//        for (int i=0;i<k;i++){
//            list.remove(stack.pop());
//        }
        IntStream.range(0, k).forEach(i -> list.remove(stack.pop()));
    }
}

public class SuperStringTest {

    public static void main(String[] args) {
        Scanner jin = new Scanner(System.in);
        int k = jin.nextInt();
        if (k == 0) {
            SuperString s = new SuperString();
            while (true) {
                int command = jin.nextInt();
                if (command == 0) {//append(String s)
                    s.append(jin.next());
                }
                if (command == 1) {//insert(String s)
                    s.insert(jin.next());
                }
                if (command == 2) {//contains(String s)
                    System.out.println(s.contains(jin.next()));
                }
                if (command == 3) {//reverse()
                    s.reverse();
                }
                if (command == 4) {//toString()
                    System.out.println(s);
                }
                if (command == 5) {//removeLast(int k)
                    s.removeLast(jin.nextInt());
                }
                if (command == 6) {//end
                    break;
                }
            }
        }
    }
}
