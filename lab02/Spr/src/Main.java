import Jama.Matrix;
import java.util.Arrays;

public class Main {
    public static final int ITERATIONS = 100;
    public static void main(String[] args) {
        correctness();
//        efficiency();
    }

    public static double[] avgErrors(double[][] matrix, double[][] vector){
        Matrix A = new Matrix(matrix);
        Matrix X = new Matrix(vector);
        MysparseMatrixMap Am1 = new MysparseMatrixMap(matrix);
        MysparseMatrixMap Am2 = new MysparseMatrixMap(matrix);
        Matrix B = A.times(X);
        double[] Bm = matrixToVector(B.getArray());
        double[] result = matrixToVector(vector);
        double[] result1 = Gauss.solveNoPivot(Am1, Bm);
        double[] result2 = Gauss.solveWithPivot(Am2, Bm);
        double[][] errors = new double[2][result.length];
        for (int i = 0; i < result.length; i++) {
            errors[0][i] = Math.abs(result[i] - result1[i]);
            errors[1][i] = Math.abs(result[i] - result2[i]);
        }
        double averageError1 = Arrays.stream(errors[0]).average().orElse(0.0);
        double averageError2 = Arrays.stream(errors[1]).average().orElse(0.0);
        double[] averageErrors = {averageError1 * 1E10, averageError2 * 1E10};
        return averageErrors;
    }

    public static void correctness(){
        double[][] errors = new double[2][ITERATIONS];
        double tries[] = new double[ITERATIONS];
        for (int i = 0; i < ITERATIONS; i++){
            double[][] denseMatrixInput = Generator.denseMatrix(10, 10);
            double[][] vectorInput = Generator.denseMatrix(10, 1);
            double[] error = avgErrors(denseMatrixInput, vectorInput);
            errors[0][i] = error[0];
            errors[1][i] = error[1];
            tries[i] = i;
        }
        Wykres.rysujWykres(errors, tries, true);
    }

    public static double[] measureExecutionTime(double[][] matrix, double[][] vector) {
        long startTime, endTime;
        Matrix A = new Matrix(matrix);
        Matrix X = new Matrix(vector);
        MysparseMatrixMap Am1 = new MysparseMatrixMap(matrix);
        MysparseMatrixMap Am2 = new MysparseMatrixMap(matrix);
        Matrix B = A.times(X);
        double[] Bm = matrixToVector(B.getArray());

        startTime = System.nanoTime();
        Gauss.solveNoPivot(Am1, Bm);
        endTime = System.nanoTime();
        double time0 = (endTime - startTime) / 1E9;

        startTime = System.nanoTime();
        Gauss.solveWithPivot(Am2, Bm);
        endTime = System.nanoTime();
        double time1 = (endTime - startTime) / 1E9;

        startTime = System.nanoTime();
        A.solve(B).getArray();
        endTime = System.nanoTime();
        double time2 = (endTime - startTime) / 1E9;

        double[] time = new double[3];
        time[0] = time0;
        time[1] = time1;
        time[2] = time2;
        return time;
    }

    public static void efficiency(){
        double[][] times = new double[3][ITERATIONS];
        double tries[] = new double[ITERATIONS];
        for (int i = 0; i < ITERATIONS; i++){
            double[][] denseMatrixInput = Generator.sparseMatrix(100, 10);
            double[][] vectorInput = Generator.denseMatrix(100, 1);
            double[] time = measureExecutionTime(denseMatrixInput, vectorInput);
//            System.out.println(Arrays.toString(time));
            times[0][i] = time[0];
            times[1][i] = time[1];
            times[2][i] = time[2];
            tries[i] = i;
        }
        Wykres.rysujWykres(times, tries, false);
    }

    public static double[] matrixToVector(double[][] matrix) {
        int rows = matrix.length;
        if (rows == 0) {
            return new double[0];
        }
        int columns = matrix[0].length;
        double[] vector = new double[rows];
        for (int i = 0; i < rows; i++) {
            if (columns != 1) {
                throw new IllegalArgumentException("Input matrix must have exactly one column.");
            }
            vector[i] = matrix[i][0];
        }
        return vector;
    }
}