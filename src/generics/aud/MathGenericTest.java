package generics.aud;

import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Random;

public class MathGenericTest {

    //public static <T extends Number> String statistics(List<T> numbers) -> I opcija
    public static String statistics(List<? extends Number> numbers) {

//        double min = Double.MAX_VALUE;
//        double max = Double.MIN_VALUE;
//        double sum = 0;
//
//        for (Number n : numbers) {
//            if (n.doubleValue() < min)
//                min = n.doubleValue();
//            if (n.doubleValue() > max)
//                max = n.doubleValue();
//            sum += n.doubleValue();
//        }
//        double average = sum / numbers.size();

        DoubleSummaryStatistics dss = new DoubleSummaryStatistics();
//        for (Number number : numbers) {
//            doubleSummaryStatistics.accept(number.doubleValue());
//        }
        numbers.stream().forEach(number -> dss.accept(number.doubleValue()));

//        DoubleSummaryStatistics dss =numbers.stream().mapToDouble(i->i.doubleValue()).summaryStatistics();

//        numbers.stream().mapToDouble(i->i.doubleValue()).average().getAsDouble();

        double sumStd = 0;
//        for (Number n : numbers) {
//            sumStd += (n.doubleValue() - average) * (n.doubleValue() - average);
//        }
        for (Number n : numbers) {
            sumStd += (n.doubleValue() - dss.getAverage()) * (n.doubleValue() - dss.getAverage());
        }

        double std = Math.sqrt(sumStd / numbers.size()); //standardna devijacija

//        List<Number> list = numbers.stream().sorted().collect(Collectors.toList());

        return String.format("Min: %.2f\nMax: %.2f\nAverage: %.2f\nStandard deviation: %.2f\n" +
                        "Count: %d\nSum: %.2f", dss.getMin(), dss.getMax(), dss.getAverage(),
                std, dss.getCount(), dss.getSum());
    }

    public static void main(String[] args) {
        List<Integer> integers = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 100000; i++) {
            integers.add(random.nextInt(100) + 1);
        }

        List<Double> doubles = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            doubles.add(random.nextDouble() * 100.0);
        }

        System.out.println(statistics(integers));
        System.out.println(statistics(doubles));
    }
}
