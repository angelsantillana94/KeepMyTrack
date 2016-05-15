package presentation.controller;

import java.io.File;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.Cursor;
import javafx.stage.Stage;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import jgpx.model.analysis.TrackData;
import jgpx.model.gpx.Track;
import jgpx.model.jaxb.GpxType;
import jgpx.model.jaxb.TrackPointExtensionT;
import model.Activity;
import model.ActivityGroup;

/**
 *
 * @author Jose Manuel
 */
public class LoaderGPX extends Task<TrackData> {

    private final File file;
    private final Stage stage;
    private ObservableList<Activity> listActivities;
    private ObservableList<ActivityGroup> listDiary;
    private Activity activity;

    public LoaderGPX(File file, Stage stage, ObservableList<Activity> listActivities, ObservableList<ActivityGroup> listDiary) {
        this.file = file;
        this.stage = stage;
        this.listActivities = listActivities;
        this.listDiary = listDiary;
    }

    public void initTask() {
        Thread thread = new Thread(this);
        thread.setDaemon(true);
        thread.start();
    }

    @Override
    protected TrackData call() throws Exception {
        JAXBContext jaxbContext = JAXBContext.newInstance(GpxType.class, TrackPointExtensionT.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        JAXBElement<Object> root = (JAXBElement<Object>) unmarshaller.unmarshal(file);
        GpxType gpx = (GpxType) root.getValue();
        if (gpx != null) {
            activity = new Activity(new Track(gpx.getTrk().get(0)));
        }
        return activity;
    }

    @Override
    protected void running() {
        super.running();
        Platform.runLater(() -> stage.getScene().setCursor(Cursor.WAIT));
    }

    @Override
    protected void succeeded() {
        super.succeeded();
        listActivities.add(activity);
        listDiary.add(new ActivityGroup(activity));
        Platform.runLater(() -> stage.getScene().setCursor(Cursor.DEFAULT));
    }

    
    
}
