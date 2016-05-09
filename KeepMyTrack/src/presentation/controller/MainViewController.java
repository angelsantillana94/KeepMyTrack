package presentation.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
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
    private Button btnAdd;
    @FXML
    private Button btnDel;
    @FXML
    private TableView<?> tableActivities;
    @FXML
    private TableColumn<?, ?> nameActivity;
    @FXML
    private TableColumn<?, ?> dateActivity;
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
    @FXML
    private Label labelNameActivity;
   

    public void initStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void addActivity(ActionEvent event) throws JAXBException  {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(stage);
        if (file == null) return; 
        LoaderGPX gpx = new LoaderGPX(file, stage);
        //System.out.println(trackData.getAverageHeight());
    }

    @FXML
    private void deleteActivity(ActionEvent event) {
    }

    @FXML
    private void showAltitude(ActionEvent event) {
        try {
            Stage newStage = new Stage();
            newStage.setTitle("Perfil del recorrido");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/view/AltitudeView.fxml"));
            AnchorPane root = (AnchorPane) loader.load();
            Scene scene = new Scene(root);
            newStage.setScene(scene);
            AltitudeViewController avc = loader.getController();
            avc.initStage(newStage); 
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showStatistics(ActionEvent event) {
    }

    @FXML
    private void showZones(ActionEvent event) {
    }


}
