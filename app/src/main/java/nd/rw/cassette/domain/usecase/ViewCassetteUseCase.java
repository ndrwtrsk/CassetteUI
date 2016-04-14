package nd.rw.cassette.domain.usecase;

import nd.rw.cassette.app.model.CassetteModel;
import nd.rw.cassette.app.model.RecordingModel;

/**
 * UseCase in which the user enters the cassette and from that point onwards is able to do the following:
 * 1) View details of the cassette (things inside CassetteModel)
 * 2) View Recordings on this Cassette.
 * 3) Update Title and Description.
 * 4) Delete Recordings.
 */
public class ViewCassetteUseCase extends AbstractUseCase{

    public CassetteModel getCassetteById(int id){
        return cassetteRepository.get(id);
    }
    public boolean deleteRecording(RecordingModel recording) {
        return recordingRepository.delete(recording);
    }
    public boolean deleteCassette(int id){
        return cassetteRepository.delete(id);
    }
    public boolean updateCassette(CassetteModel cassetteModel){
        return cassetteRepository.update(cassetteModel);
    }

}
