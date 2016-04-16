package nd.rw.cassette.data.repository;

import android.util.Log;

import java.util.Date;
import java.util.List;

import nd.rw.cassette.app.model.CassetteModel;
import nd.rw.cassette.app.model.RecordingModel;
import nd.rw.cassette.data.entity.mapper.RecordingMapper;
import nd.rw.cassette.data.repository.datasource.RecordingDataStore;
import nd.rw.cassette.domain.repository.RecordingRepository;

public class RecordingDataRepository implements RecordingRepository {

    private static final String TAG = "RecDataRepo";
    private RecordingDataStore dataStore;

    public RecordingDataRepository(RecordingDataStore dataStore) {
        this.dataStore = dataStore;
    }

    @Override
    public List<RecordingModel> getAll() {
        return RecordingMapper.map(dataStore.getAll());
    }

    @Override
    public List<RecordingModel> getAllForCassette(CassetteModel cassetteModel) {
        if (cassetteModel == null) {
            return null;
        }
        return this.getAllForCassette(cassetteModel.id);
    }

    @Override
    public List<RecordingModel> getAllForCassette(int cassetteId) {
        return RecordingMapper.map(dataStore.getAllForCassette(cassetteId));
    }

    @Override
    public RecordingModel get(int id) {
        return RecordingMapper.map(dataStore.get(id));
    }

    @Override
    public RecordingModel create(RecordingModel recordingModel) {
        Log.d(TAG, "create() called with: " + "recordingModel = [" + recordingModel + "]");
        if (recordingModel == null) {
            return null;
        }
        String path = recordingModel.path;
        Date date = recordingModel.dateRecorded.getTime();
        int cassetteId = recordingModel.cassette.id;
        int duration = recordingModel.durationInMs;
        return RecordingMapper.map(dataStore.create(path, duration, date, cassetteId));
    }

    @Override
    public boolean update(RecordingModel recordingModel) {
        return dataStore.update(recordingModel.id, recordingModel.title, recordingModel.transcription, recordingModel.path);
    }

    @Override
    public boolean delete(RecordingModel recordingModel) {
        if (recordingModel == null) {
            return false;
        }
        return this.delete(recordingModel.id);
    }

    @Override
    public boolean delete(int id) {
        return dataStore.delete(id);
    }

    @Override
    public long count() {
        return dataStore.count();
    }
}
