package nd.rw.cassetteui.data.entity;

import java.util.Date;
import java.util.GregorianCalendar;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class CassetteEntity extends RealmObject{

    //region Fields

    @PrimaryKey
    private int id;
    @Required
    private String title;
    private String description;
    @Required
    private Date date;
    private RealmList<RecordingEntity> recordingEntities;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public RealmList<RecordingEntity> getRecordingEntities() {
        return recordingEntities;
    }

    public void setRecordingEntities(RealmList<RecordingEntity> recordingEntities) {
        this.recordingEntities = recordingEntities;
    }

    //endregion Getters Setters
}
