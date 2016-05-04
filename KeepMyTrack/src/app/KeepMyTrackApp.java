
package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import presentation.controller.MainViewController;

/**
 *
 * @author JoseManuel
 */
public class KeepMyTrackApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/view/MainView.fxml"));
        AnchorPane root = (AnchorPane) loader.load();
        MainViewController mvc = loader.getController();
        mvc.initStage(stage);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("KeepMyTrack");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
