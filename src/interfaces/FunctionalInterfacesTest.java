package interfaces;

import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class FunctionalInterfacesTest {
    public static void main(String[] args) {

        Supplier<Integer> integerSupplier = () -> new Random().nextInt();

//        Supplier<Integer> integerSupplier = new Supplier<Integer>() {
//            @Override
//            public Integer get() {
//                return new Random().nextInt(); //vrakja rezultat
//            }
//        };

        Consumer<Integer> integerConsumer = integer -> System.out.println(integer);

//        Consumer<Integer> integerConsumer = new Consumer<Integer>() {
//            @Override
//            public void accept(Integer integer) {
//                System.out.println(integer); //prima 1 argument
//            }
//        };

        Predicate<Integer> integerPredicate = integer -> integer < 5;

//        Predicate<Integer> integerPredicate = new Predicate<Integer>() {
//            @Override
//            public boolean test(Integer integer) {
//                return integer < 5; //prima vrednost,vrakja boolean
//            }
//        };

        Function<Integer, Integer> function = integer -> integer + 5;

//        Function<Integer,Integer> function=new Function<Integer, Integer>() {
//            @Override
//            public Integer apply(Integer integer) {
//                return integer+5; //moze da primi edno,a da vrati dr
//            }
//        };
    }
}
