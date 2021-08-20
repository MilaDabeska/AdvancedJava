package second.labs;

import java.text.DecimalFormat;
import java.util.*;

abstract class Contact {

    private int date;

    public Contact(String date) {
        // YYYY-MM-DD
        String[] parts = date.split("-");
        this.date = Integer.parseInt(parts[0] + parts[1] + parts[2]);
    }

    public boolean isNewerThan(Contact c) {
        return date >= c.date;
    }

    public abstract String getType();
}

class EmailContact extends Contact {

    private String email;

    public EmailContact(String date, String email) {
        super(date);
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getType() {
        return "Email";
    }

    @Override
    public String toString() {
        return email;
    }
}

enum Operator {
    TMOBILE,
    ONE,
    VIP
}

class PhoneContact extends Contact {

    private String phone;

    public PhoneContact(String date, String phone) {
        super(date);
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public Operator getOperator() {
        // 070, 071, 072
        if (phone.charAt(2) == '0' || phone.charAt(2) == '1' || phone.charAt(2) == '2') {
            return Operator.TMOBILE;
        } else if (phone.charAt(2) == '5' || phone.charAt(2) == '6') {
            return Operator.ONE;
        } else return Operator.VIP;
    }

    @Override
    public String getType() {
        return "Phone";
    }

    @Override
    public String toString() {
        return phone;
    }
}

class Student {

    private List<Contact> contacts;
    private String firstName, lastName, city;
    private int age;
    private long index;

    public Student(String firstName, String lastName, String city, int age, long index) {
        contacts = new ArrayList<>();
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.age = age;
        this.index = index;
    }

    public void addEmailContact(String date, String email) {
        contacts.add(new EmailContact(date, email));
    }

    public void addPhoneContact(String date, String phone) {
        contacts.add(new PhoneContact(date, phone));
    }

    public Contact[] getEmailContacts() {
//        Contact[] tmp = new Contact[emailContacts];
//        int counter = 0;
//        for (int i = 0; i < emailContacts; i++) {
//            if (tmp[i].getType().equals("Email")) {
//                tmp[counter++] = tmp[i];
//            }
//        }
//        return tmp;
        return (Contact[]) contacts.stream().filter(i -> i.getType().equals("Email")).toArray(EmailContact[]::new);
    }

    public Contact[] getPhoneContacts() {
//        Contact[] tmp = new Contact[phoneContacts];
//        int counter = 0;
//        for (int i = 0; i < phoneContacts; i++) {
//            if (tmp[i].getType().equals("Phone")) {
//                tmp[counter++] = tmp[i];
//            }
//        }
//        return tmp;
        return (Contact[]) contacts.stream().filter(i -> i.getType().equals("Phone")).toArray(PhoneContact[]::new);
    }

    public String getCity() {
        return city;
    }

    public String getFullName() {
        return String.format("%s %s", firstName, lastName);
    }

    public long getIndex() {
        return index;
    }

    public Contact getLatestContact() {
//        int indx = 0;
//        for (int i = 0; i < contactsNumber; i++) {
//            if (contacts[i].isNewerThan(contacts[indx]))
//                indx = i;
//        }
//        return contacts[indx];

        return contacts.stream().reduce((contact, contact2) -> (contact.isNewerThan(contact2) ? contact : contact2)).get();
    }

    public int contactsNumber() {
        return contacts.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("{\"ime\":\"%s\", \"prezime\":\"%s\", \"vozrast\":%d," +
                " \"grad\":\"%s\", \"indeks\":%d, ", firstName, lastName, age, city, index));
        sb.append("\"telefonskiKontakti\":[");

        Contact[] phones = getPhoneContacts();
        for (int i = 0; i < phones.length - 1; i++)
            sb.append(String.format("\"%s\", ", phones[i].toString()));
        if (phones.length >= 1)
            sb.append(String.format("\"%s\"", phones[phones.length - 1].toString()));
        sb.append("], \"emailKontakti\":[");

        Contact[] emails = getEmailContacts();
        for (int i = 0; i < emails.length - 1; i++)
            sb.append(String.format("\"%s\", ", emails[i].toString()));
        if (emails.length >= 1)
            sb.append(String.format("\"%s\"", emails[emails.length - 1].toString()));
        sb.append("]}");
        return sb.toString();
    }
}

class Faculty {

    private String name;
    private Student[] students;

    public Faculty(String name, Student[] students) {
        this.name = name;
        this.students = students;
    }

    public int countStudentsFromCity(String cityName) {
        //враќа колку студенти има од даден град
//        int count = 0;
//        for (int i = 0; i < students.length; i++) {
//            if (cityName.equals(students[i].getCity())) {
//                count++;
//            }
//        }
//        return count;
        return (int) Arrays.stream(students).filter(i -> i.getCity().equals(cityName)).count();
    }

    public Student getStudent(long index) {
        //метод кој го враќа студентот кој го има дадениот индекс
//        int idx = -1;
//        for (int i = 0; i < students.length; i++) {
//            if (students[i].getIndex() == index) {
//                idx = i;
//                break;
//            }
//        }
//        return students[idx];
        return Arrays.stream(students).filter(i -> i.getIndex() == index).findFirst().orElse(null);
    }

