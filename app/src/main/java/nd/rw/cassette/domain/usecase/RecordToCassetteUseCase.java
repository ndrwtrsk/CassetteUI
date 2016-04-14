package nd.rw.cassette.domain.usecase;

import android.util.Log;

import nd.rw.cassette.app.model.RecordingModel;

public class RecordToCassetteUseCase extends AbstractUseCase{

    private static final String TAG = "RecordUseCase";

    public RecordingModel addRecording(RecordingModel recordingModel){
        Log.d(TAG, "addRecording() called with: " + "recordingModel = [" + recordingModel + "]");
        return recordingRepository.create(recordingModel);
    }

}
