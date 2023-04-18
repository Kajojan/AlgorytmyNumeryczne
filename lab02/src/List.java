import java.util.Arrays;

public class List implements DataInterface{
    private final int rows;
    private final int columns;
    private final double[][] matrix;

    public List(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.matrix = new double[rows][columns];
    }
    public void swapRows(int row1, int row2) {
        if (row1 == row2) return;
        for (int i = 0; i < getColumns(); i++) {
            double temp = getElement(row1, i);
            setElement(row1, i, getElement(row2, i));
            setElement(row2, i, temp);
        }
    }


    @Override
    public int getRows() {
        return this.rows;
    }

    @Override
    public int getColumns() {
        return this.columns;
    }

    @Override
    public double getElement(int row, int column) {
        if( !Double.isNaN(this.matrix[row][column])){
             return this.matrix[row][column];
         }else{
             return 0;
        }
    }

    @Override
    public void setElement(int row, int column, double value) {
        if (value != 0) {
            this.matrix[row][column] = value;
        }else{
            this.matrix[row][column]=Double.NaN;
        }
    }

    @Override
    public void addRowMultiple(int to, int from, double multiplier) {

    }

    @Override
    public double[] solveEquations(double[] b) {
        // sprawdzenie, czy macierz jest kwadratowa i ma tyle samo wierszy co wektor prawych stron
        if (this.rows != this.columns || this.columns != b.length) {
            throw new IllegalArgumentException("Matrix is not square or has different number of rows than the right-hand side vector");
        }

        // wykonanie eliminacji Gaussa na kopii macierzy i wektora prawych stron
        List copy = new List(this.rows, this.columns);
        double[] bCopy = Arrays.copyOf(b, b.length);
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.columns; j++) {
                copy.setElement(i, j, this.getElement(i, j));
            }
        }
//        GaussianElimination.gaussianElimination(copy);

        // obliczenie rozwiązań układu równań Ax = b za pomocą metody podstawiania wstecznego
        double[] x = new double[this.columns];
        for (int i = this.columns - 1; i >= 0; i--) {
            double sum = 0.0;
            for (int j = i + 1; j < this.columns; j++) {
                sum += copy.getElement(i, j) * x[j];
            }
            x[i] = (bCopy[i] - sum) / copy.getElement(i, i);
        }
        return x;
    }
}
