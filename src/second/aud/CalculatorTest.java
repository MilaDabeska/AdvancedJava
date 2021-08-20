package second.aud;

import java.util.Scanner;

// STRATEGY PATTERN

interface Strategy {
    double doOperation(double x, double y);
}

class Addition implements Strategy {
    @Override
    public double doOperation(double x, double y) {
        return x + y;
    }
}

class Subtraction implements Strategy {
    @Override
    public double doOperation(double x, double y) {
        return x - y;
    }
}

class Multiplication implements Strategy {
    @Override
    public double doOperation(double x, double y) {
        return x * y;
    }
}

class Division implements Strategy {
    @Override
    public double doOperation(double x, double y) {
        return x / y;
    }
}

class UnknownOperatorException extends Exception {
    public UnknownOperatorException(char operator) {
        super(String.format("unknown operation: %c", operator));
    }
}

class Calculator {

    private double result;
    private Strategy strategy;

    public Calculator() {
        result = 0;
    }

    public String init() {
        return String.format("calculator is on\ninit value is %.2f", result);
    }

    public String execute(char operation, double value) throws UnknownOperatorException {
        if (operation == '+') strategy = new Addition();
        else if (operation == '-') strategy = new Subtraction();
        else if (operation == '*') strategy = new Multiplication();
        else if (operation == '/') strategy = new Division();
        else throw new UnknownOperatorException(operation);

        result = strategy.doOperation(result, value);
        return String.format("result %c %.2f = %.2f", operation, value, result);
    }

    public double getResult() {
        return result;
    }

    @Override
    public String toString() {
        return String.format("updated result = %.2f", result);
    }
}


public class CalculatorTest {

    public static char character(String line) {
        if (line.trim().length() > 0) { //trim() -> skratuva prazni mesta vo desno
            return Character.toLowerCase(line.charAt(0)); //karakter na nulta pozicija
        }
        return '?';
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) { // (Y/N)
            Calculator calculator = new Calculator();
            System.out.println(calculator.init());

            while (true) { // R
                String line = scanner.nextLine();
                char choice = character(line);
                if (choice == 'r') {
                    System.out.printf("final result is: %.2f", calculator.getResult());
                    break;
                }
                String[] parts = line.split("\\s+");
                char operator = parts[0].charAt(0); // +,-,*,/
                double value = Double.parseDouble(parts[1]); //5.0,...

                try {
                    System.out.println(calculator.execute(operator, value));
                    System.out.println(calculator);
                } catch (UnknownOperatorException exception) {
                    System.out.println(exception.getMessage());
                }
            }
            System.out.println(" Again? Y/N");
            String line = scanner.nextLine();
            char choice = character(line);
            if (choice == 'n') break;
        }
    }
}
