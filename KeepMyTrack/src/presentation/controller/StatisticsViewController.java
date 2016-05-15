
package presentation.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import jgpx.model.analysis.Chunk;
import model.Activity;

/**
 * FXML Controller class
 *
 * @author angelsantillana
 */
public class StatisticsViewController implements Initializable {

    private final int SPEED = 0, HEARTRATE = 1, CADENCE = 2;
    private final int DISTANCE = 0, DURATION = 1;
    
    private Stage stage;
    private Activity activity;
    private XYChart.Series[] distanceSeries;
    private XYChart.Series[] durationSeries;
    
    private int selectedXAxis = DISTANCE;
    
    
    @FXML
    private HBox loadCircle;
    @FXML
    private LineChart<Number, Number> lineChart;
    @FXML
    private NumberAxis labelX;
    @FXML
    private CheckBox checkBoxHeartrate;
    @FXML
    private CheckBox checkBoxSpeed;
    @FXML
    private CheckBox checkBoxCadence;
    @FXML
    private RadioButton radioButonDistance;
    @FXML
    private ToggleGroup optionsXAxis;
    @FXML
    private RadioButton radioButonDuration;

    public void initStage(Stage stage, Activity activity) {
        this.stage = stage;
        this.activity = activity;
        this.initSeries();
        lineChart.getData().addAll(distanceSeries[SPEED]);
        stage.show();
        loadData();
    }
    
    private void initSeries(){
        this.distanceSeries = new XYChart.Series[3];
        this.durationSeries = new XYChart.Series[3];
        for(int i = 0; i<distanceSeries.length; i++) distanceSeries[i] = new XYChart.Series();
        for(int i = 0; i<durationSeries.length; i++) durationSeries[i] = new XYChart.Series();
        distanceSeries[SPEED].setName("Velocidad (Km/h)");
        durationSeries[SPEED].setName("Velocidad (Km/h)");
        distanceSeries[CADENCE].setName("Cadencia (rpm)");
        durationSeries[CADENCE].setName("Cadencia (rpm)");
        distanceSeries[HEARTRATE].setName("F.C. (ppm)");
        durationSeries[HEARTRATE].setName("F.C. (ppm)");
    }

    private void loadData() {
        loadChart();
        Task<Void> task = new Task<Void>() {
            ObservableList<Chunk> chunks = activity.getChunks();
            
            XYChart.Data[] distanceSpeed = new XYChart.Data[chunks.size()];
            XYChart.Data[] durationSpeed = new XYChart.Data[chunks.size()];
            XYChart.Data[] distanceHeartrate = new XYChart.Data[chunks.size()];
            XYChart.Data[] durationHeartrate = new XYChart.Data[chunks.size()];
            XYChart.Data[] distanceCadence = new XYChart.Data[chunks.size()];
            XYChart.Data[] durationCadence = new XYChart.Data[chunks.size()];

            @Override
            protected Void call() throws Exception {
                double distance = 0;
                double duration = 0;
                for (int i = 0; i < chunks.size(); i++) {
                    Chunk chunk = chunks.get(i);
                    distanceSpeed[i] = new XYChart.Data(distance, chunk.getSpeed()*(3600 / 1000));
                    distanceHeartrate[i] = new XYChart.Data(distance, chunk.getAvgHeartRate());
                    distanceCadence[i] = new XYChart.Data(distance, chunk.getAvgCadence());
                    durationSpeed[i] = new XYChart.Data(duration, chunk.getSpeed()*(3600 / 1000));
                    durationHeartrate[i] = new XYChart.Data(duration, chunk.getAvgHeartRate());
                    durationCadence[i] = new XYChart.Data(duration, chunk.getAvgCadence());
                    distance += chunk.getDistance() / 1000.0;
                    duration += chunk.getDuration().getSeconds() / 60.0;
                }
                return null;
            }

            @Override
            protected void succeeded() {
                distanceSeries[SPEED].getData().addAll((Object[]) distanceSpeed);
                distanceSeries[CADENCE].getData().addAll((Object[]) distanceCadence);
                distanceSeries[HEARTRATE].getData().addAll((Object[]) distanceHeartrate);
                durationSeries[SPEED].getData().addAll((Object[]) durationSpeed);
                durationSeries[CADENCE].getData().addAll((Object[]) durationCadence);
                durationSeries[HEARTRATE].getData().addAll((Object[]) durationHeartrate);
                
                showChart();
            }
        };
        
        Thread th = new Thread(task);
        th.start();
    }
    
    private void loadChart(){
        lineChart.setVisible(false);
        loadCircle.setVisible(true);
    }
    
    private void showChart(){
        loadCircle.setVisible(false);
        lineChart.setVisible(true);
    }
    
    private void showDuration(){
        lineChart.getData().remove(0, lineChart.getData().size());
        labelX.setLabel("Tiempo (min)");
        if(checkBoxSpeed.isSelected()) lineChart.getData().addAll(durationSeries[SPEED]);
        if(checkBoxCadence.isSelected()) lineChart.getData().addAll(durationSeries[CADENCE]);
        if(checkBoxHeartrate.isSelected()) lineChart.getData().addAll(durationSeries[HEARTRATE]);
    }
    
    private void showDistance(){
        lineChart.getData().remove(0, lineChart.getData().size());
        labelX.setLabel("Distancia (Km)");
        if(checkBoxSpeed.isSelected()) lineChart.getData().addAll(distanceSeries[SPEED]);
        if(checkBoxCadence.isSelected()) lineChart.getData().addAll(distanceSeries[CADENCE]);
        if(checkBoxHeartrate.isSelected()) lineChart.getData().addAll(distanceSeries[HEARTRATE]);
    }
    
    private void showData(){
        if(selectedXAxis == DISTANCE){
            showDistance();
        } else {
            showDuration();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        radioButonDistance.setOnAction(event -> {
            selectedXAxis = DISTANCE;
            showDistance();
        });
        radioButonDuration.setOnAction((ActionEvent event) -> {
            selectedXAxis = DURATION;
            showDuration();
        });
        checkBoxSpeed.setOnAction(event -> showData());
        checkBoxHeartrate.setOnAction(event -> showData());
        checkBoxCadence.setOnAction(event -> showData());
        
    }

}
