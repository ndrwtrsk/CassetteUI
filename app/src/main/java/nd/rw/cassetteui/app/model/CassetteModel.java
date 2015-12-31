package nd.rw.cassetteui.app.model;

import android.util.Log;

import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import nd.rw.cassetteui.app.model.descriptors.CassetteModelDescriptor;

public class CassetteModel implements Comparable<CassetteModel>{

    private static final String TAG = "CAS_MOD";
    int id;
    String title;
    String description;
    GregorianCalendar date;
    List<RecordingModel> recordingList = new LinkedList<>();

    public CassetteModel(int id, String title, String description, GregorianCalendar date) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = date;
    }

    public CassetteModel(int id, String title, String description) {
        this(id, title, description, new GregorianCalendar());
    }

    public CassetteModel(String title, String description) {
        this.title = title;
        this.description = description;
        this.date = new GregorianCalendar();
    }

    //region Getters

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public GregorianCalendar getDate() {
        return date;
    }

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

    /**
     * Returns humanly readable descriptor of this Cassette.
     * @return Humanly readable descriptor of this Cassette.
     */
    public CassetteModelDescriptor getDescriptor(){
        return new CassetteModelDescriptor(this);
    }

    public List<RecordingModel> getRecordingList() {
        return recordingList;
    }

    //endregion Getters

    //region Setters

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRecordingList(List<RecordingModel> recordingList) {
        this.recordingList = recordingList;
    }

    //endregion Setters

    //region Methods

    /**
     * Substitutes the title and description of this Model with these of provided
     * Model. Useful in updates.
     */
    public void update(CassetteModel cassetteModel){
        this.title = cassetteModel.getTitle();
        this.description = cassetteModel.getDescription();
    }

    /**
     * Returns GregorianCalendar of the newest Recording.
     * If no recordings are present, null is returned.
     * @return GregorianCalendar of the newest Recording.
     */
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
     * recording date is newer than the another one's.
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
        if (another == null) {
            return 1;
        }
        if (getId() == another.getId()){
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
