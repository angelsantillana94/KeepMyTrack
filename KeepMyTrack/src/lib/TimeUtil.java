
package lib;

/**
 *
 * @author usuario
 */
public class TimeUtil {
    
    public static String secondsToStringFormat(long seconds){
        long aux = seconds;
        int sec = (int) aux % 60;
        aux = aux / 60;
        int min = (int) aux % 60;
        aux = aux / 60;
        return aux+":"+min+":"+sec;
    }
    
}
