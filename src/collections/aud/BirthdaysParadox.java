package collections.aud;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.stream.IntStream;

public class BirthdaysParadox {

    public static boolean singleTrial(int person) {
        Set<Integer> birthdays = new HashSet<>();
        Random random = new Random();
        int counter = 0;

        for (int i = 0; i < person; i++) {
            int randomBday = random.nextInt(364) + 1;
            if (birthdays.contains(randomBday))
                return true;
            birthdays.add(randomBday);
        }
        return false;
    }

    public static double experiment(int person) {
//        int counter = 0;
//        for (int i = 0; i < 5000; i++) {
//            if (singleTrial(person))
//                counter++;
//        }
//        return counter / 5000.0;
        return IntStream.range(0, 5000)
                .filter(i -> singleTrial(person))
                .count() / 1000.0;
    }

    public static void main(String[] args) {
        for (int i = 2; i <= 50; i++) {
            System.out.printf("For %d people, the probability of two birthdays is about %.5f\n", i, experiment(i));
        }
    }
}
