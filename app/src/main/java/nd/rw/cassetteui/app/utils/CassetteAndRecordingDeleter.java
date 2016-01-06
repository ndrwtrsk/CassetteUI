package nd.rw.cassetteui.app.utils;

import android.util.Log;

import com.annimon.stream.Stream;

import java.util.List;

import nd.rw.cassetteui.app.model.CassetteModel;
import nd.rw.cassetteui.app.model.RecordingModel;

/**
 * Provides functionalities for deleting Cassettes and Recordings content on the the device.
 */
public class CassetteAndRecordingDeleter {

    private static final String TAG = "CassRecDeleter";
    private AndroidFileUtils androidFileUtils;

    public CassetteAndRecordingDeleter(AndroidFileUtils androidFileUtils) {
        this.androidFileUtils = androidFileUtils;
    }

    /**
     * Deletes all the Recording files which may are associated with this Cassette.
     */
    public void deleteCassetteContents(CassetteModel cassette){
        List<RecordingModel> recordings = cassette.getRecordingList();

        Stream.of(recordings).forEach(this::deleteRecordingContent);
    }

    /**
     * Deletes the file associated with provided recording.
     */
    public void deleteRecordingContent(RecordingModel recordingModel){
        Log.d(TAG, "deleteRecordingContent: file exists before delete? " + androidFileUtils.fileExists(recordingModel.path));
        this.androidFileUtils.deleteFile(recordingModel.path);
        Log.d(TAG, "deleteRecordingContent: file exists after delete? " + androidFileUtils.fileExists(recordingModel.path));
    }
}
