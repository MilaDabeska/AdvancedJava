package second.labs;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.IntStream;

// X

class InsufficientElementsException extends Exception {
    public InsufficientElementsException() {
        super("Insufficient number of elements");
    }
}

class InvalidRowNumberException extends Exception {
    public InvalidRowNumberException() {
        super("Invalid row number");
    }
}

class InvalidColumnNumberException extends Exception {
    public InvalidColumnNumberException() {
        super("Invalid column number");
    }
}

final class DoubleMatrix {

    private final double[][] matrix;
    private final int rows, columns;

    public DoubleMatrix(double[] a, int m, int n) throws InsufficientElementsException {
        matrix = new double[m][n];
        this.rows = m;
        this.columns = n;
        int k = 0;

        if (a.length < (m * n)) throw new InsufficientElementsException();
        else if (a.length > (m * n)) {
            k = (a.length) - (m * n);
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = a[k++];
            }
        }

        if (a.length == (m * n)) {
            k = 0;
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    matrix[i][j] = a[k++];
                }
            }
        }
    }

    public String getDimensions() {
        return String.format("[%d x %d]", rows, columns);
    }

    public int rows() {
        return rows;
    }

    public int columns() {
        return columns;
    }

    public double maxElementAtRow(int row) throws InvalidRowNumberException {
        //кој го враќа максималниот елемент во дадениот ред
        if (row < 0 || row >= rows) throw new InvalidRowNumberException();
//        double max = matrix[row][0];
//        for (int i = 1; i < columns; i++) {
//            if (matrix[row][i] > max)
//                max = matrix[row][i];
//        }
//        return max;
        return Arrays.stream(matrix[row - 1]).max().getAsDouble();
    }

    public double maxElementAtColumn(int column) throws InvalidColumnNumberException {
        //кој го враќа максималниот елемент во дадената колона
        if (column < 0 || column >= columns) throw new InvalidColumnNumberException();
//        double max = matrix[0][column];
//        for (int i = 1; i < rows; i++) {
//            if (matrix[i][column] > max)
//                max = matrix[i][column];
//        }
//        return max;
        return IntStream.range(0, rows).mapToDouble(i -> matrix[i][column - 1]).max().getAsDouble();
    }

    public double sum() {
//        double sum = 0;
//        for (int i = 0; i < rows; i++) {
//            for (int j = 0; j < columns; j++) {
//                sum += matrix[i][j];
//            }
//        }
//        return sum;
        return Arrays.stream(matrix).mapToDouble(row -> Arrays.stream(row).sum()).sum();
    }

    public double[] toSortedArray() {
        //вредностите се сортирани во опаѓачки редослед
//        double[] array = new double[rows * columns];
//        int k = 0;
//
//        for (int i = 0; i < rows; i++) {
//            for (int j = 0; j < columns; j++) {
//                array[k] = matrix[i][j];
//                k++;
//            }
//        }
//        Arrays.sort(array);
//        double tmp;
//        for (int i = 0; i < array.length / 2; i++) {
//            tmp = array[i];
//            array[i] = array[array.length - i - 1];
//            array[array.length - i - 1] = tmp;
//            System.out.println(tmp);
//        }
//        return array;
        double[] array = Arrays.stream(matrix).flatMapToDouble(Arrays::stream).toArray();
        Arrays.sort(array);
        return IntStream.range(0, array.length).mapToDouble(i -> array[array.length - i - 1]).toArray();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                sb.append(String.format(".2f", matrix[i][j]));
                if (columns - j != 1) sb.append("\t");
            }
            if (rows - i != 1) sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (getClass() != o.getClass()) return false;
        DoubleMatrix other = (DoubleMatrix) o;
        if (!Arrays.deepEquals(matrix, other.matrix)) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + Arrays.deepHashCode(matrix);
        return result;
    }
}

class MatrixReader {

    public static DoubleMatrix read(InputStream input) throws Exception {
        Scanner scanner = new Scanner(input);
        int rows = scanner.nextInt();
        int columns = scanner.nextInt();
        double[] matrix = new double[rows * columns];

        int i = 0;
        while (scanner.hasNextDouble()) {
            matrix[i++] = scanner.nextDouble();
        }
//        for (int i = 0; i < rows * columns; i++) {
//            matrix[i] = scanner.nextDouble();
//        }
//        IntStream.range(0, rows * columns).forEach(i -> matrix[i] = scanner.nextDouble());
        return new DoubleMatrix(matrix, rows, columns);
    }

}

public class DoubleMatrixTester {

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        int tests = scanner.nextInt();
        DoubleMatrix fm = null;

        double[] info = null;

