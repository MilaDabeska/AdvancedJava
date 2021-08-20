package generics.aud;


import java.util.ArrayList;
import java.util.List;

class Node<T> implements Comparable<Node<T>> {
    protected T item;
    protected int priority;

    public Node(T item, int priority) {
        this.item = item;
        this.priority = priority;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Node{");
        sb.append("element=").append(item);
        sb.append(", priority=").append(priority);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public int compareTo(Node<T> o) {
        return Integer.compare(priority, o.priority);
    }
}

class PriorityQueue<T> {
    private List<Node<T>> elements;

    public PriorityQueue() {
        elements = new ArrayList<>();
    }

    public void add(T item, int priority) {
        // додава нов елемент со асоциран приоритет
        Node<T> node = new Node<>(item, priority);
        int i;
        for (i = 0; i < elements.size(); i++) {
            if (node.compareTo(elements.get(i)) <= 0) { //sort vo rastecki redosled
                break; //da go zapamtime i (na koja pozicija da go stavime noviot element)
            }
        }
        elements.add(i, node);
    }

    public T remove() {
        //го враќа елементот со најголем приоритет и го брише од редот.
        //Ако редот е празен се враќа null.
        if (elements.size() == 0) return null;
        return elements.remove(elements.size()-1).item;
    }
}

public class PriorityQueueTest {
    public static void main(String[] args) {
        PriorityQueue<String> queue = new PriorityQueue<>();
        queue.add("sreden", 50);
        queue.add("najvisok", 100);
        queue.add("najnizok", 10);
        queue.add("sreden1", 49);
        queue.add("sreden3", 51);

        String element;
        while ((element = queue.remove()) != null) { //vadenje na elementi od redicata
            System.out.println(element);
        }
    }
}
