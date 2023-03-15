
import java.io.IOException;

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


    public static void main(String[] args) throws IOException {
        int ilosc_probek=1000000;
        int n=100;
        double max = 6;
        double min = -2;
        double[][] wzgledny = new double[4][ilosc_probek];
        double[][] bezWzgledny = new double[4][ilosc_probek];

//
        double[] probka=new double [ilosc_probek];
        for(int i = 0 ; i < ilosc_probek ; i++){
            probka[i]= (i/999999.0 *(max-1) + min );
            System.out.println(i/999999.0 *(max-1) + min );
            bezWzgledny[0][i]=Math.abs(V1(probka[i], n) - Math.exp(probka[i]))*1E+15;
            bezWzgledny[1][i]=Math.abs(V2(probka[i], n) - Math.exp(probka[i]))*1E+15;
            bezWzgledny[2][i]=Math.abs(V3(probka[i], n) - Math.exp(probka[i]))*1E+15;
            bezWzgledny[3][i]=Math.abs(V4(probka[i], n) - Math.exp(probka[i]))*1E+15;

            wzgledny[0][i]=Math.abs(bezWzgledny[0][i]/Math.exp(probka[i]));
            wzgledny[1][i]=Math.abs(bezWzgledny[1][i]/Math.exp(probka[i]));
            wzgledny[2][i]=Math.abs(bezWzgledny[2][i]/Math.exp(probka[i]));
            wzgledny[3][i]=Math.abs(bezWzgledny[3][i]/Math.exp(probka[i]));
        }


        for(int i = 0 ; i < 4 ; i++){
            double sum = 0;
            for (int j = 0; j < ilosc_probek; j++) {
                sum += wzgledny[i][j];
            }
            double srednia = sum/wzgledny[i].length;
            System.out.println(srednia);
        }

        double[][] srednie_wyniki = new double[4][ilosc_probek/1000];
        double [] srednia_probka = new double[ilosc_probek/1000];
        for(int i = 0 ; i < 4 ; i++){
            for (int j = 0; j < 1000; j++) {
                double sum = 0;
                double sum2 =0;
                for (int k = 0; k < 1000; k++) {
                    sum += wzgledny[i][j * 1000 + k];
                    sum2 += probka[j*1000+ k];
                }
                srednie_wyniki[i][j] = sum / 1000.0;
                srednia_probka[j]=sum2/1000.0;
            }
        }
        double[][] srednie_wyniki_bezWzgledny = new double[4][ilosc_probek/1000];
        double [] srednia_probka_bezWzgledny = new double[ilosc_probek/1000];
        for(int i = 0 ; i < 4 ; i++){
            for (int j = 0; j < 1000; j++) {
                double sum = 0;
                double sum2 =0;
                for (int k = 0; k < 1000; k++) {
                    sum += bezWzgledny[i][j * 1000 + k];
                    sum2 += probka[j*1000+ k];
                }
                srednie_wyniki_bezWzgledny[i][j] = sum / 1000.0;
                srednia_probka_bezWzgledny[j]=sum2/1000.0;
            }
        }
        Wykres.wykresy(srednie_wyniki,srednia_probka);
        Wykres.wykresy(srednie_wyniki_bezWzgledny,srednia_probka_bezWzgledny);



        double x = 2;
        double targetError = 1e-6;
        int n2 = 1;
        double error = Double.MAX_VALUE;
        while (error > targetError) {
            double term = V1(x,n2);
            error = Math.abs(term - Math.exp(x));
            n2++;
        }

        System.out.println("Liczba składników dla " + x + " wynosi : " + n2);

    }
}