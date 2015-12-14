package nd.rw.cassetteui.app.model;

import android.util.Log;

import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

public class CassetteModel {

    private static final String TAG = "CAS_MOD";
    int id;
    String title;
    String description;
    GregorianCalendar date;
    int numberOfRecordings = 0;
    List<RecordingModel> recordingList = new LinkedList<>();

    public CassetteModel(int id, String title, String description, GregorianCalendar date, int numberOfRecordings) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = date;
        this.numberOfRecordings = numberOfRecordings;
    }

    public CassetteModel(int id, String title, String description, GregorianCalendar date) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = date;
    }

    public int getTotalDuration(){
        int sumOfDurationsInSeconds = 0;
        for (RecordingModel recording :
                recordingList) {
            sumOfDurationsInSeconds += recording.getDurationInSeconds();
        }

        return sumOfDurationsInSeconds;
    }

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
        return numberOfRecordings;
    }

    public List<RecordingModel> getRecordingList() {
        return recordingList;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setNumberOfRecordings(int numberOfRecordings) {
        this.numberOfRecordings = numberOfRecordings;
    }

    public void update(CassetteModel cassetteModel){
        this.title = cassetteModel.getTitle();
        this.description = cassetteModel.getDescription();
        this.numberOfRecordings = cassetteModel.numberOfRecordings;
    }

    @Override
    public String toString() {
        return "CassetteModel{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", numberOfRecordings=" + numberOfRecordings +
                '}';
    }

    public static List<CassetteModel> getListOfCassettes(int numberOfCassettes, int recordingsPerCassette){

        List<CassetteModel> resultList = new LinkedList<>();

        int currentStartingIndexOfNewRecordings = 0;
        for (int i = 1; i <= numberOfCassettes; i++) {
            CassetteModel cassette = new CassetteModel(i, "Cassette #" + i, "Lorem ipsum good stuff", new GregorianCalendar(), recordingsPerCassette);
            RecordingModel.populateCassetteWithRecordings(cassette, currentStartingIndexOfNewRecordings, recordingsPerCassette - 1);
            resultList.add(cassette);
            currentStartingIndexOfNewRecordings += recordingsPerCassette;
        }

        Log.d(TAG, "getListOfCassettes: number of cassettes:" + resultList.size());
        return resultList;
    }
}