        DecimalFormat format = new DecimalFormat("0.00");

        for (int t = 0; t < tests; t++) {

            String operation = scanner.next();

            switch (operation) {
                case "READ": {
                    int N = scanner.nextInt();
                    int R = scanner.nextInt();
                    int C = scanner.nextInt();

                    double[] f = new double[N];

                    for (int i = 0; i < f.length; i++)
                        f[i] = scanner.nextDouble();

                    try {
                        fm = new DoubleMatrix(f, R, C);
                        info = Arrays.copyOf(f, f.length);

                    } catch (InsufficientElementsException e) {
                        System.out.println("Exception caught: " + e.getMessage());
                    }

                    break;
                }

                case "INPUT_TEST": {
                    int R = scanner.nextInt();
                    int C = scanner.nextInt();

                    StringBuilder sb = new StringBuilder();

                    sb.append(R + " " + C + "\n");

                    scanner.nextLine();

                    for (int i = 0; i < R; i++)
                        sb.append(scanner.nextLine() + "\n");

                    fm = MatrixReader.read(new ByteArrayInputStream(sb
                            .toString().getBytes()));

                    info = new double[R * C];
                    Scanner tempScanner = new Scanner(new ByteArrayInputStream(sb
                            .toString().getBytes()));
                    tempScanner.nextDouble();
                    tempScanner.nextDouble();
                    for (int z = 0; z < R * C; z++) {
                        info[z] = tempScanner.nextDouble();
                    }

                    tempScanner.close();

                    break;
                }

                case "PRINT": {
                    System.out.println(fm.toString());
                    break;
                }

                case "DIMENSION": {
                    System.out.println("Dimensions: " + fm.getDimensions());
                    break;
                }

                case "COUNT_ROWS": {
                    System.out.println("Rows: " + fm.rows());
                    break;
                }

                case "COUNT_COLUMNS": {
                    System.out.println("Columns: " + fm.columns());
                    break;
                }

                case "MAX_IN_ROW": {
                    int row = scanner.nextInt();
                    try {
                        System.out.println("Max in row: "
                                + format.format(fm.maxElementAtRow(row)));
                    } catch (InvalidRowNumberException e) {
                        System.out.println("Exception caught: " + e.getMessage());
                    }
                    break;
                }

                case "MAX_IN_COLUMN": {
                    int col = scanner.nextInt();
                    try {
                        System.out.println("Max in column: "
                                + format.format(fm.maxElementAtColumn(col)));
                    } catch (InvalidColumnNumberException e) {
                        System.out.println("Exception caught: " + e.getMessage());
                    }
                    break;
                }

                case "SUM": {
                    System.out.println("Sum: " + format.format(fm.sum()));
                    break;
                }

                case "CHECK_EQUALS": {
                    int val = scanner.nextInt();

                    int maxOps = val % 7;

                    for (int z = 0; z < maxOps; z++) {
                        double work[] = Arrays.copyOf(info, info.length);

                        int e1 = (31 * z + 7 * val + 3 * maxOps) % info.length;
                        int e2 = (17 * z + 3 * val + 7 * maxOps) % info.length;

                        if (e1 > e2) {
                            double temp = work[e1];
                            work[e1] = work[e2];
                            work[e2] = temp;
                        }

                        DoubleMatrix f1 = fm;
                        DoubleMatrix f2 = new DoubleMatrix(work, fm.rows(),
                                fm.columns());
                        System.out
                                .println("Equals check 1: "
                                        + f1.equals(f2)
                                        + " "
                                        + f2.equals(f1)
                                        + " "
                                        + (f1.hashCode() == f2.hashCode() && f1
                                        .equals(f2)));
                    }

                    if (maxOps % 2 == 0) {
                        DoubleMatrix f1 = fm;
                        DoubleMatrix f2 = new DoubleMatrix(new double[]{3.0, 5.0,
                                7.5}, 1, 1);

                        System.out
                                .println("Equals check 2: "
                                        + f1.equals(f2)
                                        + " "
                                        + f2.equals(f1)
                                        + " "
                                        + (f1.hashCode() == f2.hashCode() && f1
                                        .equals(f2)));
                    }

                    break;
                }

                case "SORTED_ARRAY": {
                    double[] arr = fm.toSortedArray();

                    String arrayString = "[";

                    if (arr.length > 0)
                        arrayString += format.format(arr[0]) + "";

                    for (int i = 1; i < arr.length; i++)
                        arrayString += ", " + format.format(arr[i]);

                    arrayString += "]";

                    System.out.println("Sorted array: " + arrayString);
                    break;
                }
            }
        }
        scanner.close();
    }
}
