package nd.rw.cassette.domain.usecase;

import nd.rw.cassette.app.model.CassetteModel;
import nd.rw.cassette.app.model.RecordingModel;

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
