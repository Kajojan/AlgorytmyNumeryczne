public interface MySparseMatrix {
        int getRows();

        int getColumns();

        double getElement(int row, int column);

        void setElement(int row, int column, double value);

        void swapRows(int maxIndex, int pivot);

        double[][] getMatrix();

        void print();
}
