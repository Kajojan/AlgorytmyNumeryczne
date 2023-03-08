import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

public class Main {

public static double silnia(double x){
    double suma=1;
    for(int i =1 ; i<=x; i++){
        suma*=i;
    }
    return suma;
}

    public static double[] func(double x, int n){
            double dzielnik = 1;

            double[] sum =new double[n];

            for(int i=1 ; i <= n ; i++){
                double mianownik = x;
                dzielnik=silnia(i);

                for(int j = 2 ;j<=i ; j++){
                    mianownik=mianownik*x;
                }
                sum[i-1]=mianownik/dzielnik;
            }
        return sum;

    }
    public static double V1(double x, int n){
        double dzielnik = 1;

        double[] sum =func(x,n);
        double wynik = 1;
        for(int i=0 ; i < sum.length ; i++){
            wynik+=sum[i];
        }
    return wynik;

    }
    public static double V2(double x, int n){
        double[] sum =func(x,n);
        double wynik = 0;
        for(int i=sum.length-1 ; i >= 0  ; i--){
            wynik+=sum[i];
        }
        return wynik+1;

    }

    public static double V3(double x, int n){
        double wyraz = 1;
        double wynik = 1;
        for(int i=1; i < n  ; i++){
            wyraz =(wyraz * x) / i;
            wynik+=wyraz;
        }
        return wynik;

    }

    public static double V4(double x, int n){
        double wynik = 0;
        double wyraz = 1;
        double[] tab = new double[n];
        tab[0]=1;
        for(int i=1; i < n   ; i++){
            wyraz =(wyraz * x) / i;
            tab[i]=wyraz;
        }
        for(int i = tab.length-1; i >= 0 ; i--){
            wynik+=tab[i];
        }
        return wynik;

    }


    public static void main(String[] args) {
        int ilosc_probek=1000;
        int n=100;
        double max = 2;
        double min = 1;
        double[][] wzgledny = new double[4][ilosc_probek];
        double[][] bezWzgledny = new double[4][ilosc_probek];

        for(int i = 0 ; i < ilosc_probek ; i++){
            double probka = i/999.0 *(max-1) + min ;
            bezWzgledny[0][i]=Math.abs(V1(probka, n) - Math.exp(probka));
            bezWzgledny[1][i]=Math.abs(V1(probka, n) - Math.exp(probka));
            bezWzgledny[2][i]=Math.abs(V1(probka, n) - Math.exp(probka));
            bezWzgledny[3][i]=Math.abs(V1(probka, n) - Math.exp(probka));

            wzgledny[0][i]=Math.abs(bezWzgledny[0][i]/Math.exp(probka));
            wzgledny[1][i]=Math.abs(bezWzgledny[1][i]/Math.exp(probka));
            wzgledny[2][i]=Math.abs(bezWzgledny[2][i]/Math.exp(probka));
            wzgledny[3][i]=Math.abs(bezWzgledny[3][i]/Math.exp(probka));

        }

    }
}
