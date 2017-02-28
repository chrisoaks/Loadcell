  package application;

import java.io.File;
import java.net.URL;

import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import javafx.animation.AnimationTimer;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class Controller implements Initializable{
	@FXML public Button button;
	@FXML public Button button2;
	@FXML public Button button3;
	@FXML public Button button4;
	@FXML public Label text1;
	@SuppressWarnings("rawtypes")
	@FXML public LineChart linechart;
	@FXML public NumberAxis xAxis;
	@FXML public NumberAxis yAxis;
	@FXML public MediaView mediaView;
	final int PAGEWIDTH = 1200;
	final int PAGEHEIGHT = 800;
	final String FXMLFILENAME = "Main.fxml";
    public static ConcurrentLinkedQueue<Number> dataQ1 = new ConcurrentLinkedQueue<>();
    private static ConcurrentLinkedQueue<Number> dataQ2 = new ConcurrentLinkedQueue<>();
    private static ConcurrentLinkedQueue<Number> dataQ3 = new ConcurrentLinkedQueue<>();

	private static final int MAX_DATA_POINTS = 50;
    @SuppressWarnings("unused")
	private ExecutorService executor;
    public static XYChart.Series<Number, Number> series1 = new XYChart.Series<>();
    public static XYChart.Series<Number, Number> series2 = new XYChart.Series<>();
    public static XYChart.Series<Number, Number> series3 = new XYChart.Series<>();

    @FXML private MediaView m1;
    @FXML private Button b1;
    @FXML private Button b2;
    @FXML private FlowPane hboxvideos;
    private int xSeriesData = 0;
    
    private MediaPlayer mediaPlayer;
	private MediaPlayer mediaPlayer2;	
	private MediaPlayer mediaPlayer3;	
	private MediaPlayer mediaPlayer4;	
    
	@FXML public void handleButtonClick() {}
	@FXML public void handleButton2Click() {}
	@FXML public void handleButton3Click() {}
	@FXML public void saveProject(){}
	
	@FXML public void playVideo() {
		mediaPlayer.play();
	}
	@FXML public void pauseVideo() {
		mediaPlayer.pause();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

        linechart.setPrefWidth(1600);
        linechart.setPrefHeight(800);
        linechart.setHorizontalGridLinesVisible(true);
        
        xAxis = new NumberAxis(0, MAX_DATA_POINTS, MAX_DATA_POINTS / 10);
        xAxis.setForceZeroInRange(false);
        xAxis.setAutoRanging(false);
        xAxis.setTickLabelsVisible(false);
        xAxis.setTickMarkVisible(false);
        xAxis.setMinorTickVisible(false);			
        NumberAxis yAxis = new NumberAxis(-60, 60,10);
        yAxis.setAutoRanging(false);
        series1.setName("Series 1");
        series2.setName("Series 2");
        series3.setName("Series 3");
        linechart.getData().addAll(series1);

        File file = new File("clip.mp4");
        File file2 = new File("clip2.mp4");
        File file3 = new File("clip3.mp4");
        File file4 = new File("clip4.mp4");

        
        mediaPlayer = new MediaPlayer(new Media(file.toURI().toString()));
        mediaPlayer2 = new MediaPlayer(new Media(file2.toURI().toString()));
        mediaPlayer3 = new MediaPlayer(new Media(file3.toURI().toString()));
        mediaPlayer4 = new MediaPlayer(new Media(file4.toURI().toString()));


        
        MediaControl mediaControl = new MediaControl(mediaPlayer);
        MediaControl mediaControl2 = new MediaControl(mediaPlayer2);
        MediaControl mediaControl3 = new MediaControl(mediaPlayer3);
        MediaControl mediaControl4 = new MediaControl(mediaPlayer4);
        
        hboxvideos.getChildren().add(mediaControl);
        hboxvideos.getChildren().add(mediaControl2);
        hboxvideos.getChildren().add(mediaControl3);
        hboxvideos.getChildren().add(mediaControl4);
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
	public static void addToQueue(float datapoint, String _id) {
		if (_id=="load") {
			System.out.println("addtoqueue:" + datapoint);
			dataQ1.add(datapoint);
		}
		//else System.out.println("not load");

	}
	public static void addToOtherQueue(float f) {
		//System.out.println("addingtootherque");
		//dataQ2.add(f);
	}
}
