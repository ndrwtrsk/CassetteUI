package nd.rw.cassetteui.app.model.descriptors;
import junit.framework.TestCase;

import org.junit.Test;

public class DurationFormatterTest extends TestCase{

    @Test
    public void testOneSecond(){
        //Arrange
        final int oneSecond = 1;

        //Act
        String durationString = DurationFormatter.formatTimeInSeconds(oneSecond);

        //Assert
        assertEquals("00:01", durationString);
    }

    @Test
    public void testOneMinute(){
        //Arrange
        final int oneMinute = 60;

        //Act
        String durationString = DurationFormatter.formatTimeInSeconds(oneMinute);

        //Assert
        assertEquals("01:00", durationString);
    }

    @Test
    public void test30Minutes(){
        //Arrange
        final int thirtyMinutesInSeconds = 30*60;

        //Act
        String durationString = DurationFormatter.formatTimeInSeconds(thirtyMinutesInSeconds);

        //Assert
        assertEquals("30:00", durationString);
    }

    @Test
    public void test45Minutes27seconds(){
        //Arrange
        final int thirtyMinutesInSeconds = 45*60 + 27;

        //Act
        String durationString = DurationFormatter.formatTimeInSeconds(thirtyMinutesInSeconds);

        //Assert
        assertEquals("45:27", durationString);
    }

    @Test
    public void testOneMinute30Seconds(){
        //Arrange
        final int oneMinute30SecondsInSeconds = 90;

        //Act
        String durationString = DurationFormatter.formatTimeInSeconds(oneMinute30SecondsInSeconds);

        //Assert
        assertEquals("01:30", durationString);
    }

    @Test
    public void test1Hour(){
        //Arrange
        final int oneHourInSeconds = 3600;

        //Act
        String durationString = DurationFormatter.formatTimeInSeconds(oneHourInSeconds);

        //Assert
        assertEquals("01:00:00", durationString);
    }
}
