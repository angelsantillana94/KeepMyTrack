package presentation.controller;

import java.io.File;
import javafx.application.Platform;
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

/**
 *
 * @author Jose Manuel
 */
public class LoaderGPX extends Task<TrackData> {
    
    private final File file;
    private final Stage stage;
    private TrackData trackData;
    
    public LoaderGPX(File file, Stage stage){
        this.file = file;
        this.stage = stage;
        Thread thread = new Thread(this);
        thread.setDaemon(true);
        thread.start();
    }

    @Override
    protected TrackData call() throws Exception {
        Platform.runLater(() -> stage.getScene().setCursor(Cursor.WAIT));
        JAXBContext jaxbContext = JAXBContext.newInstance(GpxType.class, TrackPointExtensionT.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        JAXBElement<Object> root = (JAXBElement<Object>) unmarshaller.unmarshal(file);
        GpxType gpx = (GpxType) root.getValue();
        if (gpx != null)
            trackData = new TrackData(new Track(gpx.getTrk().get(0)));
        Platform.runLater(() -> stage.getScene().setCursor(Cursor.DEFAULT));
        return trackData;
    }

    @Override
    protected void running() {
        super.running();
    }

    @Override
    protected void succeeded() {
        super.succeeded();
    }

}
