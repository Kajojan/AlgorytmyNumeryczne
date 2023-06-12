public class SimpsonIntegral {

    public static double simpsonIntegral(double[] points) {
        int n = points.length;
        double sum = 0.0;

        for (int i = 0; i < n - 2; i += 2) {
            double x0 = points[i];
            double x1 = points[i + 1];
            double x2 = points[i + 2];
            double y0 = f(x0);
            double y1 = f(x1);
            double y2 = f(x2);

            sum += (x2 - x0) * (y0 + 4 * y1 + y2) / 6.0;
        }

        return sum;
    }

    public static double f(double x) {
        return Math.sin(x) * 3;
    }

    public static void main(String[] args) {
        double[] points = {0.0, 0.5, 1.0, 1.5, 2.0};
        double integral = simpsonIntegral(points);

        System.out.println("Całka oznaczona: " + integral);
    }
}
