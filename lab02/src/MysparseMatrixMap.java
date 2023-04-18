
import java.util.Map;


public class MysparseMatrixMap implements DataInterface{
        private final int rows;
        private final int columns;
//        private final Map<Pair<Integer, Integer>, Double> elements;
        private final Map<Pair<Integer, Integer>, Double> elements ;

        public MysparseMatrixMap(int rows, int columns, Map<Pair<Integer, Integer>, Double> elements) {
            this.rows = rows;
            this.columns = columns;
            this.elements = elements;
        }

        public int getRows() {
            return this.rows;
        }

        public int getColumns() {
            return this.columns;
        }

        public double getElement(int row, int column) {
            Pair<Integer, Integer> key = new Pair<>(row, column);
            return this.elements.containsKey(key) ? this.elements.get(key) : 0;
        }

        public void setElement(int row, int column, double value) {
            Pair<Integer, Integer> key = new Pair<>(row, column);
//            System.out.println(row+" "+value + " " + column);
            if (value != 0) {
                this.elements.put(key, value);
//                System.out.println("value:" + this.elements.get(key));

            } else {
                this.elements.remove(key);
            }
        }

        public void addRowMultiple(int to, int from, double multiplier) {
            for (int j = 0; j < columns; j++) {
                double valueTo = getElement(to, j);
                double valueFrom = getElement(from, j);
                setElement(to, j, valueTo + multiplier * valueFrom);
            }
        }

    @Override
    public double[] solveEquations(double[] b) {
        return new double[0];
    }

    @Override
    public void swapRows(int maxIndex, int pivot) {
        if (maxIndex == pivot) return;

        for (int j = 0; j < this.columns; j++) {
            Pair<Integer, Integer> key1 = new Pair<>(maxIndex, j);
            Pair<Integer, Integer> key2 = new Pair<>(pivot, j);
            double temp = this.elements.get(key2);
            this.elements.put(key2, this.elements.get(key1));
            this.elements.put(key1, temp);
        }
    }

    // Metoda solveEquations zostałaby zaimplementowana w osobnej klasie.


}
