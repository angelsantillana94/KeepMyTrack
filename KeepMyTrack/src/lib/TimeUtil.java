
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
        String res;
        if (sec<10) res = ":0"+sec;
        else res = ":"+sec;
        if (min<10) res = ":0"+min+res;
        else res = ":"+min+res;
        return aux+res;
    }
    
}
