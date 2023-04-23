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
        }
    }

}
