package presentation.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jgpx.model.analysis.Chunk;
import model.Activity;

/**
 * FXML Controller class
 *
 * @author angelsantillana
 */
public class AltitudeViewController implements Initializable{ 

    private Stage stage;
    private Activity activity;
    private XYChart.Series distanceSeries;
    private XYChart.Series durationSeries;

    @FXML
    private AreaChart<Number, Number> areaChart;
    @FXML
    private HBox loadCircle;
    @FXML
    private RadioButton radioButonDistance;
    @FXML
    private RadioButton radioButonDuration;
    @FXML
    private ToggleGroup optionsXAxis;
    @FXML
    private NumberAxis labelX;

    public void initStage(Stage stage, Activity activity) {
        this.stage = stage;
        this.activity = activity;
        distanceSeries = new XYChart.Series();
        durationSeries = new XYChart.Series();
        areaChart.getData().addAll(distanceSeries);
        stage.show();
        loadData();
    }

    private void loadData() {
        loadChart();
        Task<Void> task = new Task<Void>() {
            ObservableList<Chunk> chunks = activity.getChunks();
            XYChart.Data[] distanceData = new XYChart.Data[chunks.size()];
            XYChart.Data[] durationData = new XYChart.Data[chunks.size()];

            @Override
            protected Void call() throws Exception {
                double distance = 0;
                double duration = 0;
                double height = chunks.get(0).getFirstPoint().getElevation();
                for (int i = 0; i < chunks.size(); i++) {
                    Chunk chunk = chunks.get(i);
                    distanceData[i] = new XYChart.Data(distance, height);
                    distance += chunk.getDistance() / 1000.0;
                    durationData[i] = new XYChart.Data(duration, height);
                    duration += chunk.getDuration().getSeconds() / 60.0;
                    height += chunk.getLastPoint().getElevation() - chunk.getFirstPoint().getElevation();
                }
                return null;
            }

            @Override
            protected void succeeded() {
                distanceSeries.getData().addAll((Object[]) distanceData);
                durationSeries.getData().addAll((Object[]) durationData);
                showChart();
            }
        };
        
        Thread th = new Thread(task);
        th.start();
    }
    
    private void loadChart(){
        areaChart.setVisible(false);
        loadCircle.setVisible(true);
    }
    
    private void showChart(){
        loadCircle.setVisible(false);
        areaChart.setVisible(true);
    }
    
    private void showDuration(){
        areaChart.getData().remove(0, areaChart.getData().size());
        labelX.setLabel("Tiempo (min)");
        areaChart.getData().addAll(durationSeries);
    }
    
    private void showDistance(){
        areaChart.getData().remove(0, areaChart.getData().size());
        labelX.setLabel("Distancia (Km)");
        areaChart.getData().addAll(distanceSeries);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        radioButonDistance.setOnAction(event -> showDistance());
        radioButonDuration.setOnAction(event -> showDuration());
    }

}
