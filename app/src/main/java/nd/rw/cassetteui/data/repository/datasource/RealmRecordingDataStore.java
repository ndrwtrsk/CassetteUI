package nd.rw.cassetteui.data.repository.datasource;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import io.realm.Realm;
import nd.rw.cassetteui.data.entity.CassetteEntity;
import nd.rw.cassetteui.data.entity.RecordingEntity;

public class RealmRecordingDataStore implements RecordingDataStore{

    private Realm mRealm;

    public RealmRecordingDataStore() {
        mRealm = Realm.getDefaultInstance();
    }

    @Override
    public List<RecordingEntity> getAll() {
        return mRealm.where(RecordingEntity.class).findAll();
    }

    @Override
    public List<RecordingEntity> getAllForCassette(final int cassetteId) {
        CassetteEntity cassetteEntity = mRealm.where(CassetteEntity.class).equalTo("id", cassetteId).findFirst();
        if (cassetteEntity == null) {
            return new LinkedList<>();
        }
        return cassetteEntity.getRecordingEntities();
    }

    @Override
    public RecordingEntity get(final int id) {
        return mRealm.where(RecordingEntity.class)
                .equalTo("id", id)
                .findFirst();
    }

    @Override
    public RecordingEntity create(RecordingEntity recordingEntity) {
        if (recordingEntity == null) {
            return null;
        }
        recordingEntity.setId(this.getNextPkValue());
        mRealm.beginTransaction();
        RecordingEntity createdRecording = mRealm.copyToRealm(recordingEntity);
        mRealm.commitTransaction();
        return createdRecording;
    }

    @Override
    public RecordingEntity create(String path, int durationInMs, Date date, int cassetteId) {
        CassetteEntity cassetteEntity = mRealm.where(CassetteEntity.class).equalTo("id", cassetteId).findFirst();
        if (cassetteEntity == null) {
            return null;
        }
        RecordingEntity recordingEntity = mRealm.createObject(RecordingEntity.class);
        recordingEntity.setId(this.getNextPkValue());
        recordingEntity.setPath(path);
        recordingEntity.setDurationInMs(durationInMs);
        recordingEntity.setDateRecorded(date);
        recordingEntity.setCassette(cassetteEntity);
        mRealm.beginTransaction();
        recordingEntity = mRealm.copyToRealm(recordingEntity);
        mRealm.commitTransaction();
        return recordingEntity;
    }

    @Override
    public boolean update(RecordingEntity recordingEntity) {
        if (recordingEntity == null) {
            return false;
        }
        mRealm.beginTransaction();
        RecordingEntity updateRecording = mRealm.copyToRealmOrUpdate(recordingEntity);
        mRealm.commitTransaction();
        return updateRecording != null;
    }

    @Override
    public boolean update(int id, String title, String transcription, String path) {
        RecordingEntity recordingEntity = this.get(id);
        if (recordingEntity == null) {
            return false;
        }
        recordingEntity.setTitle(title);
        recordingEntity.setTranscription(transcription);
        recordingEntity.setPath(path);
        mRealm.beginTransaction();
        recordingEntity = mRealm.copyToRealmOrUpdate(recordingEntity);
        mRealm.commitTransaction();

        return recordingEntity != null;
    }

    @Override
    public boolean delete(RecordingEntity recordingEntity) {
        if (recordingEntity == null) {
            return false;
        }
        int id = recordingEntity.getId();
        mRealm.beginTransaction();
        recordingEntity.removeFromRealm();
        mRealm.commitTransaction();
        return exists(id);
    }

    @Override
    public boolean delete(final int id) {
        return this.delete(this.get(id));
    }

    @Override
    public long count() {
        return mRealm.where(RecordingEntity.class).count();
    }

    @Override
    public int getNextPkValue() {
        return mRealm.where(RecordingEntity.class).max("id").intValue() + 1;
    }

    private boolean exists(final int id){
        return mRealm.where(RecordingEntity.class)
                .equalTo("id", id).findFirst() != null;
    }
}
