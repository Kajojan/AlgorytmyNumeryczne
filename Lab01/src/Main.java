public class Main {

public static double silnia(double x){
    double suma=1;
    for(int i =1 ; i<=x; i++){
        suma*=i;
    }
    return suma;
}
    public static double func(double x, double n){
            double dzielnik = 1;
            double mianownik = x;
            double sum =0;
            for(int i =0 ; i <= n ; i++){
                dzielnik=silnia(i);

                for(int j = 1 ;j<i ; j++){
                    mianownik=mianownik*mianownik;
                }
                sum+=mianownik/dzielnik;
                System.out.println(i+" : "+mianownik);
                System.out.println(dzielnik);

            }
        return sum;

    }

    public static void main(String[] args) {
        System.out.println(func(2,3));
    }
}