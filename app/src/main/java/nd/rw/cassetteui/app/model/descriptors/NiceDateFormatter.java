package nd.rw.cassetteui.app.model.descriptors;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Formats provided date using relative dates. If provided date was yesterday, instead of
 */
public final class NiceDateFormatter {

    /**
     * Gets nicely formatted, human readable version of provided date.
     */
    public static String getNiceDate(GregorianCalendar date){
        // TODO: 28.12.2015 Clean this mess
        if (date == null) {
            return null;
        }
        GregorianCalendar today = new GregorianCalendar();
        if (areEqualDates(date, today)){
            SimpleDateFormat format;
            String stringFormat = "HH:mm";
            format = new SimpleDateFormat(stringFormat);
            format.setCalendar(date);
            String result = format.format(date.getTime()) + " today";
            return result;
        }

        GregorianCalendar yesterday = new GregorianCalendar();
        yesterday.add(Calendar.DAY_OF_YEAR, -1);

        if (areEqualDates(date, yesterday)){
            SimpleDateFormat format;
            String stringFormat = "HH:mm";
            format = new SimpleDateFormat(stringFormat);
            format.setCalendar(date);
            String result = format.format(date.getTime()) + " yesterday";
            return result;
        }

        yesterday.add(Calendar.DAY_OF_YEAR, 1); //  repurpose the object by bringing to today's date.

        SimpleDateFormat format;
        String stringFormat = "";

        GregorianCalendar oneMonthAgo = new GregorianCalendar();
        oneMonthAgo.add(Calendar.DAY_OF_YEAR, -30);

        if( date.get(Calendar.YEAR) < yesterday.get(Calendar.YEAR)) {
            stringFormat = "dd MMMM yyyy";
            format = new SimpleDateFormat(stringFormat);
            format.setCalendar(date);
            return format.format(date.getTime());
        } else {
            if (date.compareTo(oneMonthAgo) < 0) {
                stringFormat = "dd MMMM";
            } else {
                stringFormat = "HH:mm dd MMMM";
            }
            format = new SimpleDateFormat(stringFormat);
            format.setCalendar(date);
            return format.format(date.getTime());
        }
    }

    private static boolean areEqualDates(GregorianCalendar calendar1, GregorianCalendar calendar2){
        return  calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR) &&
                calendar1.get(Calendar.DAY_OF_YEAR) == calendar2.get(Calendar.DAY_OF_YEAR);
    }

}
