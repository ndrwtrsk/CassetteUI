package nd.rw.cassetteui.app.model.descriptors;

public final class DurationFormatter {

    private DurationFormatter() {
        throw new AssertionError("No instances please!");
    }

    public static String formatTimeInSeconds(final int durationInSeconds){

        final int hours = durationInSeconds / 3600;
        final int minutes = (durationInSeconds % 3600) / 60;
        final int seconds = durationInSeconds % 60;

        String result = "";

        if (hours != 0){
            result += String.format("%02d:", hours);
        }

        result += String.format("%02d:%02d", minutes, seconds);
        return result;
    }

}
