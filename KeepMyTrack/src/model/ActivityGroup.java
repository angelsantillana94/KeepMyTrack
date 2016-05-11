package model;

import java.time.Duration;
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
    
    public double getTotalDistance(){
        double totalDistance = 0;
        for(Activity activity : listActivities)
            totalDistance += activity.getTotalDistance();
        return totalDistance;
    }
    
    public Duration getTotalDuration(){
        Duration totalDuration = listActivities.get(0).getTotalDuration();
        for(int i = 1; i<listActivities.size(); i++)
            totalDuration = totalDuration.plus(listActivities.get(i).getTotalDuration());
        return totalDuration;
    }
    
    public Duration getMovingTime(){
        Duration movingTime = listActivities.get(0).getMovingTime();
        for(int i = 1; i<listActivities.size(); i++)
            movingTime = movingTime.plus(listActivities.get(i).getMovingTime());
        return movingTime;
    }
    
    public double getAverageSpeed(){
        double averageSpeed = 0;
        long totalDuration = getTotalDuration().getSeconds();
        for(Activity activity : listActivities){
            double percent = 1.0 * activity.getTotalDuration().getSeconds() / totalDuration;
            averageSpeed += activity.getAverageSpeed() * percent;
        }
        return averageSpeed;
    }
    
    public double getMaxSpeed(){
        double maxSpeed = 0;
        for(Activity activity : listActivities){
            if(maxSpeed<activity.getMaxSpeed()) maxSpeed = activity.getMaxSpeed();
        }
        return maxSpeed;
    }
    
    public int getAverageHeartrate(){
        int averageHeartrate = 0;
        long totalDuration = this.getTotalDuration().getSeconds();
        for(Activity activity : listActivities){
            double percent = 1.0 * activity.getTotalDuration().getSeconds() / totalDuration;
            averageHeartrate += activity.getAverageHeartrate()* percent;
        }
        return averageHeartrate;
    }
    
    public int getMaxHeartrate(){
        int maxHeartrate = 0;
        for(Activity activity : listActivities){
            if(maxHeartrate<activity.getMaxHeartrate()) maxHeartrate = activity.getMaxHeartrate();
        }
        return maxHeartrate;
    }
    
    public int getMinHeartRate(){
        int minHeartrate = listActivities.get(0).getMinHeartRate();
        for(Activity activity : listActivities){
            if(minHeartrate>activity.getMinHeartRate()) minHeartrate = activity.getMinHeartRate();
        }
        return minHeartrate;
    }
    
    public double getTotalAscent(){
        double totalAscent = 0;
        for(Activity activity : listActivities)
            totalAscent += activity.getTotalAscent();
        return totalAscent;
    }
    
    public double getTotalDescend(){
        double totalDescend = 0;
        for(Activity activity : listActivities)
            totalDescend += activity.getTotalDescend();
        return totalDescend;
    }
    
    public int getAverageCadence(){
        int averageCadence = 0;
        long totalDuration = getTotalDuration().getSeconds();
        for(Activity activity : listActivities){
            double percent = 1.0 * activity.getTotalDuration().getSeconds() / totalDuration;
            averageCadence += activity.getAverageCadence()* percent;
        }
        return averageCadence;
    }
    
    public int getMaxCadence(){
        int maxCadence = 0;
        for(Activity activity : listActivities){
            if(maxCadence<activity.getMaxHeartrate()) maxCadence = activity.getMaxCadence();
        }
        return maxCadence;
    }
}
