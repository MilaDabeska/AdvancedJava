package exceptions.labs;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

// Ne e tocna,treba da se doreshi

class InvalidNameException extends Exception {
    public String name;

    public InvalidNameException(String name) {
        this.name = name;
    }
}

class MaximumSizeExceddedException extends Exception {
    public MaximumSizeExceddedException() {
    }
}

class InvalidFormatException extends Exception {
    public InvalidFormatException() {
    }
}

class InvalidNumberException extends Exception {
    public InvalidNumberException() {
    }
}

class Contact implements Comparable<Contact> {

    private String name;
    private List<String> phonenumbers;

    public Contact(String name, String... phonenumber) throws InvalidNameException, MaximumSizeExceddedException {
        this.name = name;
        phonenumbers = new ArrayList();
        if (!validName(name)) throw new InvalidNameException(name);
        if (phonenumbers.size() > 5) throw new MaximumSizeExceddedException();
        for (int i = 0; i < phonenumber.length; i++) {
            if (!validNumber(phonenumber[i])) throw new InvalidNameException(name);
            phonenumbers.add(phonenumber[i]);
        }
    }

    public boolean validName(String name) {
        // името треба да е подолго од 4 караткери, но максимум до 10 карактери и не смее
        // да содржи други знаци освен латинични букви и бројки
        return name.matches("[A-Z-a-z-0-9]{5,10}");
    }

    public boolean validNumber(String number) {
        if (number.length() == 9) {
            //"070", "071", "072", "075","076","077" или "078"
            switch (number.charAt(2)) {
                case 0:
                case 1:
                case 2:
                case 5:
                case 6:
                case 7:
                case 8:
                    return true;
            }
        }
        return false;
    }

    public String getName() {
        return name;
    }

    public String[] getNumbers() {
        return phonenumbers.toArray(new String[0]);
    }

    public void addNumber(String phonenumber) throws MaximumSizeExceddedException {
        if (!validNumber(phonenumber)) throw new MaximumSizeExceddedException();
        if (phonenumbers.size() > 5) throw new MaximumSizeExceddedException();
        phonenumbers.add(phonenumber);
    }

    @Override
    public String toString() {
        String sb = Arrays.stream(getNumbers())
                .map(i -> i + "\n")
                .collect(Collectors.joining("", name + "\n" + phonenumbers + "\n", ""));
        return sb;
    }

    public Contact valueOf(String s) throws InvalidFormatException, InvalidNameException, MaximumSizeExceddedException {
        String[] parts = s.split("\\s+");
        if (!validName((parts[0] + " " + parts[1]))) throw new InvalidFormatException();
        String name = parts[0] + " " + parts[1];
        String[] numbers = new String[parts.length - 2];
        for (int i = 2; i < parts.length; i++) {
            if (!validNumber(parts[i])) throw new InvalidFormatException();
            numbers[i - 2] = parts[i];
        }
        return new Contact(name, numbers);
    }

    @Override
    public int compareTo(Contact o) {
        return name.compareTo(o.name);
    }
}

class PhoneBook {

    private List<Contact> contacts;

    public PhoneBook() {
        contacts = new ArrayList<>();
    }

    public void addContact(Contact contact) throws MaximumSizeExceddedException, InvalidFormatException {
        if (contacts.size() > 250) throw new MaximumSizeExceddedException();
        for (Contact c : contacts) {
            if (c.getName().equals(c.getName())) throw new InvalidFormatException();
        }
        contacts.add(contact);
    }

