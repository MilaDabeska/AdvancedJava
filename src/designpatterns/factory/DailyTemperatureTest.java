package designpatterns.factory;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.List;

// Factory design pattern

abstract class Temperature {
    protected int value;

    public Temperature(int value) {
        this.value = value;
    }

    public static Temperature createTemperature(String part) {
        char type = part.charAt(part.length() - 1);
        Integer value = Integer.parseInt(part.substring(0, part.length() - 1));
        if (type == 'C') return new Celsius(value);
        else
            return new Fahrenheit(value);
    }

    abstract double getCelsius();

    abstract double getFahrenheit();
}

class Celsius extends Temperature {
    public Celsius(int value) {
        super(value);
    }

    @Override
    double getCelsius() {
        return value;
    }

    @Override
    double getFahrenheit() {
        //T∗9/5+32
        return (double) (value * 9.0) / 5.0 + 32;
    }

    @Override
    public String toString() {
        return String.format("%dC", value);
    }
}

class Fahrenheit extends Temperature {

    public Fahrenheit(int value) {
        super(value);
    }

    @Override
    double getCelsius() {
        //(T−32)∗5/9
        return (double) (value - 32) * 5.0 / 9.0;
    }

    @Override
    double getFahrenheit() {
        return value;
    }

    @Override
    public String toString() {
        return String.format("%dF", value);
    }
}

class DailyData implements Comparable<DailyData> {

    protected int day;
    protected List<Temperature> temperatures; //непознат број на мерења на температури за тој ден

    public DailyData(int day, List<Temperature> temperatures) {
        this.day = day;
        this.temperatures = temperatures;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public List<Temperature> getTemperatures() {
        return temperatures;
    }

    public void setTemperatures(List<Temperature> temperatures) {
        this.temperatures = temperatures;
    }

    public static DailyData createDailyMeasurement(String line) {
        String[] parts = line.split("\\s+");
        int day = Integer.parseInt(parts[0]);

//        List<Temperature> temperatures=new ArrayList<>();
//        for (int i=1;i< parts.length;i++){
//            temperatures.add(Temperature.createTemperature(parts[i]));
//        }

        List<Temperature> temperatures = Arrays.stream(parts)
                .skip(1)
                .map(i -> Temperature.createTemperature(i))
                .collect(Collectors.toList());

        return new DailyData(day, temperatures);
    }

    public String toString(char scale) {
        DoubleSummaryStatistics dss = temperatures.stream()
                .mapToDouble(t -> scale == 'C' ? t.getCelsius() : t.getFahrenheit())
                .summaryStatistics();

        //[ден]: Count: [вк. мерења - 3 места] Min: [мин. температура] Max: [макс. температура] Avg: [просек ]
        //11: Count:   7 Min:  38.33C Max:  40.56C Avg:  39.44C

        return String.format("%3d: Count: %3d Min: %6.2f%c Max: %6.2f%c Avg: %6.2f%c",
                day, dss.getCount(), dss.getMin(), scale, dss.getMax(), scale, dss.getAverage(), scale);
    }

    @Override
    public int compareTo(DailyData o) {
        return Integer.compare(day, o.day);
    }
}

class DailyTemperatures {

    List<DailyData> days;

    public DailyTemperatures() {
        days = new ArrayList<>();
    }

    public void readTemperatures(InputStream inputStream) {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        days = br.lines()
                .map(line -> DailyData.createDailyMeasurement(line))
                .collect(Collectors.toList());
    }

    public void writeDailyStats(OutputStream outputStream, char scale) {
        PrintWriter pw = new PrintWriter(outputStream);
        days.sort(Comparator.naturalOrder());
        days.forEach(dailyTemperatures -> pw.println(dailyTemperatures.toString(scale)));
        pw.flush();
    }
}

/**
 * I partial exam 2016
 */
public class DailyTemperatureTest {
    public static void main(String[] args) {
        DailyTemperatures dailyTemperatures = new DailyTemperatures();
        dailyTemperatures.readTemperatures(System.in);
        System.out.println("=== Daily temperatures in Celsius (C) ===");
        dailyTemperatures.writeDailyStats(System.out, 'C');
        System.out.println("=== Daily temperatures in Fahrenheit (F) ===");
        dailyTemperatures.writeDailyStats(System.out, 'F');
    }
}

// Vashiot kod ovde
