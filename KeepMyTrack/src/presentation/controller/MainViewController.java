package presentation.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
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
    private final ObservableList<ActivityGroup> listDiary = FXCollections.observableArrayList(new ArrayList<ActivityGroup>());
    private Stage stage;
    
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
    private TableView<ActivityGroup> tableDiary;
    @FXML
    private TableColumn<ActivityGroup, String> nameDiary;
    @FXML
    private TableColumn<ActivityGroup, Integer> nDiary;
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
    private Label labelDateTime;
    @FXML
    private GridPane resume;
    @FXML
    private VBox imgBackground;
    @FXML
    private Button btnStatisticsGroup;
    
    public void initStage(Stage stage) {
        this.stage = stage;
    }
    
    private Stage createNewStage(String title, Scene scene){
        Stage newStage = new Stage();
        newStage.setTitle(title);
        newStage.setScene(scene);
        newStage.initModality(Modality.APPLICATION_MODAL);
        return newStage;
    }
    
    private void loadListeners() {
        btnDel.setDisable(true);
        tableActivities.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Activity> obs, Activity oldSelection, Activity newSelection) -> {
            if (newSelection != null) {
                btnDel.setDisable(false);
                showActivityButtons();
                Activity selectedActivity = tableActivities.getSelectionModel().getSelectedItem();
                labelDateTime.setText("El "+selectedActivity.getStartTime().toLocalDate().format(DateTimeFormatter.ofPattern("d MMM uuuu"))+" a las "+selectedActivity.getStartTime().toLocalTime());
                imgBackground.setVisible(false);
                resume.setVisible(true);
                updateResumeWith(new ActivityGroup(selectedActivity));
            }
        });
        tableDiary.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends ActivityGroup> obs, ActivityGroup oldSelection, ActivityGroup newSelection) -> {
            if (newSelection != null) {
                btnDel.setDisable(true);
                showActivityGroupButtons();
                ActivityGroup selectedActivityGroup = tableDiary.getSelectionModel().getSelectedItem();
                labelDateTime.setText("Número de actividades: "+selectedActivityGroup.getListActivities().size());
                imgBackground.setVisible(false);
                resume.setVisible(true);
                updateResumeWith(selectedActivityGroup);
            }
        });
    }
    
    private void showActivityGroupButtons(){
        btnStatisticsGroup.setVisible(true);
        btnStatistics.setVisible(false);
        btnAltitude.setVisible(false);
        btnZones.setVisible(false);
    }
    
    private void showActivityButtons(){
        btnStatisticsGroup.setVisible(false);
        btnStatistics.setVisible(true);
        btnAltitude.setVisible(true);
        btnZones.setVisible(true);
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
        dateActivity.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getStartTime().toLocalDate().toString()));
        tableActivities.setItems(listActivities);
        nameDiary.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getName().toUpperCase()));
        nDiary.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getListActivities().size()));
        tableDiary.setItems(listDiary);
    }
    
    @FXML
    private void addActivity(ActionEvent event) throws JAXBException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("gpx", "*.gpx"),
                new FileChooser.ExtensionFilter("All files", "*.*")
        );
        File file = fileChooser.showOpenDialog(stage);
        if (file == null) {
            return;
        }
        new LoaderGPX(file, stage, listActivities, listDiary).initTask();
    }
    
    @FXML
    private void deleteActivity(ActionEvent event) {
        int index = tableActivities.getSelectionModel().getSelectedIndex();
        MsgWindow msgPrint = new MsgWindow(
                Alert.AlertType.WARNING, "ELIMINAR", null,
                "¿Desea eliminar la actividad " + tableActivities.getItems().get(index).getName() + "?"
        );
        ButtonType buttonTypeNo = new ButtonType("NO", ButtonBar.ButtonData.NO);
        ButtonType buttonTypeYes = new ButtonType("SI", ButtonBar.ButtonData.YES);
        msgPrint.getButtonTypes().setAll(buttonTypeNo,buttonTypeYes);
        Optional<ButtonType> result = msgPrint.showAndWait();
        if (result.isPresent() && result.get() == buttonTypeYes){
            tableActivities.getItems().remove(index);
            if(tableActivities.getItems().isEmpty()){
                btnDel.setDisable(true);
                resume.setVisible(false);
                labelDateTime.setVisible(false);
                imgBackground.setVisible(true);
                labelNameActivity.setText("Selecciona una actividad");
            }
        }
    }
    
    @FXML
    private void showAltitude(ActionEvent event) {
        Activity activity = tableActivities.getSelectionModel().getSelectedItem();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/view/AltitudeView.fxml"));
            AnchorPane root = (AnchorPane) loader.load();
            Scene scene = new Scene(root);
            Stage newStage = createNewStage("Perfil del recorrido", scene);
            AltitudeViewController controller = loader.<AltitudeViewController>getController();
            controller.initStage(newStage,activity);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void showStatistics(ActionEvent event) {
        Activity activity = tableActivities.getSelectionModel().getSelectedItem();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/view/StatisticsView.fxml"));
            AnchorPane root = (AnchorPane) loader.load();
            Scene scene = new Scene(root);
            Stage newStage = createNewStage("Estadísticas del recorrido", scene);
            StatisticsViewController controller = loader.<StatisticsViewController>getController();
            controller.initStage(newStage,activity);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void showZones(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog("23");
        dialog.setTitle("Edad del deportista");
        dialog.setHeaderText("Calcular pulsaciones máximas a partir de su edad");
        dialog.setContentText("Introduzca su edad: ");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            Activity activity = tableActivities.getSelectionModel().getSelectedItem();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/view/ZonesView.fxml"));
                AnchorPane root = (AnchorPane) loader.load();
                Scene scene = new Scene(root);
                Stage newStage = createNewStage("Grafico de esfuerzo cardíaco", scene);
                ZonesViewController controller = loader.<ZonesViewController>getController();
                controller.initStage(newStage,activity,result);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void showStatisticsGroup(ActionEvent event) {
        
    }
}
