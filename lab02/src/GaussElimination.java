import java.util.Arrays;
import java.util.HashMap;

public class GaussElimination {

    public static void main(String[] args) {
        // przykładowa macierz układu równań
//        List a = new List(3,3);
        MysparseMatrixMap a = new MysparseMatrixMap(4,4, new HashMap<>());
        double[] value = new double[]{2,1,1,1, -1,-2,-1,1, 1,1,-2,-3, 4,-2,0,4};
        for(int i = 0; i< a.getRows(); i++){
            for(int j = 0 ; j< a.getColumns(); j++)
                a.setElement(i,j,value[i*4+j]);
        }
        double[] b = {0,1,2,2};
        double[] x = gaussElimination(a, b);
        System.out.println("Rozwiązanie: " + Arrays.toString(x));
    }

    public static double[] gaussElimination(DataInterface  a, double[] b) {
        int n = b.length;

        double[] bCopy = Arrays.copyOf(b, n);
        // eliminacja Gaussa
        for (int i = 0; i < n; i++) {
//            int maxRow = i;
//            while (maxRow < n && a.getElement(maxRow,i) == 0) {
//                maxRow++;
//            }
//            if (maxRow == n) {
//                throw new IllegalStateException("Układ równań jest sprzeczny.");
//            }

//             wybierz element podstawowy

            double maxElem = Math.abs(a.getElement(i,i));
            int maxRow = i;
            for (int k = i + 1; k < n; k++) {
                if (Math.abs(a.getElement(k,i)) > maxElem) {
                    maxElem = Math.abs(a.getElement(k,i));

                }
            }
            // zamień wiersze

            a.swapRows(maxRow,i);

            double temp2 = bCopy[maxRow];
            bCopy[maxRow] = bCopy[i];
            bCopy[i] = temp2;

            // eliminacja
            for (int k = i + 1; k < n; k++) {
                double c = -a.getElement(k,i) / a.getElement(i,i);
                for (int j = i; j < n; j++) {
                    if (i == j) {
                        a.setElement(k,j,0);
                    } else {
                        a.setElement(k,j,(c * a.getElement(i,j) + a.getElement(k,j)));
                    }
                }
                bCopy[k] += c * bCopy[i];
            }
        }
        System.out.println("Matrix: ");
        for(int i = 0; i<a.getRows(); i++) {
            for (int j = 0; j <a.getColumns(); j++) {
                System.out.print(a.getElement(i, j) + " ");
            }
            System.out.println(" ");
        }
        System.out.println(" ");

        // wsteczna substytucja
        double[] x = new double[n];
        for (int i = n - 1; i >= 0; i--) {
            x[i] = bCopy[i] / a.getElement(i,i);
            for (int k = i - 1; k > 0; k--) {
                bCopy[k] = bCopy[k] - (a.getElement(k,i) * x[i]);
            }

        }
        return x;
    }
}
