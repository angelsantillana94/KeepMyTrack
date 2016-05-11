package presentation.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.xml.bind.JAXBException;
import lib.TimeUtil;
import model.Activity;
import model.ActivityGroup;

/**
 *
 * @author Jose Manuel
 */
public class MainViewController implements Initializable {
    
    private final ObservableList<Activity> listActivities = FXCollections.observableArrayList(new ArrayList<Activity>());
    private Stage stage;
    private Activity selectedActivity;
    
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnDel;
    @FXML
    private TableView<Activity> tableActivities;
    @FXML
    private TableColumn<Activity, String> nameActivity;
    @FXML
    private TableColumn<Activity, String> dateActivity;
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
    @FXML
    private GridPane resume;
    
    
    
    public void initStage(Stage stage) {
        this.stage = stage;
    }
    
    private void loadListeners() {
        tableActivities.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                resume.setVisible(true);
                selectedActivity = tableActivities.getSelectionModel().getSelectedItem();
                updateResumeWith(new ActivityGroup(selectedActivity));
            }
        });
    }
    
    private void updateResumeWith(ActivityGroup activityGroup) {
        DecimalFormat round = (DecimalFormat) DecimalFormat.getInstance();
        round.applyPattern("#.##");
        labelNameActivity.setText(activityGroup.getName());
        duration.setText(TimeUtil.secondsToStringFormat(activityGroup.getTotalDuration().getSeconds()));
        movingDuration.setText(TimeUtil.secondsToStringFormat(activityGroup.getMovingTime().getSeconds()));
        distance.setText(round.format(activityGroup.getTotalDistance() / 1000) + " Km");
        avgSpeed.setText(round.format(activityGroup.getAverageSpeed() * 3600 / 1000) + " Km/h");
        maxSpeed.setText(round.format(activityGroup.getMaxSpeed() * 3600 / 1000) + " Km/h");
        avgFC.setText(activityGroup.getAverageHeartrate() + " ppm");
        maxFC.setText(activityGroup.getMaxHeartrate() + " ppm");
        minFC.setText(activityGroup.getMinHeartRate() + " ppm");
        gainAltitude.setText(round.format(activityGroup.getTotalAscent()) + " m");
        lossAltitude.setText(round.format(activityGroup.getTotalDescend()) + " m");
        avgCadence.setText(activityGroup.getAverageCadence() + " rpm");
        maxCadence.setText(activityGroup.getMaxCadence() + " rpm");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadListeners();
        nameActivity.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getName().toUpperCase()));
        dateActivity.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getEndTime().toLocalDate().toString()));
        tableActivities.setItems(listActivities);
    }
    
    @FXML
    private void addActivity(ActionEvent event) throws JAXBException {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(stage);
        if (file == null) {
            return;
        }
        new LoaderGPX(file, stage, listActivities).initTask();
    }
    
    @FXML
    private void deleteActivity(ActionEvent event) {
    }
    
    @FXML
    private void showAltitude(ActionEvent event) {
        try {
            Stage newStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/view/AltitudeView.fxml"));
            AltitudeViewController avc = loader.getController();
            avc.initStage(newStage,selectedActivity);
            AnchorPane root = (AnchorPane) loader.load();
            Scene scene = new Scene(root);
            newStage.setTitle("Perfil del recorrido");
            newStage.setScene(scene);
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
