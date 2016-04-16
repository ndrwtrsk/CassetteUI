package nd.rw.cassette.app.model;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import nd.rw.cassette.app.model.descriptors.RecordingModelDescriptor;

public class RecordingModel implements Comparable<RecordingModel>{

    //region Fields

    public int id;
    public String title;
    public String transcription = "transcription";
    public GregorianCalendar dateRecorded;
    public int durationInMs;
    public String path;
    public CassetteModel cassette;

    //endregion Fields


    //region Constructors

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

    public RecordingModel(GregorianCalendar dateRecorded, int durationInMs,
                          String path, CassetteModel cassette) {
        this.dateRecorded = dateRecorded;
        this.durationInMs = durationInMs;
        this.path = path;
        this.cassette = cassette;
    }

    //endregion Constructors

    //region Methods

    public String getFormattedDate(){
        SimpleDateFormat fmt = new SimpleDateFormat("HH:mm:ss dd MMMM yyyy");
        fmt.setCalendar(dateRecorded);
        String dateFormatted = fmt.format(dateRecorded.getTime());
        return dateFormatted;
    }

    public RecordingModelDescriptor getDescriptor(){
        return new RecordingModelDescriptor(this);
    }

    public int getDurationInSeconds(){
        return durationInMs/1000;
    }

    /**
     * Compares this Recording to another Recording. This Recording is considered greater, if it's
     * recording creationDate is newer than the another one's.
     *
     * @param another the object to compare to this instance.
     * @return a negative integer if this instance is less than {@code another};
     * a positive integer if this instance is greater than
     * {@code another}; 0 if this instance has the same order as
     * {@code another}.
     * @throws ClassCastException if {@code another} cannot be converted into something
     *                            comparable to {@code this} instance.
     */
    @Override
    public int compareTo(RecordingModel another) {
        if (another == null) {
            return 1;
        }
        return this.dateRecorded.compareTo(another.dateRecorded);
    }

    //endregion Methods

    //region Static Methods

    public static void populateCassetteWithRecordings(CassetteModel cassette, int startingIndex, int endingIndex){
        for (int i = startingIndex; i < endingIndex; i++) {
            new RecordingModel(i, "Recording " + i, new GregorianCalendar(), 21230, "/path", cassette);
        }
    }

    //endregion Static Methods

}
