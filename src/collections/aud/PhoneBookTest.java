package collections.aud;

import java.util.*;

class DuplicateNumberException extends Exception {
    private String num;

    public DuplicateNumberException(String num) {
        super(String.format("Duplicate number: %s", num));
    }
}

class Contact implements Comparable<Contact> {
    protected String name, number;

    public Contact(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return String.format("%s %s", name, number);
    }

    @Override
    public int compareTo(Contact o) {
        int result = name.compareTo(o.name);
        if (result == 0) return number.compareTo(o.number);
        else return result;
    }

    public List<String> getSubNumbers() {
        List<String> result = new ArrayList<>();
        for (int i = 3; i <= number.length(); i++) {
            for (int j = 0; j <= number.length() - i; j++) {
                result.add(number.substring(j, j + i));
            }
        }
        return result;
    }
}

class PhoneBook {
    Map<String, String> namesByPhoneNumber;
    Map<String, Set<Contact>> contactsBySubnumber;
    Map<String, Set<Contact>> contactsByName;

    public PhoneBook() {
        namesByPhoneNumber = new HashMap<>();
        contactsBySubnumber = new HashMap<>();
        contactsByName = new HashMap<>();
    }

    public void addContact(String name, String number) throws DuplicateNumberException {
        if (namesByPhoneNumber.containsKey(number)) {
            throw new DuplicateNumberException(number);
        }
        namesByPhoneNumber.put(number, name);

        contactsByName.putIfAbsent(name, new TreeSet<>()); //kje bidat sortirani kontaktite
        contactsByName.get(name).add(new Contact(name, number));

        Contact c = new Contact(name, number);

        for (String subNumber : c.getSubNumbers()) {
            contactsBySubnumber.putIfAbsent(subNumber, new TreeSet<>());
            contactsBySubnumber.get(subNumber).add(c);
        }
    }

    public void contactsByNumber(String number) {
        if (!contactsBySubnumber.containsKey(number)) {
            System.out.println("NOT FOUND");
        } else {
            contactsBySubnumber.get(number)
                    .stream()
                    .forEach(c -> System.out.println(c));
        }
    }

    public void contactsByName(String name) {
        if (!contactsByName.containsKey(name)) {
            System.out.println("NOT FOUND");
        } else {
            contactsByName.get(name)
                    .stream()
                    .forEach(c -> System.out.println(c));
        }
    }
}

public class PhoneBookTest {

    public static void main(String[] args) {
        PhoneBook phoneBook = new PhoneBook();
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split(":");
            try {
                phoneBook.addContact(parts[0], parts[1]);
            } catch (DuplicateNumberException e) {
                System.out.println(e.getMessage());
            }
        }
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            System.out.println(line);
            String[] parts = line.split(":");
            if (parts[0].equals("NUM")) {
                phoneBook.contactsByNumber(parts[1]);
            } else {
                phoneBook.contactsByName(parts[1]);
            }
        }
    }
}

// Вашиот код овде


