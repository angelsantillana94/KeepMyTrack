
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

    public void initStage(Stage stage, Activity activity) {
        this.stage = stage;
        this.activity = activity;
        XYChart.Series series = new XYChart.Series();
        areaChart.getData().addAll(series);
        ObservableList<Chunk> chunks = activity.getChunks();
        double distance = 0;
        double duration = 0;
        for(Chunk chunk : chunks){
            series.getData().add(new XYChart.Data(distance, chunk.getAvgHeight()));
            distance += chunk.getDistance()/1000;
            //duration += chunk.getDuration().getSeconds()/60.0;
        }
        
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }

}
