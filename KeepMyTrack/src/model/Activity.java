package model;

import jgpx.model.analysis.TrackData;
import jgpx.model.gpx.Track;

/**
 *
 * @author José Manuel Garcia
 */
public class Activity extends TrackData {

    private String name;

    public Activity(Track track) {
        super(track);
        this.name = track.getName();
    }

    public String getName() {
        return name;
    }

}
