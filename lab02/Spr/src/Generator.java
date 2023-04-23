import java.util.Random;

public class Generator {
    public static double rand() {
        Random random = new Random();
        int r = random.nextInt(65536) - 32768;
        return (double) r / 32768.0;
    }
    public static double[][] denseMatrix(int rows, int cols) {
        double[][] matrix = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = rand();
            }
        }
        return matrix;
    }
    public static double[][] bandMatrix(int size, int bandWidth) {
        double[][] matrix = new double[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (Math.abs(i - j) <= bandWidth) {
                    matrix[i][j] = rand();
                } else {
                    matrix[i][j] = 0.0;
                }
            }
        }
        return matrix;
    }
    public static double[][] sparseMatrix(int size, int fillPercentage) {
        double[][] matrix = new double[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == j || Math.random() < (double) fillPercentage / 100.0) {
                    matrix[i][j] = rand();
                } else {
                    matrix[i][j] = 0.0;
                }
            }
        }
        return matrix;
    }
}
