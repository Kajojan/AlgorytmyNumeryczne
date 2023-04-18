public interface DataInterface {
        // Zwraca liczbę wierszy macierzy
        int getRows();

        // Zwraca liczbę kolumn macierzy
        int getColumns();

        // Zwraca wartość elementu macierzy o podanych współrzędnych
        double getElement(int row, int column);

        // Ustawia wartość elementu macierzy o podanych współrzędnych
        void setElement(int row, int column, double value);

        // Wykonuje operację dodawania do wiersza "to" wiersza "from" pomnożonego przez mnożnik
        void addRowMultiple(int to, int from, double multiplier);

        // Zwraca wektor wynikowy równania Ax=b dla macierzy w postaci tablicy double'ów
        double[] solveEquations(double[] b);

        void swapRows(int maxIndex, int pivot);
}
