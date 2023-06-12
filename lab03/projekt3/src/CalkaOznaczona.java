import java.util.HashMap;
import java.util.function.Function;

public class CalkaOznaczona {

    private static double[] stworzWezly(double min, double max, int liczbaKrokow) {
        double[] wezly = new double[liczbaKrokow];
        double step = (max - min) / (liczbaKrokow - 1);
        for (int i = 0; i < liczbaKrokow; i++) {
            double wartosc = min + i * step;
            wezly[i] = wartosc;
        }
        return wezly;
    }

    public static double[][] zamien(double [][] dane){
        int r = dane.length;
        int c = dane[0].length;
        double[][] result = new double[c][r];

        for(int i = 0; i < r; i++){
            for(int j = 0; j < c; j++){
                result[j][i] = dane[i][j];
            }
        }
        return result;
    }

    public static MySparseMatrix buildTridiagonalMatrix(double[] lambda, double[] mi, double[] lambdaLast) {
        int n = lambda.length;

        if (n != mi.length || n != lambdaLast.length) {
            throw new IllegalArgumentException("Długość wektorów lambda, mi i lambdaLast musi być taka sama.");
        }

        MySparseMatrix matrix = new MySparseMatrixMap(n, n, new HashMap<>());

        for (int i = 0; i < n; i++) {
            matrix.setElement(i, i, 2);
            if (i > 0) {
                matrix.setElement(i, i - 1, lambda[i]);
            }
            if (i < n - 1) {
                matrix.setElement(i, i + 1, mi[i + 1]);
            }
        }

        // Ostatni wiersz
        matrix.setElement(n - 1, n - 1, 2);
        matrix.setElement(n - 1, n - 2, lambdaLast[n - 1]);

        return matrix;
    }

    public static double metodaTrapezow(double[] x, double[] y) {
        if (x.length != y.length) {
            throw new IllegalArgumentException("Długość tablicy węzłów musi być równa długości tablicy wartości.");
        }
        int n = x.length;
        double suma = 0.0;
        for (int i = 1; i < n; i++) {
            double szerokoscOdcinka = x[i] - x[i - 1];
            double sumaWartosci = y[i] + y[i - 1];
            suma += 0.5 * szerokoscOdcinka * sumaWartosci;
        }
        return suma;
    }

    public static double metodaSimpsona(double[] x, double[] y) {
        if (x.length != y.length) {
            throw new IllegalArgumentException("Długość tablicy węzłów musi być równa długości tablicy wartości.");
        }
        int n = x.length;
        if (n % 2 != 1) {
            throw new IllegalArgumentException("Długość tablicy węzłów musi być nieparzysta.");
        }
        double suma = 0.0;
        for (int i = 0; i < n - 2; i += 2) {
            double szerokoscOdcinka = x[i + 2] - x[i];
            double sumaWartosci = y[i] + 4 * y[i + 1] + y[i + 2];
            suma += (szerokoscOdcinka / 3) * sumaWartosci;
        }
        return suma / 2;
    }

    public static double metodaCSI(double[] x, double[] y) {
        if (x.length != y.length) {
            throw new IllegalArgumentException("Długość tablicy węzłów musi być równa długości tablicy wartości.");
        }

        int n = x.length;
        if (n < 3) {
            throw new IllegalArgumentException("Minimalna długość tablicy węzłów to 3.");
        }

        double[] h = new double[n - 1];
        double[] delta = new double[n - 1];
        double[] lambda = new double[n];
        double[] mi = new double[n];
        double[] d = new double[n];
        double[] lambdaLast = new double[n];

        // Obliczanie różnic między węzłami
        for (int i = 0; i < n - 1; i++) {
            h[i] = x[i + 1] - x[i];
            delta[i] = (y[i + 1] - y[i]) / h[i];
        }

        // Obliczanie współczynników dla tridiagonalnego układu równań
        lambda[0] = 1;
        for (int i = 1; i < n - 1; i++) {
            lambda[i] = h[i - 1] / (h[i - 1] + h[i]);
            mi[i] = 1 - lambda[i];
            d[i] = 6 * (delta[i] - delta[i - 1]) / (h[i - 1] + h[i]);
        }
        lambda[n - 1] = 0;
        lambdaLast[n - 1] = 1;  // Ustawienie ostatniego elementu wektora lambdaLast na 1
        d[0] = 0;
        d[n - 1] = 0;

        // Rozwiązanie tridiagonalnego układu równań za pomocą eliminacji Gaussa
        MySparseMatrix matrix = buildTridiagonalMatrix(lambda, mi, lambdaLast);
//        matrix.print();
        double[] m = Gauss.solveWithPivot(matrix, d);

        // Obliczanie wartości całki na podstawie interpolacji sklejanej
        double suma = 0.0;
        for (int i = 0; i < n - 1; i++) {
            double a = y[i];
            double b = delta[i] - (2 * m[i] + m[i + 1]) * h[i] / 6;
            double c = m[i] / 2;
            double d_ = (m[i + 1] - m[i]) / (6 * h[i]);
            double x0 = x[i];
            double x1 = x[i + 1];

            double h3 = Math.pow(h[i], 3);
            suma += (a * (x1 - x0) + b * (Math.pow(x1, 2) - Math.pow(x0, 2)) / 2
                    + c * (Math.pow(x1, 3) - Math.pow(x0, 3)) / 3 + d_ * (Math.pow(x1, 4) - Math.pow(x0, 4)) / 4) / h3;
        }

        return suma;
    }

