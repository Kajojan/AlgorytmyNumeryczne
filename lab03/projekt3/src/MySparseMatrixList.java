import java.util.*;

public class MySparseMatrixList implements MySparseMatrix{
    private final int rows;
    private final int columns;
    private final List<Map<Integer, Double>> matrix;

    public MySparseMatrixList(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.matrix = new ArrayList<>();
        for(int i =0; i<= rows; i++){
            this.matrix.add(new HashMap<>());
        }
    }
    public MySparseMatrixList(double[][] matrix) {
        this.rows = matrix.length;
        this.columns = matrix[0].length;
        this.matrix = new ArrayList<>();
        for(int i = 0; i < rows; i++){
            Map<Integer, Double> rowMap = new HashMap<>();
            for(int j = 0; j < columns; j++){
                if(matrix[i][j] != 0.0){
                    rowMap.put(j, matrix[i][j]);
                }
            }
            this.matrix.add(rowMap);
        }
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
        if( this.matrix.get(row).get(column) != null){
            return this.matrix.get(row).get(column);
        }else{
            return 0.0;
        }

    }

    @Override
    public void setElement(int row, int column, double value) {
        if (value != 0 ) {
            this.matrix.get(row).put(column, value);
        }else{
            this.matrix.get(row).remove(column);
        }

    }
    public double[][] getMatrix() {
        return null;
    }

    @Override
    public void print() {
        double[][] denseMatrix = getMatrix();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                System.out.printf("%.5f ", denseMatrix[i][j]);
            }
            System.out.println();
        }
    }
}