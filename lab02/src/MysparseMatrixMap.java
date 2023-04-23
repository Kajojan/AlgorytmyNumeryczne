
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


    @Override
    public void swapRows(int maxIndex, int pivot) {

        for (int j = 0; j < this.columns; j++) {
            Pair<Integer, Integer> key1 = new Pair<>(maxIndex, j);
            Pair<Integer, Integer> key2 = new Pair<>(pivot, j);
            if (this.elements.containsKey(key2) && this.elements.containsKey(key1)) {
                double temp = this.elements.get(key2);
                this.elements.put(key2, this.elements.get(key1));
                this.elements.put(key1, temp);
            } else if (this.elements.containsKey(key1) && !this.elements.containsKey(key2)) {
                this.elements.put(key2, this.elements.get(key1));
                this.elements.remove(key1);
            } else if (this.elements.containsKey(key2) && !this.elements.containsKey(key1)) {
                this.elements.put(key1, this.elements.get(key2));
                this.elements.remove(key2);
            }

        }

    }

}
