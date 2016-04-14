package nd.rw.cassette.app.model.descriptors;

import junit.framework.TestCase;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class NiceDateFormatterTest extends TestCase{

    public void testYesterday(){
        //  Arrange
        GregorianCalendar yesterday = new GregorianCalendar();
        yesterday.add(Calendar.DAY_OF_YEAR, -1);

        //  Act
        String shouldBeYesterday = NiceDateFormatter.getNiceDate(yesterday);

        //  Assert
        assertEquals("Yesterday", shouldBeYesterday);
    }

    @Test
    public void test15Days(){
        //  Arrange
        GregorianCalendar fifteenDaysBeforeToday = new GregorianCalendar();
        fifteenDaysBeforeToday.add(Calendar.DAY_OF_YEAR, -15);
        String stringFormat = "HH:mm dd MMMM"; // YES TIME
        SimpleDateFormat format = new SimpleDateFormat(stringFormat);
        format.setCalendar(fifteenDaysBeforeToday);
        String todaysDate = format.format(fifteenDaysBeforeToday.getTime());

        //  Act
        String shouldBeNice = NiceDateFormatter.getNiceDate(fifteenDaysBeforeToday);

        //  Assert
        assertEquals(todaysDate, shouldBeNice);
    }

    @Test
    public void test2monthsAgo(){
        //  Arrange
        GregorianCalendar twoMonthsAgo = new GregorianCalendar();
        twoMonthsAgo.add(Calendar.MONTH, -2);
        String stringFormat = "dd MMMM"; // NO TIME
        SimpleDateFormat format = new SimpleDateFormat(stringFormat);
        format.setCalendar(twoMonthsAgo);
        String todaysDate = format.format(twoMonthsAgo.getTime());

        //  Act
        String shouldBeNice = NiceDateFormatter.getNiceDate(twoMonthsAgo);

        //  Assert
        assertEquals(todaysDate, shouldBeNice);
    }

    @Test
    public void testTwoYearsAgo(){
        //  Arrange
        GregorianCalendar twoYearsAgo = new GregorianCalendar();
        twoYearsAgo.add(Calendar.YEAR, -2);
        String stringFormat = "dd MMMM yyyy"; // NO TIME
        SimpleDateFormat format = new SimpleDateFormat(stringFormat);
        format.setCalendar(twoYearsAgo);
        String todaysDate = format.format(twoYearsAgo.getTime());

        //  Act
        String shouldBeNice = NiceDateFormatter.getNiceDate(twoYearsAgo);

        //  Assert
        assertEquals(todaysDate, shouldBeNice);
    }
}
