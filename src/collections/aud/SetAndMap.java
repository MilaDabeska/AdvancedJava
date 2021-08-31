package collections.aud;

import java.util.*;
import java.util.function.Function;

public class SetAndMap {
    public static void main(String[] args) {
        HashSet<String> hashSet = new HashSet<>(); //se gubi redosledot na vnesuvanje
        hashSet.add("12345");
        hashSet.add("Mila");
        hashSet.add("Vesna");
        hashSet.add("Petko");
        System.out.println(hashSet);

        LinkedHashSet<String> linkedHashSet = new LinkedHashSet<>(); //go zacuvuva redosledot
        linkedHashSet.add("12345");
        linkedHashSet.add("Mila");
        linkedHashSet.add("Vesna");
        linkedHashSet.add("Petko");
        System.out.println(linkedHashSet);

        TreeSet<String> treeSet = new TreeSet<>(Comparator.comparing(String::length)
                .thenComparing(Function.identity())); //gi sortira elementite,proveruva duplikati spored compareTo
        treeSet.add("12345");
        treeSet.add("Mila");
        treeSet.add("Vesna");
        treeSet.add("Petko");
        System.out.println(treeSet);

        HashMap<String, String> hashMap = new HashMap<>(); //se gubi redosledot na vnesuvanje
        hashMap.put("12345", "678910");
        hashMap.put("Mila", "21");
        hashMap.put("Vesna", "50");
        hashMap.put("Petko", "32");
        System.out.println(hashMap);

        LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<>(); //go zacuvuva redosledot
        linkedHashMap.put("12345", "678910");
        linkedHashMap.put("Mila", "21");
        linkedHashMap.put("Vesna", "50");
        linkedHashMap.put("Petko", "32");
        System.out.println(linkedHashMap);

        TreeMap<String, String> treeMap = new TreeMap<>(); //gi sortira elementite
        treeMap.put("12345", "678910");
        treeMap.put("Mila", "21");
        treeMap.put("Vesna", "50");
        treeMap.put("Petko", "32");
        System.out.println(treeMap);
    }
}
