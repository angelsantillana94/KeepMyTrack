package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author José Manuel Garcia
 */
public class ActivityGroup {
    
    private String name;
    private List<Activity> listActivities = new ArrayList<>();
    
    public ActivityGroup(String name, List<Activity> listActivities, int daysAgo) {
        this.name = name;
        LocalDateTime date = LocalDateTime.now().minusDays(daysAgo);
        for (Activity activity : listActivities) {
            if (activity.getEndTime().isAfter(date)) {
                this.listActivities.add(activity);
            }
        }
    }
    
    public ActivityGroup(Activity activity){
        this.name = activity.getName();
        this.listActivities.add(activity);
    }
    
    public String getName() {
        return name;
    }
    
    public List<Activity> getListActivities() {
        return listActivities;
    }
    
}
