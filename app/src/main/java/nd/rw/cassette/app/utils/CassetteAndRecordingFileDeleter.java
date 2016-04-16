package nd.rw.cassette.app.utils;

import android.util.Log;

import com.annimon.stream.Stream;

import java.util.List;

import nd.rw.cassette.app.model.CassetteModel;
import nd.rw.cassette.app.model.RecordingModel;

public class CassetteAndRecordingFileDeleter {

    private static final String TAG = "CassRecDeleter";

    /**
     * Deletes all the Recording files which may are associated with this Cassette.
     */
    public void deleteCassetteContents(CassetteModel cassette){
        List<RecordingModel> recordings = cassette.recordingList;

        Stream.of(recordings).forEach(this::deleteRecordingContent);
    }

    /**
     * Deletes the file associated with provided recording.
     */
    public void deleteRecordingContent(RecordingModel recordingModel){
        Log.d(TAG, "deleteRecordingContent: file exists before delete? " + FileUtils.fileExists(recordingModel.path));
        FileUtils.deleteFile(recordingModel.path);
        Log.d(TAG, "deleteRecordingContent: file exists after delete? " + FileUtils.fileExists(recordingModel.path));
    }
}
