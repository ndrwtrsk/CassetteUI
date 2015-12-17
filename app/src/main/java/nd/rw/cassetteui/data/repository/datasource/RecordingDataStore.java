package nd.rw.cassetteui.data.repository.datasource;

import java.util.Date;
import java.util.List;

import nd.rw.cassetteui.data.entity.RecordingEntity;

public interface RecordingDataStore {

    List<RecordingEntity> getAll();

    List<RecordingEntity> getAllForCassette(final int cassetteId);

    RecordingEntity get(final int id);

    RecordingEntity create(RecordingEntity recordingEntity);

    RecordingEntity create(String path, int durationInMs, Date date, int cassetteId);

    boolean update(RecordingEntity recordingEntity);

    boolean update(int id, String title, String transcription, String path);

    boolean delete(RecordingEntity recordingEntity);

    boolean delete(final int id);

    long count();

    int getNextPkValue();
}
