package presentation.controller;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import jgpx.model.analysis.Chunk;
import model.Activity;

/**
 * FXML Controller class
 *
 * @author angelsantillana
 */
public class ZonesViewController implements Initializable {

    private Stage stage;
    private Activity activity;
    private Optional result;
    private ObservableList<PieChart.Data> pieChartData;

    int z1 = 0, z2 = 0, z3 = 0, z4 = 0, z5 = 0;

    @FXML
    private PieChart pieChart;
    @FXML
    private HBox loadCircle;

    void initStage(Stage stage, Activity activity, Optional result) {
        this.stage = stage;
        this.activity = activity;
        this.result = result;
        stage.show();
        loadData();
    }

    private void loadData() {
        loadChart();
        Task<Void> task = new Task<Void>() {
            ObservableList<Chunk> chunks = activity.getChunks();
            int ppm = 220 - Integer.parseInt((String) result.get());

            @Override
            protected Void call() throws Exception {
                for (int i = 0; i < chunks.size(); i++) {
                    Chunk chunk = chunks.get(i);
                    if (chunk.getAvgHeartRate() > ppm * 0.9) {
                        z5++;
                    } else if (chunk.getAvgHeartRate() > ppm * 0.8) {
                        z4++;
                    } else if (chunk.getAvgHeartRate() > ppm * 0.7) {
                        z3++;
                    } else if (chunk.getAvgHeartRate() > ppm * 0.6) {
                        z2++;
                    } else {
                        z1++;
                    }
                }
                return null;
            }

            @Override
            protected void succeeded() {
                DecimalFormat round = (DecimalFormat) DecimalFormat.getInstance();
                round.applyPattern("#.##");
                ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                        new PieChart.Data("Z1 - "+round.format((double) z1/chunks.size() * 100)+"%", z1),
                        new PieChart.Data("Z2 - "+round.format((double) z2/chunks.size() * 100)+"%", z2),
                        new PieChart.Data("Z3 - "+round.format((double) z3/chunks.size() * 100)+"%", z3),
                        new PieChart.Data("Z4 - "+round.format((double) z4/chunks.size() * 100)+"%", z4),
                        new PieChart.Data("Z5 - "+round.format((double) z5/chunks.size() * 100)+"%", z5)
                );
                pieChart.setTitle("Zonas frecuencia cardiaca");
                pieChart.setData(pieChartData);
                showChart();
            }
        };

        Thread th = new Thread(task);
        th.start();
    }

    private void loadChart() {
        pieChart.setVisible(false);
        loadCircle.setVisible(true);
    }

    private void showChart() {
        loadCircle.setVisible(false);
        pieChart.setVisible(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //

    }

}
