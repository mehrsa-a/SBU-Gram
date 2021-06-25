package Common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
/**
 * <h1>Time</h1>
 * <p>this class returns time in two way that one of them is for compare in integer type and one for show in String type</p>
 * @author Mehrsa Arabzadeh
 * @since 6/2/2021
 * @version 1.0
 */
public class Time {
    private static final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    /**
     * @return this method returns time in String way for show it to every one
     */
    public static String getTime(){
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    /**
     * @return this method return millies time and we use it for comparing publication times
     */
    public static Long getMilli(){
        return Instant.now().toEpochMilli();
    }
}
