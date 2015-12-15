package nd.rw.cassetteui.app.model;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public class RecordingModel {

    public int id;
    public String title;
    public GregorianCalendar dateRecorded;
    public int durationInMs;
    public String path;
    public String transcription = "transcription";
    public CassetteModel cassette;


    public int getDurationInSeconds(){
        return durationInMs/1000;
    }

    public RecordingModel(int id, String title, GregorianCalendar dateRecorded,
                          int durationInMs, String path, CassetteModel cassette) {
        this.id = id;
        this.title = title;
        this.dateRecorded = dateRecorded;
        this.durationInMs = durationInMs;
        this.path = path;
        this.cassette = cassette;
        this.cassette.recordingList.add(this);
    }

    public String getFormattedDate(){
        SimpleDateFormat fmt = new SimpleDateFormat("HH:mm:ss dd-MMM-yyyy");
        fmt.setCalendar(dateRecorded);
        String dateFormatted = fmt.format(dateRecorded.getTime());
        return dateFormatted;
    }



    public static void populateCassetteWithRecordings(CassetteModel cassette, int startingIndex, int endingIndex){
        for (int i = startingIndex; i < endingIndex; i++) {
            new RecordingModel(i, "Recording " + i, new GregorianCalendar(), 200, "/path", cassette);
        }
    }

}
