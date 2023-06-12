public class TrapezoidalIntegral {

    public static double trapezoidalIntegral(double[] points) {
        int n = points.length;
        double sum = 0.0;

        for (int i = 0; i < n - 1; i++) {
            double x0 = points[i];
            double x1 = points[i + 1];
            double y0 = f(x0);
            double y1 = f(x1);

            sum += (x1 - x0) * (y0 + y1) / 2.0;
        }

        return sum;
    }

    public static double f(double x) {
        return Math.sin(x) * 3;
    }

    public static void main(String[] args) {
        double[] points = {0.0, 0.5, 1.0, 1.5, 2.0};
        double integral = trapezoidalIntegral(points);

        System.out.println("Całka oznaczona: " + integral);
    }
}
