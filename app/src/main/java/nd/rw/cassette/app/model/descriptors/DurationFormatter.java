package nd.rw.cassette.app.model.descriptors;

public final class DurationFormatter {

    private DurationFormatter() {
        throw new AssertionError("No instances please!");
    }

    public static String formatTimeInSeconds(final int secs){

        final int hours = secs / 3600;
        final int minutes = (secs % 3600) / 60;
        final int seconds = secs % 60;

        String result = "";

        if (hours != 0){
            result += String.format("%02d:", hours);
        }

        result += String.format("%02d:%02d", minutes, seconds);
        return result;
    }

    public static String formatTimeInMilliseconds(final int msecs){
        return formatTimeInSeconds(msecs/1000);
    }

}
