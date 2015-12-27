package nd.rw.cassetteui.domain.usecase;

import nd.rw.cassetteui.app.model.RecordingModel;
import nd.rw.cassetteui.data.repository.RecordingDataRepository;
import nd.rw.cassetteui.data.repository.datasource.DataSourceFactory;
import nd.rw.cassetteui.domain.repository.RecordingRepository;

public class DeleteRecordingUseCase {

    private RecordingRepository recordingRepository
            = new RecordingDataRepository(DataSourceFactory.getRealmRecordingDataStore());

    public boolean deleteRecording(RecordingModel recording){
        return recordingRepository.delete(recording);
    }
}
