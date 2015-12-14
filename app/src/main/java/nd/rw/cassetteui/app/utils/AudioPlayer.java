package nd.rw.cassetteui.app.utils;

import android.media.MediaPlayer;
import android.util.Log;

import java.io.IOException;

public class AudioPlayer {
    private static final String TAG = "AudioPlayer";

    private MediaPlayer mediaPlayer;

    public void startPlaybackFile(String fileName){
        mediaPlayer = new MediaPlayer();
        try{
            mediaPlayer.setDataSource(fileName);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            Log.d(TAG, "prepare() failed");
        }
    }

    public void stopPlayback(){
        mediaPlayer.release();
        mediaPlayer = null;
    }


}
