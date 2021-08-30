package midterm1;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class F1Test {

    public static void main(String[] args) throws IOException {
        F1Race f1Race = new F1Race();
        f1Race.readResults(System.in);
        f1Race.printSorted(System.out);
    }
}

class Driver implements Comparable<Driver> {
    private String name, bestLap1, bestLap2, bestLap3;

    public Driver(String name, String bestLap1, String bestLap2, String bestLap3) {
        this.name = name;
        this.bestLap1 = bestLap1;
        this.bestLap2 = bestLap2;
        this.bestLap3 = bestLap3;
    }

    public String bestLap() {
        String min = "";
        if (bestLap1.compareTo(bestLap2) > 0) {
            min = bestLap2;
        } else {
            min = bestLap1;
        }
        if (min.compareTo(bestLap3) < 0) {
            return min;
        } else return bestLap3;
    }

    @Override
    public int compareTo(Driver o) {
        return bestLap().compareTo(o.bestLap());
    }

    @Override
    public String toString() {
        // со 10 места за името на возачот (порамнето од лево) и
        // 10 места за времето на најдобриот круг порамнето од десно
        return String.format("%-10s%10s", name, bestLap());
    }
}

class F1Race {
    // vashiot kod ovde
    List<Driver> drivers;

    public F1Race() {
        drivers = new ArrayList<>();
    }

    public void readResults(InputStream inputStream) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = br.readLine()) != null) {
            //Massa 1:57:563 1:55:187 1:55:634
            String[] parts = line.split("\\s+");
            String name = parts[0];
            String lap1 = parts[1];
            String lap2 = parts[2];
            String lap3 = parts[3];
            drivers.add(new Driver(name, lap1, lap2, lap3));
        }
    }

    public void printSorted(OutputStream outputStream) {
        PrintWriter pw = new PrintWriter(outputStream);
        Collections.sort(drivers);
        int i = 1; //pozicii
        for (Driver driver : drivers) {
            //1. Alonso      1:53:563
            pw.println(i++ + ". " + driver.toString());
            pw.flush();
        }
    }
}
