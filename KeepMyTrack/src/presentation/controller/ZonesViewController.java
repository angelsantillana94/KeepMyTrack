/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation.controller;

import java.net.URL;
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
    int z1 = 0;
    int z2 = 0;
    int z3 = 0;
    int z4 = 0;
    int z5 = 0;
    
    @FXML
    private PieChart pieChart;
    @FXML
    private HBox loadCircle;
    
    
    
    void initStage(Stage stage, Activity activity, Optional result) {
        this.stage = stage;
        this.activity = activity;
        this.result = result;
        pieChart.setData(pieChartData);
        stage.show();
        loadChart();
        loadData();
    }
    
    public void loadData() {
        loadChart();
        Task<Void> task = new Task<Void>() {
            ObservableList<Chunk> chunks = activity.getChunks();
            int ppm = 220 - Integer.parseInt((String) result.get()) ;
            
            @Override
            protected Void call() throws Exception {
                for (int i = 0; i < chunks.size(); i++) {
                    Chunk chunk = chunks.get(i);
                    //System.out.println(chunk.getAvgHeartRate());
                    if(chunk.getAvgHeartRate() >  ppm*0.9){
                        z5 += 1;
                    }else if(chunk.getAvgHeartRate() >=  ppm*0.8 && chunk.getAvgHeartRate() <=  ppm*0.9){
                        z4 += 1;
                    }else if(chunk.getAvgHeartRate() >=  ppm*0.7 && chunk.getAvgHeartRate() <=  ppm*0.8){
                        z3 += 1;
                    }else if(chunk.getAvgHeartRate() >=  ppm*0.6 && chunk.getAvgHeartRate() <=  ppm*0.7){
                        z2 += 1;
                    }else if(chunk.getAvgHeartRate() <  ppm*0.6){
                        z1 += 1;
                    }
                }
                //System.out.println("Termina FOR "+pieChartData);
                return null;
            }

            @Override
            protected void succeeded() {
                ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList( 
                      new PieChart.Data("Z1", z1), 
                      new PieChart.Data("Z2", z2), 
                      new PieChart.Data("Z3", z3),
                      new PieChart.Data("Z4", z4),
                      new PieChart.Data("Z5", z5)
                );
                //System.out.println("Succeded!: "+pieChartData);
                //System.out.println(" Zona1:"+z1+ " Zona2:"+z2+" Zona 3:"+z3+ " Zona4:"+z4+" Zona5:"+z5);
                pieChart.setData(pieChartData);
                pieChart.setTitle("Zonas Frecuencia Cardiaca");
                showChart();
            }
        };
        
        Thread th = new Thread(task);
        th.start();
    }
    
    private void loadChart(){
        pieChart.setVisible(false);
        loadCircle.setVisible(true);
    }
    
    private void showChart(){
        loadCircle.setVisible(false);
        pieChart.setVisible(true);
    }
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //
        
    }
    
}
