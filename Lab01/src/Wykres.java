import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Wykres {


    private static final String[] legenda = {"V1- wzoru", "V2- wzoru od końca",
            "V3 z poprzedniego wyrazu", "V4 Z poprzedniego wyrazu od końca"};



    public static void wykresy(double[][] data, double[] sample) throws IOException {
        XYSeriesCollection dataset = new XYSeriesCollection();

        for (int i = 0; i < data.length; i++) {
            XYSeries series = new XYSeries(legenda[i]);
            for (int j = 0; j < data[i].length; j++) {
                series.add(sample[j], data[i][j]);
            }
            dataset.addSeries(series);
        }

//        createXYLineChart
        JFreeChart chart = ChartFactory.createScatterPlot(
                "Błąd bezwzględny  pomnożone przez 1E+15",
                "min - max",
                "wartości błędu",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        XYPlot plot = (XYPlot) chart.getPlot();
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setAutoRange(false);
        NumberAxis domainAxis = (NumberAxis) plot.getDomainAxis();
        domainAxis.setAutoRangeIncludesZero(false);

//        NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();

//        yAxis.setRange(0, 1.6);


        ChartFrame frame = new ChartFrame("Błędy", chart);
        frame.pack();
        frame.setVisible(true);

        int width = 800;
        int height = 600;
        String filename = "wykres.png";
        ChartUtilities.saveChartAsPNG(new File(filename), chart, width, height);
    }
}
