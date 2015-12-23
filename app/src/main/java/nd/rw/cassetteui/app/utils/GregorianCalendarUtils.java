package nd.rw.cassetteui.app.utils;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public final class GregorianCalendarUtils {

    public static String getISO8601DateFormat(GregorianCalendar gregorianCalendar){
        if (gregorianCalendar == null) {
            throw new NullPointerException("Provided calendar should not be null");
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        sdf.setCalendar(gregorianCalendar);

        return sdf.format(gregorianCalendar.getTime());
    }

}