    // Funkcje testowe

    // 2 * e^{{sin(x)}^2} * sin(x) * cos(x)
    public static double f1(double x) {
        return 2 * Math.exp(Math.pow(Math.sin(x), 2)) * Math.cos(x) * Math.sin(x);
    }

    // e^{{sin(x)}^2}
    public static double F1(double x) {
        return Math.exp(Math.pow(Math.sin(x), 2));
    }

    // -3 * x^2 * e^{cos(x^3)} * sin(x^3)
    public static double f2(double x) {
        return -3 * Math.pow(x, 2) * Math.exp(Math.cos(Math.pow(x, 3))) * Math.sin(Math.pow(x, 3));
    }

    // e^{cos(x^3)}
    public static double F2(double x) {
        return Math.exp(Math.cos(Math.pow(x, 3)));
    }

    // -6 * x^2 * e^{cos(x^3)^2} * cos(x^3) * sin(x^3) * cos(e^{cos(x^3)^2})
    public static double f3(double x) {
        double e = Math.exp(Math.pow(Math.cos(Math.pow(x, 3)),2));
        return -6 * Math.pow(x, 2) * e * Math.cos(Math.pow(x, 3)) *
                Math.sin(Math.pow(x, 3)) * Math.cos(e);
    }

    // sin(e^{cos(x^3)^2})
    public static double F3(double x) {
        return Math.sin(Math.exp(Math.pow(Math.cos(Math.pow(x, 3)),2)));
    }

    public static double[] obliczBladWGranicach(Function<Double, Double> f, Function<Double, Double> F,
                                                  double min, double max, int n){
        double[] blad = new double[3];
        double[] arg = stworzWezly(min, max, n);

        double[] valsf = new double[n];

        for (int i = 0; i < n; i++) {
            valsf[i] = f.apply(arg[i]);
        }
        double expected = F.apply(max) - F.apply(min);
        blad[0] = Math.abs(metodaTrapezow(arg, valsf) - expected);
        blad[1] = Math.abs(metodaSimpsona(arg, valsf) - expected);
//        blad[2] = Math.abs(metodaCSI(arg, valsf) - expected);
//        blad[1] = 10E-15;
//        System.out.println(blad[0] + ", " + blad[1] + ", " + blad[2]);

        return blad;
    }

    public static void main(String[] args) {
        double start = -25.0; // Dolny limit całkowania
        double stop = 25.0;
        int iter = 1001; // Liczba iteracji
        double size = (stop - start) / iter;
        int n = 201; // Liczba podziałów przedziału całkowania, ! nieparzysta !
        Function<Double, Double> f = CalkaOznaczona::f3;
        Function<Double, Double> F = CalkaOznaczona::F3;

        double[][] bledy = new double[iter][3];
        double[] xStart = new double[iter];
        double[] nstart = new double[iter];
        int index = 0 ;

        for(int j = 3 ; j < n; j=j+2 ) {
                double min = start * size;
                double max = start  * size + size;
                    nstart[index]= j ;
                    bledy[index] = obliczBladWGranicach(f, F, min, max, j);
                    index++;
//                    xStart[i] = i * size + start;
            }

//        for (double[] blad : bledy){
//            System.out.println(blad[0] + ", " + blad[1] + ", " + blad[2]);
//        }

        Wykres.rysujWykres(zamien(bledy), nstart, false);
    }





}
