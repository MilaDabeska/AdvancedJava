package collections.aud;

import java.util.*;

class Participant {
    protected String city, code, name;
    protected int age;

    public Participant(String city, String code, String name, int age) {
        this.city = city;
        this.code = code;
        this.name = name;
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Participant)) return false;
        Participant that = (Participant) o;
        return Objects.equals(code, that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    @Override
    public String toString() {
        return String.format("%s %s %d", code, name, age);
    }

    public String getCity() {
        return city;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}

class Audition {
    protected Map<String, Set<Participant>> participantsByCity;

    public Audition() {
        participantsByCity = new HashMap<>();
    }

    public void addParticpant(String city, String code, String name, int age) {
//        if (!participantsByCity.containsKey(city)){
//            participantsByCity.put(city,new TreeSet<>());
//        }
        participantsByCity.putIfAbsent(city, new HashSet<>());

        participantsByCity.get(city).add(new Participant(city, code, name, age));

//        participantsByCity.computeIfPresent(city, (k, v) -> {
//            v.add(new Participant(city, code, name, age));
//            return v;
//        });
    }

    public void listByCity(String city) {
        participantsByCity.getOrDefault(city, new HashSet<>())
                .stream().sorted(Comparator.comparing(Participant::getName)
                .thenComparing(Participant::getAge)
                .thenComparing(Participant::getCode))
                .forEach(p -> System.out.println(p));
    }
}

public class AuditionTest {
    public static void main(String[] args) {
        Audition audition = new Audition();
        List<String> cities = new ArrayList<String>();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");
            if (parts.length > 1) {
                audition.addParticpant(parts[0], parts[1], parts[2],
                        Integer.parseInt(parts[3]));
            } else {
                cities.add(line);
            }
        }
        for (String city : cities) {
            System.out.printf("+++++ %s +++++\n", city);
            audition.listByCity(city);
        }
        scanner.close();
    }
}
