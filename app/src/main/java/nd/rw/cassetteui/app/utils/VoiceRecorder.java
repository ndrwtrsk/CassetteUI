package nd.rw.cassetteui.app.utils;

import android.media.MediaRecorder;
import android.util.Log;

import java.io.IOException;

public class VoiceRecorder {
    private static final String LOG_TAG = "VoiceRecorder";

    //region Fields

    private MediaRecorder mediaRecorder;

    //endregion Fields

    public void startRecording(String fileName){

        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setOutputFile(fileName);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mediaRecorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

        mediaRecorder.start();
        
    }

    public void stopRecording() {
        mediaRecorder.stop();
        mediaRecorder.release();
        mediaRecorder = null;
    }

}
