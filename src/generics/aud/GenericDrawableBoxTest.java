package generics.aud;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

interface Drawable {
    void draw();
}

class Box2<T extends Drawable /*& Comparable<T>*/> {
    List<T> elements;
    static Random RANDOM = new Random();

    public Box2() {
        elements = new ArrayList<>();
    }

    public void add(T element) {
        elements.add(element);
    }

    public boolean isEmpty() {
        return elements.size() == 0;
    }

    public void drawElement() {
        if (isEmpty()) return;

        T element = elements.remove(RANDOM.nextInt(elements.size()));
        element.draw();
    }
}

public class GenericDrawableBoxTest {

    static Random RANDOM = new Random();

    public static void main(String[] args) {
        Box2<Drawable> box = new Box2<>();
        for (int i = 0; i < 100; i++) {
            box.add(() -> System.out.println(RANDOM.nextInt(50))); //() -> bidejkji nemame argumenti
        }

        for (int i = 0; i < 50; i++) {
            box.drawElement();
        }
    }
}
