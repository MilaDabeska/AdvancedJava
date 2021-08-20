package lists.aud;

import java.io.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

class Person implements Comparable<Person> {

    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Person(String line) {
        String[] parts = line.split("\\s+");
        this.name = parts[0];
        this.age = Integer.parseInt(parts[1]);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Person{");
        sb.append("name='").append(name).append('\'');
        sb.append(", age=").append(age);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public int compareTo(Person o) {
        return Integer.compare(age, o.age);
    }
}

public class OldestPersonTest {

    public static void printOldestPerson(InputStream in) {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));

//        br.lines().map(line -> {
//            String[] parts = line.split("\\s+");
//            String name = parts[0];
//            int age = Integer.parseInt(parts[1]);
//            return new Person(name, age);
//        }).max(Comparator.naturalOrder()).get();

//        Person oldest = br.lines().map(line -> new Person(line)).max(Comparator.naturalOrder()).get();
//        System.out.println(oldest.toString());

        List<Person> list = br.lines().map(line -> new Person(line)).collect(Collectors.toList());
        Collections.sort(list);
        System.out.println(list.get(list.size() - 1));
    }

    public static void main(String[] args) {
        File file = new File("C:\\Users\\Danilo\\Desktop\\napredno\\src\\lists\\aud\\people");

        try {
            printOldestPerson(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
