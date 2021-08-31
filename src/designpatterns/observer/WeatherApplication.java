package designpatterns.observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


interface Subscriber {
    void setMeasurements(float temperature, float humidity, float pressure);
}

class CurrentConditionsDisplay implements Subscriber {

    public CurrentConditionsDisplay(WeatherDispatcher weatherDispatcher) {
        weatherDispatcher.register(this);
    }

    @Override
    public void setMeasurements(float temperature, float humidity, float pressure) {
        System.out.printf("Temperature: %.1fF\nHumidity: %.1f%%", temperature, humidity);
    }
}

class ForecastDisplay implements Subscriber {

    float previousPressure = (float) 0.0;

    public ForecastDisplay(WeatherDispatcher weatherDispatcher) {
        weatherDispatcher.register(this);
    }

    @Override
    public void setMeasurements(float temperature, float humidity, float pressure) {
        Integer result = Float.compare(pressure, previousPressure);
        if (result == 1) {
            System.out.println("Forecast: Improving\n");
        } else if (result == 0) {
            System.out.println("Forecast: Same\n");
        } else {
            System.out.println("Forecast: Cooler\n");
        }
        previousPressure = pressure;
    }
}

class WeatherDispatcher {
    List<Subscriber> subscribers;

    public WeatherDispatcher() {
        subscribers = new ArrayList<>();
    }

    public void setMeasurements(float temperature, float humidity, float pressure) {
        for (Subscriber s : subscribers) {
            s.setMeasurements(temperature, humidity, pressure);
        }
    }

    public void remove(Subscriber subscriber) {
        subscribers.remove(subscriber);
    }

    public void register(Subscriber subscriber) {
        subscribers.add(subscriber);
    }

}

public class WeatherApplication {

    public static void main(String[] args) {
        WeatherDispatcher weatherDispatcher = new WeatherDispatcher();

        CurrentConditionsDisplay currentConditions = new CurrentConditionsDisplay(weatherDispatcher);
        ForecastDisplay forecastDisplay = new ForecastDisplay(weatherDispatcher);

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            String[] parts = line.split("\\s+");
            weatherDispatcher.setMeasurements(Float.parseFloat(parts[0]), Float.parseFloat(parts[1]), Float.parseFloat(parts[2]));
            if (parts.length > 3) {
                int operation = Integer.parseInt(parts[3]);
                if (operation == 1) {
                    weatherDispatcher.remove(forecastDisplay);
                }
                if (operation == 2) {
                    weatherDispatcher.remove(currentConditions);
                }
                if (operation == 3) {
                    weatherDispatcher.register(forecastDisplay);
                }
                if (operation == 4) {
                    weatherDispatcher.register(currentConditions);
                }

            }
        }
    }
}
