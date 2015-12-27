package nd.rw.cassetteui.app.utils;

import android.media.MediaPlayer;
import android.util.Log;

import java.io.IOException;

/**
 * Provides functionalities around MediaPlayer class. This class is statetless and doesn't give a damn
 * what recording is currently played. This is is the responsibility of the RecordingPlayer class.
 */
public class AudioPlayer {
    private static final String TAG = "AudioPlayer";

    private MediaPlayer mediaPlayer;

    public void startPlaybackFile(String fileName) throws IOException {
        mediaPlayer = new MediaPlayer();
        /**
         * Interesting feature of MediaPlayer class.
         * MediaPlayer will refuse to open a file if there data source string is not prepended with
         * 'file://'.
         * As per:
         * http://stackoverflow.com/questions/15402201/mediaplayer-preparing-failed/15444165#15444165
         */
        mediaPlayer.setDataSource("file://"+fileName);
        mediaPlayer.prepare();
        mediaPlayer.start();
    }

    public void startPlayback(){
        mediaPlayer.start();
    }

    public void pausePlayback() {
        mediaPlayer.pause();
    }

    public void stopPlayback() {
        mediaPlayer.stop();
    }

    public boolean isPlaying(){
        return mediaPlayer.isPlaying();
    }

    public void dispose() {
        if (mediaPlayer != null){
            mediaPlayer.release();
        }
        mediaPlayer = null;
    }


}
