import java.util.Arrays;

public class Gauss {

    public static double[] solveWithPivot(MySparseMatrix matrix, double[] vector) {
        int n = vector.length;

        double[] bCopy = Arrays.copyOf(vector, n);
        // eliminacja Gaussa
        for (int i = 0; i < n; i++) {
            double maxElem = Math.abs(matrix.getElement(i, i));
            int maxRow = i;
            for (int k = i + 1; k < n; k++) {
                if (Math.abs(matrix.getElement(k, i)) > maxElem) {
                    maxElem = Math.abs(matrix.getElement(k, i));
                    maxRow=k;
                }
            }
            matrix.swapRows(maxRow,i);

            double temp2 = bCopy[maxRow];
            bCopy[maxRow] = bCopy[i];
            bCopy[i] = temp2;
            // eliminacja
            for (int k = i + 1; k < n; k++) {
                double c = -matrix.getElement(k, i) / matrix.getElement(i, i);
                for (int j = i; j < n; j++) {
                    if (i == j) {
                        matrix.setElement(k, j, 0);
                    } else {
                        matrix.setElement(k, j, (c * matrix.getElement(i, j) + matrix.getElement(k, j)));
                    }
                }
                bCopy[k] += c * bCopy[i];
            }
        }
//        printMatrix(matrix);
        // wsteczna substytucja
        double[] x = new double[n];
        for (int i = n - 1; i >= 0; i--) {
            x[i] = bCopy[i] / matrix.getElement(i, i);
            for (int k = i - 1; k >= 0; k--) {
                bCopy[k] -= matrix.getElement(k, i) * x[i];
            }
        }
        return x;
    }

    public static double[] solveNoPivot(MySparseMatrix matrix, double[] vector) {
        int n = vector.length;

        double[] bCopy = Arrays.copyOf(vector, n);
        // eliminacja Gaussa
        for (int i = 0; i < n; i++) {
            // eliminacja
            for (int k = i + 1; k < n; k++) {
                double c = -matrix.getElement(k, i) / matrix.getElement(i, i);
                for (int j = i; j < n; j++) {
                    if (i == j) {
                        matrix.setElement(k, j, 0);
                    } else {
                        matrix.setElement(k, j, (c * matrix.getElement(i, j) + matrix.getElement(k, j)));
                    }
                }
                bCopy[k] += c * bCopy[i];
            }
        }
        // wsteczna substytucja
        double[] x = new double[n];
        for (int i = n - 1; i >= 0; i--) {
            x[i] = bCopy[i] / matrix.getElement(i, i);
            for (int k = i - 1; k >= 0; k--) {
                bCopy[k] -= matrix.getElement(k, i) * x[i];
            }
        }
        return x;
    }
}
