package presentation.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import jgpx.model.analysis.Chunk;
import model.Activity;

/**
 * FXML Controller class
 *
 * @author angelsantillana
 */
public class AltitudeViewController implements Initializable {

    private Stage stage;
    private Activity activity;
    private XYChart.Series distanceSeries;
    private XYChart.Series durationSeries;

    @FXML
    private AreaChart<Number, Number> areaChart;

    public void initStage(Stage stage, Activity activity) {
        this.stage = stage;
        this.activity = activity;
        distanceSeries = new XYChart.Series();
        durationSeries = new XYChart.Series();
        areaChart.getData().addAll(durationSeries);
        loadData();
    }

    private void loadData() {
        Task<Void> task = new Task<Void>() {
            ObservableList<Chunk> chunks = activity.getChunks();
            XYChart.Data[] distanceData = new XYChart.Data[chunks.size()];
            XYChart.Data[] durationData = new XYChart.Data[chunks.size()];

            @Override
            protected Void call() throws Exception {
                double distance = 0;
                double duration = 0;
                for (int i = 0; i < chunks.size(); i++) {
                    Chunk chunk = chunks.get(i);
                    distanceData[i] = new XYChart.Data(distance, chunk.getAvgHeight());
                    distance += chunk.getDistance() / 1000.0;
                    durationData[i] = new XYChart.Data(duration, chunk.getAvgHeight());
                    duration += chunk.getDuration().getSeconds() / 60.0;
                }
                return null;
            }

            @Override
            protected void succeeded() {
                distanceSeries.getData().addAll(distanceData);
                durationSeries.getData().addAll(durationData);
            }
        };
        
        Thread th = new Thread(task);
        th.start();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}