    public Contact getContactForName(String name) {
//        for (Contact contact : contacts) {
//            if (contact.getName().equals(name)) return contact;
//        }
//        return null;
        return contacts.stream()
                .filter(contact -> contact.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public int numberOfContacts() {
        return contacts.size();
    }

    public Contact[] getContacts() {
//        Contact[] contact = new Contact[contacts.size()];
//        for (int i = 0; i < contacts.size(); i++) {
//            contact[i] = contacts.get(i);
//        }
        Contact[] contact = contacts.stream().toArray(Contact[]::new);
        Arrays.sort(contact);
        return contact;
    }

    public boolean removeContact(String name) {
        for (int i = 0; i < contacts.size(); i++) {
            if (contacts.get(i).getName().equals(name))
                contacts.remove(i);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        String sb;
        Collections.sort(contacts); //mora Comparable da se implementira
//        for (Contact contact : contacts) {
//            sb.append(contact.toString()).append("\n");
//        }
        sb = contacts.stream()
                .map(contact -> contact.toString() + "\n")
                .collect(Collectors.joining());
        return sb;
    }

    public static boolean saveAsTextFile(PhoneBook phonebook, String path) {
        File file = new File(path);
        try {
            file.createNewFile();
            PrintWriter pw = new PrintWriter(file);
            pw.print(phonebook.toString());
            pw.flush();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public static PhoneBook loadFromTextFile(String path) throws IOException, InvalidNameException, MaximumSizeExceddedException, InvalidFormatException {
        try (BufferedReader br = new BufferedReader(new FileReader(new File(path)));) {
            String current;
            PhoneBook phoneBook = new PhoneBook();
            while ((current = br.readLine()) != null) {
                String name = current;
                int length = Integer.parseInt(br.readLine());
                String[] numbers = new String[length];
                for (int i = 0; i < length; i++) {
                    numbers[i] = br.readLine();
                }
                Contact contact = new Contact(name, numbers);
                phoneBook.addContact(contact);
                br.readLine();
            }
            return phoneBook;
        }
    }

    public Contact[] getContactsForNumber(String number_prefix) {
        Contact[] contacts1 = new Contact[contacts.size()];
        int i = 0;
        for (Contact c : contacts) {
            for (String num : c.getNumbers()) {
                if (num.startsWith(number_prefix))
                    contacts1[i++] = c;
            }
        }
        return Arrays.copyOf(contacts1, i);
    }
}

public class PhonebookTester {

    public static void main(String[] args) throws Exception {
        Scanner jin = new Scanner(System.in);
        String line = jin.nextLine();
        switch (line) {
            case "test_contact":
                testContact(jin);
                break;
            case "test_phonebook_exceptions":
                testPhonebookExceptions(jin);
                break;
            case "test_usage":
                testUsage(jin);
                break;
        }
    }

    private static void testFile(Scanner jin) throws Exception {
        PhoneBook phonebook = new PhoneBook();
        while (jin.hasNextLine())
            phonebook.addContact(new Contact(jin.nextLine(), jin.nextLine().split("\\s++")));
        String text_file = "phonebook.txt";
        PhoneBook.saveAsTextFile(phonebook, text_file);
        PhoneBook pb = PhoneBook.loadFromTextFile(text_file);
        if (!pb.equals(phonebook)) System.out.println("Your file saving and loading doesn't seem to work right");
        else System.out.println("Your file saving and loading works great. Good job!");
    }

    private static void testUsage(Scanner jin) throws Exception {
        PhoneBook phonebook = new PhoneBook();
        while (jin.hasNextLine()) {
            String command = jin.nextLine();
            switch (command) {
                case "add":
                    phonebook.addContact(new Contact(jin.nextLine(), jin.nextLine().split("\\s++")));
                    break;
                case "remove":
                    phonebook.removeContact(jin.nextLine());
                    break;
                case "print":
                    System.out.println(phonebook.numberOfContacts());
                    System.out.println(Arrays.toString(phonebook.getContacts()));
                    System.out.println(phonebook.toString());
                    break;
                case "get_name":
                    System.out.println(phonebook.getContactForName(jin.nextLine()));
                    break;
                case "get_number":
                    System.out.println(Arrays.toString(phonebook.getContactsForNumber(jin.nextLine())));
                    break;
            }
        }
    }

    private static void testPhonebookExceptions(Scanner jin) {
        PhoneBook phonebook = new PhoneBook();
        boolean exception_thrown = false;
        try {
            while (jin.hasNextLine()) {
                phonebook.addContact(new Contact(jin.nextLine()));
            }
        } catch (InvalidNameException e) {
            System.out.println(e.name);
            exception_thrown = true;
        } catch (Exception e) {
        }
        if (!exception_thrown) System.out.println("Your addContact method doesn't throw InvalidNameException");
        /*
		exception_thrown = false;
		try {
		phonebook.addContact(new Contact(jin.nextLine()));
		} catch ( MaximumSizeExceddedException e ) {
			exception_thrown = true;
		}
		catch ( Exception e ) {}
		if ( ! exception_thrown ) System.out.println("Your addContact method doesn't throw MaximumSizeExcededException");
        */
    }

    private static void testContact(Scanner jin) throws Exception {
        boolean exception_thrown = true;
        String names_to_test[] = {"And\nrej", "asd", "AAAAAAAAAAAAAAAAAAAAAA", "Ð�Ð½Ð´Ñ€ÐµÑ˜A123213", "Andrej#", "Andrej<3"};
        for (String name : names_to_test) {
            try {
                new Contact(name);
                exception_thrown = false;
            } catch (InvalidNameException e) {
                exception_thrown = true;
            }
            if (!exception_thrown) System.out.println("Your Contact constructor doesn't throw an InvalidNameException");
        }
        String numbers_to_test[] = {"+071718028", "number", "078asdasdasd", "070asdqwe", "070a56798", "07045678a", "123456789", "074456798", "073456798", "079456798"};
        for (String number : numbers_to_test) {
            new Contact("Andrej", number);
            exception_thrown = false;
            if (!exception_thrown)
                System.out.println("Your Contact constructor doesn't throw an InvalidNumberException");
        }
        String nums[] = new String[10];
        for (int i = 0; i < nums.length; ++i) nums[i] = getRandomLegitNumber();
        try {
            new Contact("Andrej", nums);
            exception_thrown = false;
        } catch (MaximumSizeExceddedException e) {
            exception_thrown = true;
        }
        if (!exception_thrown)
            System.out.println("Your Contact constructor doesn't throw a MaximumSizeExceddedException");
        Random rnd = new Random(5);
        Contact contact = new Contact("Andrej", getRandomLegitNumber(rnd), getRandomLegitNumber(rnd), getRandomLegitNumber(rnd));
        System.out.println(contact.getName());
        System.out.println(Arrays.toString(contact.getNumbers()));
        System.out.println(contact.toString());
        contact.addNumber(getRandomLegitNumber(rnd));
        System.out.println(Arrays.toString(contact.getNumbers()));
        System.out.println(contact.toString());
        contact.addNumber(getRandomLegitNumber(rnd));
        System.out.println(Arrays.toString(contact.getNumbers()));
        System.out.println(contact.toString());
    }

    static String[] legit_prefixes = {"070", "071", "072", "075", "076", "077", "078"};
    static Random rnd = new Random();

    private static String getRandomLegitNumber() {
        return getRandomLegitNumber(rnd);
    }

    private static String getRandomLegitNumber(Random rnd) {
        StringBuilder sb = new StringBuilder(legit_prefixes[rnd.nextInt(legit_prefixes.length)]);
        for (int i = 3; i < 9; ++i)
            sb.append(rnd.nextInt(10));
        return sb.toString();
    }


}

