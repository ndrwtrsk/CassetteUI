package nd.rw.cassetteui.domain.usecase;

import android.util.Log;

import nd.rw.cassetteui.app.model.RecordingModel;
import nd.rw.cassetteui.data.repository.RecordingDataRepository;
import nd.rw.cassetteui.data.repository.datasource.DataSourceFactory;
import nd.rw.cassetteui.domain.repository.RecordingRepository;

public class RecordToCassetteUseCase extends AbstractUseCase{

    private static final String TAG = "RecordUseCase";
    private RecordingRepository recordingRepository
            = new RecordingDataRepository(DataSourceFactory.getRealmRecordingDataStore());

    public RecordingModel addRecording(RecordingModel recordingModel){
        Log.d(TAG, "addRecording() called with: " + "recordingModel = [" + recordingModel + "]");
        return recordingRepository.create(recordingModel);
    }

}
