import java.awt.Color;
import java.awt.Dimension;
import java.awt.geom.Ellipse2D;
import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.axis.LogAxis;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class Wykres extends JFrame {

    private static final long serialVersionUID = 1L;
    private static final int WIDTH = 720;
    private static final int HEIGHT = 720;
    private static final Color[] COLORS = {Color.RED, Color.BLUE, Color.GREEN, Color.CYAN};
    private static final String[] LEGENDS = {"Trapezów", "Simpsona",
            "CSI"};

    public Wykres(double[][] data, double[] xStart, boolean logarithmic) {
        super("Wykres");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(WIDTH, HEIGHT));
        setLocationRelativeTo(null);

        XYSeriesCollection dataset = new XYSeriesCollection();
        for (int i = 0; i < data.length; i++) {
            XYSeries series = new XYSeries(LEGENDS[i]);
            for (int j = 0; j < data[i].length; j++) {
                series.add(xStart[j], data[i][j]);
            }
            dataset.addSeries(series);
        }

        JFreeChart chart = ChartFactory.createXYLineChart("Błąd bezwzględny wyniku całkowania",
                "x", "błąd bezwzględny", dataset,
                PlotOrientation.VERTICAL, true, true, true);

        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setRangeGridlinePaint(Color.BLACK);
        plot.setDomainGridlinePaint(Color.BLACK);

        if (logarithmic){
            LogAxis logyAxis = new LogAxis(plot.getRangeAxis().getLabel());
            logyAxis.setBase(10);
            logyAxis.setAutoRange(true);
            plot.setRangeAxis(logyAxis);
        }
        else {
            NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
            rangeAxis.setAutoRange(true);
            rangeAxis.setTickLabelPaint(Color.BLACK);
            rangeAxis.setLabelPaint(Color.BLACK);
        }

        NumberAxis domainAxis = (NumberAxis) plot.getDomainAxis();
        domainAxis.setAutoRangeIncludesZero(false);
        domainAxis.setTickLabelPaint(Color.BLACK);
        domainAxis.setLabelPaint(Color.BLACK);

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        for (int i = 0; i < COLORS.length; i++) {
            renderer.setSeriesPaint(i, COLORS[i]);
            renderer.setSeriesShapesVisible(i, true);
            renderer.setSeriesLinesVisible(i, false);
            renderer.setSeriesShape(i, new Ellipse2D.Double(-0.5, -0.5, 1.0, 1.0));
        }
        plot.setRenderer(renderer);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setContentPane(chartPanel);
    }

    public static void rysujWykres(double[][] data, double[] xStart, boolean logarithmic) {
        Wykres wykres = new Wykres(data, xStart, logarithmic);
        wykres.setVisible(true);
    }
}