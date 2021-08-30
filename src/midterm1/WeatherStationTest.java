package midterm1;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.stream.Collectors;


class WeatherData {

    private double temperature, humidity, wind, visibility;
    private Date date;

    public WeatherData(double temperature, double wind, double humidity, double visibility, Date date) {
        this.temperature = temperature;
        this.wind = wind;
        this.humidity = humidity;
        this.visibility = visibility;
        this.date = date;
    }

    @Override
    public String toString() {
        //41.8 9.4 km/h 40.8% 20.7 km Tue Dec 17 23:35:15 GMT 2013
        return String.format("%.1f %.1f km/h %.1f%% %.1f km %s", temperature, wind, humidity, visibility, date);
    }

    public double getTemperature() {
        return temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public double getWind() {
        return wind;
    }

    public double getVisibility() {
        return visibility;
    }

    public Date getDate() {
        return date;
    }
}

class WeatherStation {

    private int days;
    private List<WeatherData> data;

    public WeatherStation(int days) {
        this.days = days;
        data = new ArrayList<>();
    }

    public void addMeasurment(float temperature, float wind, float humidity, float visibility, Date date) {
        for (WeatherData d : data) {
            //ако времето на новото мерење кое се додава се разликува за помалку од 2.5 минути
            // од времето на некое претходно додадено мерење, тоа треба да се игнорира (не се додава)
            if (Math.abs(d.getDate().getTime() - date.getTime()) < 150 * 1000) {
                return;
            }
        }

        Iterator<WeatherData> it = data.iterator();
        while (it.hasNext()) {
            WeatherData d = it.next();
            long time1 = d.getDate().getMonth() * 31 + d.getDate().getDate();
            long time2 = date.getMonth() * 31 + date.getDate();
            if (time2 - time1 >= days) {
                it.remove();
            }
        }
        data.add(new WeatherData(temperature, wind, humidity, visibility, date));
    }

    public int total() {
        return data.size();
    }

    public void status(Date from, Date to) {
        //ги печати сите мерења во периодот од from до to подредени според датумот во
        // растечки редослед и на крај ја печати просечната температура во овој период

        double average = 0;
//        List<WeatherData> tmp = new ArrayList<>();
//        for (WeatherData d : data) {
//            if (d.getDate().getTime() >= from.getTime() && d.getDate().getTime() <= to.getTime())
//                tmp.add(d);
//        }
        List<WeatherData> tmp = data.stream()
                .filter(d -> d.getDate().getTime() >= from.getTime() && d.getDate().getTime() <= to.getTime())
                .collect(Collectors.toList());

        Iterator<WeatherData> it = tmp.iterator();
        while (it.hasNext()) {
            WeatherData d = it.next();
            System.out.println(d.toString());
            average += d.getTemperature();
        }


//        List<WeatherData> list = new ArrayList<>();
//        for (WeatherData weatherData : tmp) {
//            list.add(weatherData);
//        }
//        list.sort((o1, o2) -> (o1.getDate().compareTo(o2.getDate())));
//        tmp = list;

        tmp = tmp.stream()
                .sorted((o1, o2) -> (o1.getDate().compareTo(o2.getDate())))
                .collect(Collectors.toList());

        if (data.isEmpty()) {
            throw new RuntimeException();
        }

        if (tmp.isEmpty()) {
            throw new RuntimeException();
        }

        System.out.printf("Average temperature: %.2f", average / tmp.size());

    }

}

public class WeatherStationTest {
    public static void main(String[] args) throws ParseException {
        Scanner scanner = new Scanner(System.in);
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        int n = scanner.nextInt();
        scanner.nextLine();
        WeatherStation ws = new WeatherStation(n);
        while (true) {
            String line = scanner.nextLine();
            if (line.equals("=====")) {
                break;
            }
            String[] parts = line.split(" ");
            float temp = Float.parseFloat(parts[0]);
            float wind = Float.parseFloat(parts[1]);
            float hum = Float.parseFloat(parts[2]);
            float vis = Float.parseFloat(parts[3]);
            line = scanner.nextLine();
            Date date = df.parse(line);
            ws.addMeasurment(temp, wind, hum, vis, date);
        }
        String line = scanner.nextLine();
        Date from = df.parse(line);
        line = scanner.nextLine();
        Date to = df.parse(line);
        scanner.close();
        System.out.println(ws.total());
        try {
            ws.status(from, to);
        } catch (RuntimeException e) {
            System.out.println(e);
        }
    }
}

// vashiot kod ovde
