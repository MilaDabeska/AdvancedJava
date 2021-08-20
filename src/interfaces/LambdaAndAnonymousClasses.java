package interfaces;

interface Operation {
    double execute(double x, double y);
}

public class LambdaAndAnonymousClasses {
    public static void main(String[] args) {

        Operation addition = new Operation() { //anonymous class
            @Override
            public double execute(double x, double y) {
                return x + y;
            }
        };

        Operation subtraction = (x, y) -> x - y; //lambda
        Operation multiplication = (x, y) -> x * y;
        Operation division = (x, y) -> x / y;
    }
}
