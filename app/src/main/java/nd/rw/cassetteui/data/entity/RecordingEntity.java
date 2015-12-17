package nd.rw.cassetteui.data.entity;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class RecordingEntity extends RealmObject{

    //region Fields

    @PrimaryKey
    private int id;
    private String title;
    private String transcription;
    @Required
    private Date dateRecorded;
    private int durationInMs;
    @Required
    private String path;
    private CassetteEntity cassette;

    //endregion Fields

    //region Getters Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTranscription() {
        return transcription;
    }

    public void setTranscription(String transcription) {
        this.transcription = transcription;
    }

    public Date getDateRecorded() {
        return dateRecorded;
    }

    public void setDateRecorded(Date dateRecorded) {
        this.dateRecorded = dateRecorded;
    }

    public int getDurationInMs() {
        return durationInMs;
    }

    public void setDurationInMs(int durationInMs) {
        this.durationInMs = durationInMs;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public CassetteEntity getCassette() {
        return cassette;
    }

    public void setCassette(CassetteEntity cassette) {
        this.cassette = cassette;
    }


    //endregion Getters Setters
}
