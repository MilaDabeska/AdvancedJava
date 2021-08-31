package collections.aud;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

class Book implements Comparable<Book> {
    protected String title, category;
    protected float price;

//    protected static Comparator<Book> byNameAndPrice = (l, r) -> {
//        int result = l.title.compareToIgnoreCase(r.title);
//        if (result == 0) return Float.compare(l.price, r.price);
//        else return result;
//    };

    protected static Comparator<Book> byNameAndPrice = Comparator.comparing(Book::getTitle)
            .thenComparing(Book::getPrice);

//    protected static Comparator<Book> byPrice = (l, r) -> {
//        int result = Float.compare(l.price, r.price);
//        if (result == 0) return l.title.compareToIgnoreCase(r.title);
//        else return result;
//    };

    protected static Comparator<Book> byPrice = Comparator.comparing(Book::getPrice)
            .thenComparing(Book::getTitle);

    public Book(String title, String category, float price) {
        this.title = title;
        this.category = category;
        this.price = price;
    }

    @Override
    public int compareTo(Book o) {
        int result = title.compareTo(o.title);
        if (result == 0) {
            return Float.compare(price, o.price);
        } else return result;
    }

    @Override
    public String toString() {
        //A Brief History of Time:A:25.66
        return String.format("%s (%s) %.2f", title, category, price);
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public float getPrice() {
        return price;
    }
}

class BookCollection {
    private List<Book> books;

    public BookCollection() {
        books = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void printByCategory(String category) {
//        Collections.sort(books);
//        for (Book b : books) {
//            if (b.category.equalsIgnoreCase(category))
//                System.out.println(b.toString());
//        }
        books.stream()
                .filter(b -> b.category.equalsIgnoreCase(category))
                .sorted(Book.byNameAndPrice)
                .forEach(b -> System.out.println(b.toString()));
    }

    public List<Book> getCheapestN(int n) {
        return books.stream()
                .sorted(Book.byPrice)
                .limit(n) //gi zima prvite n
                .collect(Collectors.toList());
    }
}

public class BooksTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        BookCollection booksCollection = new BookCollection();
        Set<String> categories = fillCollection(scanner, booksCollection);
        System.out.println("=== PRINT BY CATEGORY ===");
        for (String category : categories) {
            System.out.println("CATEGORY: " + category);
            booksCollection.printByCategory(category);
        }
        System.out.println("=== TOP N BY PRICE ===");
        print(booksCollection.getCheapestN(n));
    }

    static void print(List<Book> books) {
        for (Book book : books) {
            System.out.println(book);
        }
    }

    static TreeSet<String> fillCollection(Scanner scanner, BookCollection collection) {
        TreeSet<String> categories = new TreeSet<String>();
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            String[] parts = line.split(":");
            Book book = new Book(parts[0], parts[1], Float.parseFloat(parts[2]));
            collection.addBook(book);
            categories.add(parts[1]);
        }
        return categories;
    }
}

// Вашиот код овде
