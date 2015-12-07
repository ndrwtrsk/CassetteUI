package nd.rw.cassetteui.app.model;

import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

public class CassetteModel {

    int id;
    String title;
    String description;
    GregorianCalendar date;
    int numberOfRecordings;
    List<RecordingModel> recordingList = new LinkedList<>();

    public CassetteModel(int id, String title, String description, GregorianCalendar date, int numberOfRecordings) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = date;
        this.numberOfRecordings = numberOfRecordings;
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

    public static List<CassetteModel> getListOfCassettes(int numberOfCassettes, int recordingsPerCassette){

        List<CassetteModel> resultList = new LinkedList<>();

        int currentStartingIndexOfNewRecordings = 0;
        for (int i = 1; i <= numberOfCassettes; i++) {
            CassetteModel cassette = new CassetteModel(i, "Cassette #" + i, "Lorem ipsum good stuff", new GregorianCalendar(), recordingsPerCassette);
            RecordingModel.populateCassetteWithRecordings(cassette, currentStartingIndexOfNewRecordings, recordingsPerCassette - 1);
            currentStartingIndexOfNewRecordings += recordingsPerCassette;
        }

        return resultList;
    }


}
