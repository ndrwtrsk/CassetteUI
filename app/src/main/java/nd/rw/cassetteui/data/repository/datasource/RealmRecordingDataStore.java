package nd.rw.cassetteui.data.repository.datasource;

import android.util.Log;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import io.realm.Realm;
import nd.rw.cassetteui.data.entity.CassetteEntity;
import nd.rw.cassetteui.data.entity.RecordingEntity;

public class RealmRecordingDataStore implements RecordingDataStore{

    private static final String TAG = "RealmRecDataStore";
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
        Log.d(TAG, "create() called with: " + "path = [" + path + "], durationInMs = [" + durationInMs + "], date = [" + date + "], cassetteId = [" + cassetteId + "]");
        CassetteEntity cassetteEntity = mRealm.where(CassetteEntity.class).equalTo("id", cassetteId).findFirst();
        if (cassetteEntity == null) {
            Log.e(TAG, "create: CassetteEntity was null!");
            return null;
        }
        mRealm.beginTransaction();
        RecordingEntity recordingEntity = mRealm.createObject(RecordingEntity.class);
        recordingEntity.setId(this.getNextPkValue());
        recordingEntity.setPath(path);
        recordingEntity.setDurationInMs(durationInMs);
        recordingEntity.setDateRecorded(date);
        recordingEntity.setCassette(cassetteEntity);
        recordingEntity = mRealm.copyToRealm(recordingEntity);
        /**
         * Newly created Recording has to be added to the parent object in order for it's
         * relation ship to parent to be recognized by the database, hence below call
         * to get CassetteEntity's list of recordings and subsequent addition of newly
         * created recording.
         *
         * Simple and nice.
         */
        cassetteEntity.getRecordingEntities().add(recordingEntity);
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
        return !exists(id); //CHECK THAT IT _DOESN'T_ EXIST!!!
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
        Number maximumNumber = mRealm.where(RecordingEntity.class).max("id");
        if (maximumNumber == null) {
            return 1;
        } else {
            return maximumNumber.intValue() + 1;
        }
    }

    private boolean exists(final int id){
        return mRealm.where(RecordingEntity.class)
                .equalTo("id", id).findFirst() != null;
    }
}
