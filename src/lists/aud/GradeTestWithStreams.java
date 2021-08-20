package lists.aud;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

class Student2 implements Comparable<Student2> {

    private String firstName, lastName;
    private int exam1, exam2, exam3;

    public Student2(String firstName, String lastName, int exam1, int exam2, int exam3) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.exam1 = exam1;
        this.exam2 = exam2;
        this.exam3 = exam3;
    }

    public static Student2 createStudentFrom(String line) {
        String[] parts = line.split(":");
        return new Student2(
                parts[0], //firstName
                parts[1], //lastName
                Integer.parseInt(parts[2]), //exam1
                Integer.parseInt(parts[3]), //exam2
                Integer.parseInt(parts[4]));//exam3
    }

    public double getTotalPoints() {
        return exam1 * 0.25 + exam2 * 0.3 + exam3 * 0.45;
    }

    public char getGrade() {
        double points = getTotalPoints();
        char grade = 'F';
        if (points >= 90) {
            grade = 'A';
        } else if (points >= 80) {
            grade = 'B';
        } else if (points >= 70) {
            grade = 'C';
        } else if (points >= 60) {
            grade = 'D';
        } else if (points >= 50) {
            grade = 'E';
        }
        return grade;
    }

    public String studentWithGrade() {
        return String.format("%s %s %c", firstName, lastName, getGrade());
    }

    @Override
    public int compareTo(Student2 o) {
        return Character.compare(getGrade(), o.getGrade());
    }

    @Override
    public String toString() {
        return String.format("%s %s %d %d %d %.2f %c",
                firstName, lastName, exam1, exam2, exam3, getTotalPoints(), getGrade());
    }
}

class Course2 {

    private List<Student> students;

    public Course2() {
        students = new ArrayList<>();
    }

    public void readStudentData(InputStream in) {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        students = br.lines()
                .map(line -> Student.createStudentFrom(line))/* .forEach(student -> students.add(student)); */
                .collect(Collectors.toList());
    }

    public void printSortedStudents(OutputStream out) {
        PrintWriter pw = new PrintWriter(out);
        students.stream()
                .sorted()
                .forEach(student -> pw.println(student.studentWithGrade()));
        pw.flush(); //mora na kraj
    }

    public void printDetailedReport(OutputStream out) {
        PrintWriter pw = new PrintWriter(out);
        students.stream()
                .forEach(student -> pw.println(student.toString()));
        pw.flush();
    }

    public void printGradeDistribution(OutputStream out) {
        PrintWriter pw = new PrintWriter(out);
        int[] gradeDistribution = new int[6];
        for (Student s : students) {
            gradeDistribution[s.getGrade() - 'A']++; //A -> 0,B -> 1...
        }
        for (int i = 0; i < 6; i++) {
            pw.printf("%c -> %d\n", i + 'A', gradeDistribution[i]);
        }
        pw.flush();
    }
}


public class GradeTestWithStreams {
    public static void main(String[] args) throws FileNotFoundException {
        File inputFile = new File("C:\\Users\\Danilo\\Desktop\\napredno\\src\\lists\\aud\\inputGrades");
        File outputFile = new File("C:\\Users\\Danilo\\Desktop\\napredno\\src\\lists\\aud\\outputGrades");
        Course course = new Course();
        course.readStudentData(new FileInputStream(inputFile));
        System.out.println("====Sorted students====");
        course.printSortedStudents(System.out);
        System.out.println("====Detailed report====");
        course.printDetailedReport(new FileOutputStream(outputFile));
        System.out.println("====Grade distribution====");
        course.printGradeDistribution(System.out);
    }
}
