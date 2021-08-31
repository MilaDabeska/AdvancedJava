package designpatterns.singleton;

class Singleton {
    String name;
    static Singleton obj = new Singleton();

    private Singleton() { //mora private 
        this.name = "Mila";
    }

    public static Singleton getInstance() {
        return obj;
    }
}

public class SingletonTest {
    public static void main(String[] args) {
        Singleton obj = Singleton.getInstance();
        System.out.println(obj.name);
    }
}
