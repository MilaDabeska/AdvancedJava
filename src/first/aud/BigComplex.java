package first.aud;

import java.math.BigDecimal;

public class BigComplex {

    private BigDecimal real, imaginary;

    public BigComplex(BigDecimal real, BigDecimal imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    public BigComplex add(BigComplex complex) {
        BigDecimal real = this.real.add(complex.real);
        BigDecimal imaginary = this.imaginary.add(complex.imaginary);

        return new BigComplex(real, imaginary);
    }

    public BigComplex subtract(BigComplex complex) {
        BigDecimal real = this.real.subtract(complex.real);
        BigDecimal imaginary = this.imaginary.subtract(complex.imaginary);

        return new BigComplex(real, imaginary);
    }

    public BigComplex multiply(BigComplex complex) {
        BigDecimal real = this.real.multiply(complex.real);
        BigDecimal imaginary = this.imaginary.multiply(complex.imaginary);

        return new BigComplex(real, imaginary);
    }

    public BigComplex divide(BigComplex complex) {
        BigDecimal real = this.real.divide(complex.real);
        BigDecimal imaginary = this.imaginary.divide(complex.imaginary);

        return new BigComplex(real, imaginary);
    }

    @Override
    public String toString() {
        return String.format("%f , %f",real,imaginary);
    }

    public static void main(String[] args) {
        BigDecimal bd1 = new BigDecimal("63745");
        BigDecimal bd2 = new BigDecimal("-36755");
        BigComplex complex = new BigComplex(bd1, bd2);
        System.out.println(complex.add(complex));
        System.out.println(complex.subtract(complex));
        System.out.println(complex.multiply(complex));
        System.out.println(complex.divide(complex));
    }
}
