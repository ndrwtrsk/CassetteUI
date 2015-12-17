package nd.rw.cassetteui.app.model;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import nd.rw.cassetteui.app.model.descriptors.RecordingModelDescriptor;

public class RecordingModel {

    public int id;
    public String title;
    public String transcription = "transcription";
    public GregorianCalendar dateRecorded;
    public int durationInMs;
    public String path;
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

    public RecordingModel(int id, String title, String transcription,
                          GregorianCalendar dateRecorded,
                          int durationInMs, String path, CassetteModel cassette) {
        this.id = id;
        this.title = title;
        this.transcription = transcription;
        this.dateRecorded = dateRecorded;
        this.durationInMs = durationInMs;
        this.path = path;
        this.cassette = cassette;
    }

    public String getFormattedDate(){
        SimpleDateFormat fmt = new SimpleDateFormat("HH:mm:ss dd MMMM yyyy");
        fmt.setCalendar(dateRecorded);
        String dateFormatted = fmt.format(dateRecorded.getTime());
        return dateFormatted;
    }

    public RecordingModelDescriptor getDescriptor(){
        return new RecordingModelDescriptor(this);
    }

    public static void populateCassetteWithRecordings(CassetteModel cassette, int startingIndex, int endingIndex){
        for (int i = startingIndex; i < endingIndex; i++) {
            new RecordingModel(i, "Recording " + i, new GregorianCalendar(), 21230, "/path", cassette);
        }
    }

}
