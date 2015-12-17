package nd.rw.cassetteui.data.repository;

import java.util.Date;
import java.util.List;

import nd.rw.cassetteui.app.model.CassetteModel;
import nd.rw.cassetteui.app.model.RecordingModel;
import nd.rw.cassetteui.data.entity.mapper.RecordingMapper;
import nd.rw.cassetteui.data.repository.datasource.RecordingDataStore;
import nd.rw.cassetteui.domain.repository.RecordingRepository;

public class RecordingDataRepository implements RecordingRepository {

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
        return this.getAllForCassette(cassetteModel.getId());
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
        if (recordingModel == null) {
            return null;
        }
        String path = recordingModel.path;
        Date date = recordingModel.dateRecorded.getTime();
        int cassetteId = recordingModel.cassette.getId();
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
