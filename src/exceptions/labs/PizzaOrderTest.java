package exceptions.labs;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

// 85.71% tocna

class ItemOutOfStockException extends Exception {
    public ItemOutOfStockException(Item item) {
    }
}

class InvalidExtraTypeException extends Exception {
    public InvalidExtraTypeException() {
    }
}

class InvalidPizzaTypeException extends Exception {
    public InvalidPizzaTypeException() {
    }
}

class ArrayIndexOutOfBоundsException extends Exception {
    public ArrayIndexOutOfBоundsException(int idx) {
    }
}

class OrderLockedException extends Exception {
    public OrderLockedException() {
    }
}

class EmptyOrder extends Exception {
    public EmptyOrder() {
    }
}

interface Item {
    int getPrice();

    String getType();
}

class ExtraItem implements Item {

    private String type;

    public ExtraItem(String type) throws InvalidExtraTypeException {
        if (!(type.equals("Coke") || type.equals("Ketchup"))) {
            throw new InvalidExtraTypeException();
        }
        this.type = type;
    }

    @Override
    public int getPrice() {
        switch (type) {
            case "Ketchup":
                return 3;
            case "Coke":
                return 5;
            default:
                return 0;
        }
    }

    @Override
    public String getType() {
        return type;
    }
}

class PizzaItem implements Item {

    private String type;

    public PizzaItem(String type) throws InvalidPizzaTypeException {
        if (!(type.equals("Standard") || type.equals("Pepperoni") || type.equals("Vegetarian"))) {
            throw new InvalidPizzaTypeException();
        }
        this.type = type;
    }

    @Override
    public int getPrice() {
        switch (type) {
            case "Standard":
                return 10;
            case "Pepperoni":
                return 12;
            case "Vegetarian":
                return 8;
            default:
                return 0;
        }
    }

    @Override
    public String getType() {
        return type;
    }
}

class Order {

    private List<Item> items;
    private List<Integer> count;
    boolean locked;

    public Order() {
        items = new ArrayList<>();
        count = new ArrayList<>();
        this.locked = false;
    }

    public void addItem(Item item, int count) throws ItemOutOfStockException, InvalidPizzaTypeException, InvalidExtraTypeException, OrderLockedException {
        if (locked) throw new OrderLockedException();
        if (count > 10) throw new ItemOutOfStockException(item);

        //Доколку во нарачката веќе ја има соодветната ставка Item
        // тогаш истата се заменува со нова.

        IntStream.range(0, items.size()) //for
                .filter(i -> items.get(i).getType().equals(item.getType())) //if
                .findFirst()
                .ifPresent(i -> this.count.set(i, count));

        items.add(item instanceof PizzaItem ? new PizzaItem(item.getType()) : new ExtraItem(item.getType()));
        this.count.add(count);
    }

    public int getPrice() {
        return IntStream.range(0, items.size())
                .map(i -> items.get(i).getPrice() * count.get(i))
                .sum();
    }

    public void displayOrder() {
        IntStream.range(0, items.size())
                .forEach(i -> System.out.printf("%3d.%-15sx%2d%5d$\n", i + 1, items.get(i).getType(),
                        count.get(i), items.get(i).getPrice() * count.get(i)));
//        IntStream.range(0, items.size())
//                .mapToObj(i -> String.format("%3d.%-15sx%2d%5d$\n", i + 1, items.get(i).getType(),
//                count.get(i), items.get(i).getPrice() * count.get(i))).forEach(printStream -> System.out.printf(printStream));
        System.out.printf("Total:                   %d$\n", getPrice());
    }

    public void removeItem(int idx) throws ArrayIndexOutOfBоundsException, OrderLockedException {
        if (locked) throw new OrderLockedException();
        if (idx > count.size()) throw new ArrayIndexOutOfBоundsException(idx);
        items.remove(idx);
        count.remove(idx);
    }

    public void lock() throws EmptyOrder {
        if (items.size() == 0) throw new EmptyOrder();
        else locked = true;
    }
}

public class PizzaOrderTest {
    public static void main(String[] args) {
        Scanner jin = new Scanner(System.in);
        int k = jin.nextInt();
        if (k == 0) { //test Item
            try {
                String type = jin.next();
                String name = jin.next();
                Item item = null;
                if (type.equals("Pizza")) item = new PizzaItem(name);
                else item = new ExtraItem(name);
                System.out.println(item.getPrice());
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
        }
        if (k == 1) { // test simple order
            Order order = new Order();
            while (true) {
                try {
                    String type = jin.next();
                    String name = jin.next();
                    Item item = null;
                    if (type.equals("Pizza")) item = new PizzaItem(name);
                    else item = new ExtraItem(name);
                    if (!jin.hasNextInt()) break;
                    order.addItem(item, jin.nextInt());
                } catch (Exception e) {
                    System.out.println(e.getClass().getSimpleName());
                }
            }
            jin.next();
            System.out.println(order.getPrice());
            order.displayOrder();
            while (true) {
                try {
                    String type = jin.next();
                    String name = jin.next();
                    Item item = null;
                    if (type.equals("Pizza")) item = new PizzaItem(name);
                    else item = new ExtraItem(name);
                    if (!jin.hasNextInt()) break;
                    order.addItem(item, jin.nextInt());
                } catch (Exception e) {
                    System.out.println(e.getClass().getSimpleName());
                }
            }
            System.out.println(order.getPrice());
            order.displayOrder();
        }
        if (k == 2) { // test order with removing
            Order order = new Order();
            while (true) {
                try {
                    String type = jin.next();
                    String name = jin.next();
                    Item item = null;
                    if (type.equals("Pizza")) item = new PizzaItem(name);
                    else item = new ExtraItem(name);
                    if (!jin.hasNextInt()) break;
                    order.addItem(item, jin.nextInt());
                } catch (Exception e) {
                    System.out.println(e.getClass().getSimpleName());
                }
            }
            jin.next();
            System.out.println(order.getPrice());
            order.displayOrder();
            while (jin.hasNextInt()) {
                try {
                    int idx = jin.nextInt();
                    order.removeItem(idx);
                } catch (Exception e) {
                    System.out.println(e.getClass().getSimpleName());
                }
            }
            System.out.println(order.getPrice());
            order.displayOrder();
        }
        if (k == 3) { //test locking & exceptions
            Order order = new Order();
            try {
                order.lock();
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
            try {
                order.addItem(new ExtraItem("Coke"), 1);
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
            try {
                order.lock();
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
            try {
                order.removeItem(0);
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
        }
    }
}
