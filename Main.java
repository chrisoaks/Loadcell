package application;
	
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import jssc.SerialPort;
import jssc.SerialPortException;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	final int PAGEWIDTH = 1200;
	final int PAGEHEIGHT = 800;
	final String FXMLFILENAME = "Main.fxml";
    private static ConcurrentLinkedQueue<Number> dataQ1 = new ConcurrentLinkedQueue<>();
	private static final int MAX_DATA_POINTS = 500;
    private NumberAxis xAxis;
    private ExecutorService executor;
    private XYChart.Series<Number, Number> series1 = new XYChart.Series<>();
    private int xSeriesData = 0;

    @FXML
    private LineChart<Number, Number> lclinechart;
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = FXMLLoader.load(Main.class.getResource(FXMLFILENAME));
			Scene scene = new Scene(root,PAGEWIDTH,PAGEHEIGHT);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Strike Analysis Suite");
			primaryStage.show();
	        xAxis = new NumberAxis(0, MAX_DATA_POINTS, MAX_DATA_POINTS / 10);
	        xAxis.setForceZeroInRange(false);
	        xAxis.setAutoRanging(false);
	        xAxis.setTickLabelsVisible(false);
	        xAxis.setTickMarkVisible(false);
	        xAxis.setMinorTickVisible(false);			
	        NumberAxis yAxis = new NumberAxis(0, 500,10);
	        yAxis.setAutoRanging(false);
	        lclinechart = new LineChart<Number, Number>(xAxis, yAxis) {
	            // Override to remove symbols on each data point
	            @Override
	            protected void dataItemAdded(Series<Number, Number> series, int itemIndex, Data<Number, Number> item) {
	            }
	        };
	        lclinechart.setAnimated(false);
	        lclinechart.setTitle("Animated Line Chart");
	        lclinechart.setHorizontalGridLinesVisible(true);
	        
	        series1.setName("Series 1");


	        // Add Chart Series
	        lclinechart.getData().addAll(series1);

	        primaryStage.setScene(new Scene(lclinechart));
	        primaryStage.show();
	        LoadCellListener loadcelllistener = new LoadCellListener();

			Thread.sleep(1000);

	        executor = Executors.newCachedThreadPool(new ThreadFactory() {
	            @Override
	            public Thread newThread(Runnable r) {
	                Thread thread = new Thread(r);
	                thread.setDaemon(true);
	                return thread;
	            }
	        });
	        prepareTimeline();

			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
		          public void handle(WindowEvent we) {
		              System.out.println("Stage is closing");
		  			  try {
						  loadcelllistener.serialPort.closePort();
					  } catch (SerialPortException spe) {
						  spe.printStackTrace();
					  }
					  System.out.println("Serial Port Opened?" + loadcelllistener.serialPort.isOpened());
					  System.out.println(dataQ1);
		          }
		      });
		} catch(Exception e) {
			e.printStackTrace();
		}
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
        for (int i = 0; i < 20; i++) { //-- add 20 numbers to the plot+
            if (dataQ1.isEmpty()) break;
            series1.getData().add(new XYChart.Data<>(xSeriesData++, dataQ1.remove()));
        }
        // remove points to keep us at no more than MAX_DATA_POINTS
        if (series1.getData().size() > MAX_DATA_POINTS) {
            series1.getData().remove(0, series1.getData().size() - MAX_DATA_POINTS);
        }
        // update
        xAxis.setLowerBound(xSeriesData - MAX_DATA_POINTS);
        xAxis.setUpperBound(xSeriesData - 1);
    }
	public static void addToQueue(float f) {
		System.out.println("addtoqueue");
        dataQ1.add(f);

	}

	
	public static void main(String[] args) {
		launch(args);
	}
}