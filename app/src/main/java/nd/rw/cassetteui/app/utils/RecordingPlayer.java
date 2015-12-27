package nd.rw.cassetteui.app.utils;

import android.util.Log;

import java.io.IOException;

import nd.rw.cassetteui.app.model.RecordingModel;

/**
 * Recording player.
 */
public class RecordingPlayer {

    //region Fields
    public static final String TAG = "RecordingPlayer";

    private AudioPlayer audioPlayer = new AudioPlayer();
    private RecordingModel currentRecording;
    private boolean isPaused = false;
    //endregion Fields

    //default constructor

    //region Methods

    public void playRecording(RecordingModel recording) throws IOException {
        if (recording == null) {
            Log.e(TAG, "playRecording: Provided recording is null.");
            return;
        }
        if (currentRecording != null && currentRecording.id == recording.id){
            if (isPaused){
                audioPlayer.startPlayback();
            }
            else{
                this.audioPlayer.pausePlayback();
            }
            isPaused = !isPaused;
        } else {
            if (currentRecording != null){
                this.audioPlayer.dispose();
            }
            currentRecording = recording;
            this.audioPlayer.startPlaybackFile(currentRecording.path);
        }
    }

    public void dispose(){
        if (audioPlayer != null) {
            this.audioPlayer.dispose();
        }
        this.audioPlayer = null;
    }

    //endregion Methods
}
