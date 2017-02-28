package application;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class Loop {
	final int PAGEWIDTH = 1200;
	final int PAGEHEIGHT = 800;
	final String FXMLFILENAME = "Main.fxml";
    private static ConcurrentLinkedQueue<Number> dataQ1 = new ConcurrentLinkedQueue<>();
    private static ConcurrentLinkedQueue<Number> dataQ2 = new ConcurrentLinkedQueue<>();
    private static ConcurrentLinkedQueue<Number> dataQ3 = new ConcurrentLinkedQueue<>();

	private static final int MAX_DATA_POINTS = 1000;
    @SuppressWarnings("unused")
	private ExecutorService executor;
    public static XYChart.Series<Number, Number> series1 = new XYChart.Series<>();
    public static XYChart.Series<Number, Number> series2 = new XYChart.Series<>();
    public static XYChart.Series<Number, Number> series3 = new XYChart.Series<>();
	@FXML public NumberAxis xAxis;

    private int xSeriesData = 0;
    
    public Loop() {
        executor = Executors.newCachedThreadPool(new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setDaemon(true);
                return thread;
            }
        });
        prepareTimeline();
    }
    private void prepareTimeline() {
        // Every frame to take any data from queue and add to chart
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                addDataToSeries();
            }
        }.start();
    }
    private void addDataToSeries() {
    	System.out.println("adddatatoseries");
        for (int i = 0; i < 20; i++) { //-- add 20 numbers to the plot+
            if (!dataQ1.isEmpty())
            	series1.getData().add(new XYChart.Data<>(xSeriesData++, dataQ1.remove()));
            if (!dataQ2.isEmpty())
            	series2.getData().add(new XYChart.Data<>(xSeriesData++, dataQ2.remove()));
            if (!dataQ3.isEmpty())
                	series3.getData().add(new XYChart.Data<>(xSeriesData++, dataQ3.remove()));
        }
        // remove points to keep us at no more than MAX_DATA_POINTS
        if (series1.getData().size() > MAX_DATA_POINTS) {
            series1.getData().remove(0, series1.getData().size() - MAX_DATA_POINTS);
        }        
        if (series2.getData().size() > MAX_DATA_POINTS) {
            series2.getData().remove(0, series2.getData().size() - MAX_DATA_POINTS);
        }        
        if (series3.getData().size() > MAX_DATA_POINTS) {
            series3.getData().remove(0, series3.getData().size() - MAX_DATA_POINTS);
        }
        // update
        xAxis.setLowerBound(xSeriesData - MAX_DATA_POINTS);
        xAxis.setUpperBound(xSeriesData - 1);
    }
	public static void addToQueue(float f) {
		System.out.println("addtoqueue");
        dataQ1.add(f);
	}
	public static void addToOtherQueue(float f) {
		System.out.println("addingtootherque");
		//dataQ2.add(f);
	}
}
