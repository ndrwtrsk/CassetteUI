package nd.rw.cassetteui.domain.repository;

import java.util.List;

import nd.rw.cassetteui.app.model.CassetteModel;
import nd.rw.cassetteui.app.model.RecordingModel;

/**
 * Defines contract for all implementing classes to realize in order to become a repository
 * for Recordings.
 * Serves as an interface for communicating with Data Access Layer.
 */
public interface RecordingRepository {

    List<RecordingModel> getAll();

    List<RecordingModel> getAllForCassette(CassetteModel cassetteModel);

    List<RecordingModel> getAllForCassette(final int cassetteId);

    RecordingModel get(final int id);

    RecordingModel create(RecordingModel recordingModel);

    boolean update(RecordingModel recordingModel);

    boolean delete(RecordingModel recordingModel);

    boolean delete(final int id);

    long count();
    
}
