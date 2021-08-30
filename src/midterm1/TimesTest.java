package midterm1;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class TimesTest {

    public static void main(String[] args) throws IOException {
        TimeTable timeTable = new TimeTable();
        try {
            timeTable.readTimes(System.in);
        } catch (UnsupportedFormatException e) {
            System.out.println("UnsupportedFormatException: " + e.getMessage());
        } catch (InvalidTimeException e) {
            System.out.println("InvalidTimeException: " + e.getMessage());
        }
        System.out.println("24 HOUR FORMAT");
        timeTable.writeTimes(System.out, TimeFormat.FORMAT_24);
        System.out.println("AM/PM FORMAT");
        timeTable.writeTimes(System.out, TimeFormat.FORMAT_AMPM);
    }
}

enum TimeFormat {
    FORMAT_24, FORMAT_AMPM
}

class InvalidTimeException extends Exception {
    public InvalidTimeException(String message) {
        super(message);
    }
}

class UnsupportedFormatException extends Exception {
    public UnsupportedFormatException(String message) {
        super(message);
    }
}

class Time implements Comparable<Time> {
    private int hours, minutes;

    public Time(int hours, int minutes) {
        this.hours = hours;
        this.minutes = minutes;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public String AmPm() {
        StringBuilder sb = new StringBuilder();
        if (hours == 0) {
            sb.append(String.format("%2d:%02d AM", hours + 12, minutes));
        } else if (hours >= 1 && hours <= 11) {
            sb.append(String.format("%2d:%02d AM", hours, minutes));
        } else if (hours == 12) {
            sb.append(String.format("%2d:%02d PM", hours, minutes));
        } else if (hours >= 13 && hours <= 23) {
            sb.append(String.format("%2d:%02d PM", hours - 12, minutes));
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return String.format("%2d:%02d", hours, minutes);
    }

    @Override
    public int compareTo(Time o) {
        if (hours == o.hours)
            return Integer.compare(minutes, o.minutes);
        return Integer.compare(hours, o.hours);
    }
}

class TimeTable {

    private List<Time> times;

    public TimeTable() {
        times = new ArrayList<>();
    }

    public void readTimes(InputStream inputStream) throws IOException, InvalidTimeException, UnsupportedFormatException {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split("\\s+");
            for (int i = 0; i < parts.length; i++) {
                String time = parts[i];
                if (!time.contains(":") && !time.contains(".")) {
                    throw new UnsupportedFormatException(time);
                }
                time = time.replace(".", ":");
                int hours = Integer.parseInt(time.split(":")[0]);
                int minutes = Integer.parseInt(time.split(":")[1]);

                if (hours > 23 || hours < 0 || minutes > 59 || minutes < 0) {
                    throw new InvalidTimeException(time);
                }

                times.add(new Time(hours, minutes));
            }
        }
    }

    public void writeTimes(OutputStream outputStream, TimeFormat format) {
        PrintWriter pw = new PrintWriter(outputStream);
        Collections.sort(times);
//        for (int i = 0; i < times.size(); i++) {
//            if (format.equals(TimeFormat.FORMAT_AMPM)) {
//                pw.println(times.get(i).AmPm());
//            } else {
//                pw.println(times.get(i).toString());
//            }
//        }
        IntStream.range(0, times.size()).forEach(i -> {
            if (format.equals(TimeFormat.FORMAT_AMPM)) {
                pw.println(times.get(i).AmPm());
            } else {
                pw.println(times.get(i).toString());
            }
        });
        pw.flush();
    }
}
