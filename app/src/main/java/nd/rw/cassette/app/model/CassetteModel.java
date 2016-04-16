package nd.rw.cassette.app.model;

import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import nd.rw.cassette.app.model.descriptors.CassetteModelDescriptor;

public class CassetteModel implements Comparable<CassetteModel>{

    private static final String TAG = "CAS_MOD";
    public int id;
    public String title;
    public String description;
    public GregorianCalendar creationDate;
    public List<RecordingModel> recordingList = new LinkedList<>();

    public CassetteModel(int id, String title, String description, GregorianCalendar date) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.creationDate = date;
    }

    public CassetteModel(int id, String title, String description) {
        this(id, title, description, new GregorianCalendar());
    }

    public CassetteModel(String title, String description) {
        this.title = title;
        this.description = description;
        this.creationDate = new GregorianCalendar();
    }


    //region Methods

    public int getNumberOfRecordings() {
        if (recordingList == null){
            return 0;
        }
        return recordingList.size();
    }

    public int getTotalDuration(){
        int sumOfDurationsInSeconds = 0;
        for (RecordingModel recording :
                recordingList) {
            sumOfDurationsInSeconds += recording.getDurationInSeconds();
        }

        return sumOfDurationsInSeconds;
    }

    public CassetteModelDescriptor getDescriptor(){
        return new CassetteModelDescriptor(this);
    }

    public void substituteTitleAndDescription(CassetteModel cassetteModel){
        this.title = cassetteModel.title;
        this.description = cassetteModel.description;
    }

    public GregorianCalendar getNewestRecordingDate(){
        if(recordingList == null || recordingList.size() == 0){
            return null;
        }
        GregorianCalendar result = recordingList.get(0).dateRecorded;

        for (int i = 1; i < recordingList.size(); i++) {
            GregorianCalendar currentRecordingsDate = recordingList.get(i).dateRecorded;
            if (result.compareTo(currentRecordingsDate) < 0){
                result = currentRecordingsDate;
            }
        }

        return result;
    }

    @Override
    public String toString() {
        return "CassetteModel{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    /**
     * Compares this Cassette to another cassette. This Cassette is considered greater, if it's newest
     * recording creationDate is newer than the another one's.
     *
     * However if their ID's are equal(meaning they are the same entity...) return 0.
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
    public int compareTo(CassetteModel another) {
        // TODO: 16.04.2016 Simplify this mess!!!!!!!
        if (another == null) {
            return 1;
        }
        if (id == another.id){
            return 0;
        }
        if (this.getNewestRecordingDate() == null) {
            return -1;
        }
        if (another.getNewestRecordingDate() == null) {
            return 1;
        }

        return -this.getNewestRecordingDate().compareTo(another.getNewestRecordingDate());
    }

    //endregion Methods

    //region Static Methods

    public static List<CassetteModel> getListOfCassettes(int numberOfCassettes, int recordingsPerCassette){

        List<CassetteModel> resultList = new LinkedList<>();

        /*int currentStartingIndexOfNewRecordings = 0;
        for (int i = 1; i <= numberOfCassettes; i++) {
            CassetteModel cassette = new CassetteModel(i, "Cassette #" + i, "Lorem ipsum good stuff", new GregorianCalendar());
            RecordingModel.populateCassetteWithRecordings(cassette, currentStartingIndexOfNewRecordings, currentStartingIndexOfNewRecordings + recordingsPerCassette);
            Log.d(TAG, "getListOfCassettes: recording in currrent cassette: " + cassette.getNumberOfRecordings());
            resultList.add(cassette);
            currentStartingIndexOfNewRecordings += recordingsPerCassette;
        }

        Log.d(TAG, "getListOfCassettes: number of cassettes:" + resultList.size());*/
        return resultList;
    }

    //endregion Static Methods
}