    public double getAverageNumberOfContacts() {
//        double sum = 0;
//        for (int i = 0; i < students.length; i++) {
//            sum += students[i].getContactsNumber();
//        }
//        return sum;
        return Arrays.stream(students).mapToDouble(Student::contactsNumber).average().orElse(0);
    }

    public Student getStudentWithMostContacts() {
        //кој го враќа студентот со најмногу контакти (доколку има повеќе студенти со ист број
        // на контакти да го врати студентот со најголем индекс)
//        int maxIndex = 0;
//        int maxContacts = students[0].contacts.length;
//        for (int i = 1; i < students.length; i++) {
//            if (students[i].contacts.length > maxContacts) {
//                maxContacts = students[i].contacts.length;
//                maxIndex = i;
//            } else if (students[i].contacts.length == maxContacts) {
//                if (students[i].getIndex() > students[maxContacts].getIndex()) {
//                    maxContacts = students[i].contacts.length;
//                    maxIndex = i;
//                }
//            }
//        }
//        return students[maxIndex];
        return Arrays.stream(students).max(Comparator.comparing(Student::contactsNumber).thenComparing(Student::getIndex)).orElse(null);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("{\"fakultet\":\"%s\", \"studenti\":[", name));
        for (int i = 0; i < students.length - 1; i++) {
            sb.append(students[i]);
            sb.append(", ");
        }
        sb.append(students[students.length - 1]);
        sb.append("]}");
        return sb.toString();
    }
}


public class ContactsTester {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int tests = scanner.nextInt();
        Faculty faculty = null;

        int rvalue = 0;
        long rindex = -1;

        DecimalFormat df = new DecimalFormat("0.00");

        for (int t = 0; t < tests; t++) {

            rvalue++;
            String operation = scanner.next();

            switch (operation) {
                case "CREATE_FACULTY": {
                    String name = scanner.nextLine().trim();
                    int N = scanner.nextInt();

                    Student[] students = new Student[N];

                    for (int i = 0; i < N; i++) {
                        rvalue++;

                        String firstName = scanner.next();
                        String lastName = scanner.next();
                        String city = scanner.next();
                        int age = scanner.nextInt();
                        long index = scanner.nextLong();

                        if ((rindex == -1) || (rvalue % 13 == 0))
                            rindex = index;

                        Student student = new Student(firstName, lastName, city,
                                age, index);
                        students[i] = student;
                    }

                    faculty = new Faculty(name, students);
                    break;
                }

                case "ADD_EMAIL_CONTACT": {
                    long index = scanner.nextInt();
                    String date = scanner.next();
                    String email = scanner.next();

                    rvalue++;

                    if ((rindex == -1) || (rvalue % 3 == 0))
                        rindex = index;

                    faculty.getStudent(index).addEmailContact(date, email);
                    break;
                }

                case "ADD_PHONE_CONTACT": {
                    long index = scanner.nextInt();
                    String date = scanner.next();
                    String phone = scanner.next();

                    rvalue++;

                    if ((rindex == -1) || (rvalue % 3 == 0))
                        rindex = index;

                    faculty.getStudent(index).addPhoneContact(date, phone);
                    break;
                }

                case "CHECK_SIMPLE": {
                    System.out.println("Average number of contacts: "
                            + df.format(faculty.getAverageNumberOfContacts()));

                    rvalue++;

                    String city = faculty.getStudent(rindex).getCity();
                    System.out.println("Number of students from " + city + ": "
                            + faculty.countStudentsFromCity(city));

                    break;
                }

                case "CHECK_DATES": {

                    rvalue++;

                    System.out.print("Latest contact: ");
                    Contact latestContact = faculty.getStudent(rindex)
                            .getLatestContact();
                    if (latestContact.getType().equals("Email"))
                        System.out.println(((EmailContact) latestContact)
                                .getEmail());
                    if (latestContact.getType().equals("Phone"))
                        System.out.println(((PhoneContact) latestContact)
                                .getPhone()
                                + " ("
                                + ((PhoneContact) latestContact).getOperator()
                                .toString() + ")");

                    if (faculty.getStudent(rindex).getEmailContacts().length > 0
                            && faculty.getStudent(rindex).getPhoneContacts().length > 0) {
                        System.out.print("Number of email and phone contacts: ");
                        System.out
                                .println(faculty.getStudent(rindex)
                                        .getEmailContacts().length
                                        + " "
                                        + faculty.getStudent(rindex)
                                        .getPhoneContacts().length);

                        System.out.print("Comparing dates: ");
                        int posEmail = rvalue
                                % faculty.getStudent(rindex).getEmailContacts().length;
                        int posPhone = rvalue
                                % faculty.getStudent(rindex).getPhoneContacts().length;

                        System.out.println(faculty.getStudent(rindex)
                                .getEmailContacts()[posEmail].isNewerThan(faculty
                                .getStudent(rindex).getPhoneContacts()[posPhone]));
                    }

                    break;
                }

                case "PRINT_FACULTY_METHODS": {
                    System.out.println("Faculty: " + faculty.toString());
                    System.out.println("Student with most contacts: "
                            + faculty.getStudentWithMostContacts().toString());
                    break;
                }
            }
        }
        scanner.close();
    }
}
