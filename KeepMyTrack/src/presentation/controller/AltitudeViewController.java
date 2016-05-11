/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
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
    
    @FXML
    private AreaChart<Number, Number> areaChart;
    
    void initStage(Stage stage, Activity activity) {
        this.stage = stage;
        this.activity = activity;
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        XYChart.Series series = new XYChart.Series();
        ObservableList<Chunk> chunks = activity.getChunks();
        if (chunks==null) return;
        double distance = 0;
        for(Chunk chunk : chunks){
            series.getData().add(new XYChart.Data(distance, chunk.getFirstPoint().getElevation()));
            distance += chunk.getDistance();
        }
        areaChart.getData().addAll(series);
    }    

    
    
}
