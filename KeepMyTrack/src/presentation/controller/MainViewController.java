package presentation.controller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.xml.bind.JAXBException;
import jgpx.model.analysis.TrackData;

/**
 *
 * @author Jose Manuel
 */
public class MainViewController implements Initializable {

    private Stage stage;
    private TrackData trackData;
    
      @FXML
    private TableView<?> tableActivities;
    @FXML
    private TableColumn<?, ?> nameActivity;
    @FXML
    private TableColumn<?, ?> dateActivity;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnDel;
    @FXML
    private Label titleActivity;
    @FXML
    private Label distance;
    @FXML
    private Label avgSpeed;
    @FXML
    private Label maxSpeed;
    @FXML
    private Label avgFC;
    @FXML
    private Label gainAltitude;
    @FXML
    private Label lossAltitude;
    @FXML
    private Label maxFC;
    @FXML
    private Label avgCadence;
    @FXML
    private Label maxCadence;
    @FXML
    private Label minFC;
    @FXML
    private Label duration;
    @FXML
    private Label movingDuration;
    @FXML
    private Button btnAltitude;
    @FXML
    private Button btnStatistics;
    @FXML
    private Button btnZones;

    public void initStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    private void loadGPX(ActionEvent event) throws JAXBException {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(stage);
        if (file == null) return; 
        LoaderGPX gpx = new LoaderGPX(file, stage);
        //System.out.println(trackData.getAverageHeight());
    }

    @FXML
    private void addActivity(ActionEvent event) {
    }

    @FXML
    private void deleteActivity(ActionEvent event) {
    }

    @FXML
    private void showAltitude(ActionEvent event) {
    }

    @FXML
    private void showStatistics(ActionEvent event) {
    }

    @FXML
    private void showZones(ActionEvent event) {
    }

}
