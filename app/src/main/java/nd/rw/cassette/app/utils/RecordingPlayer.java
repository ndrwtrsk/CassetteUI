package nd.rw.cassette.app.utils;

import android.media.MediaPlayer;
import android.util.Log;

import java.io.IOException;

import nd.rw.cassette.app.model.RecordingModel;

/**
 * Recording player.
 */
public class RecordingPlayer {

    //region Fields

    public static final String TAG = "RecordingPlayer";

    private MediaPlayer mediaPlayer;
    private RecordingModel currentRecording;
    private MediaPlayer.OnCompletionListener onCompletionListener;

    //endregion Fields

    //default constructor

    //region Methods

    public void playRecording(RecordingModel recording) throws IOException {
        if (recording == null) {
            Log.e(TAG, "playRecording: Provided recording is null.");
            return;
        }
        if (currentRecording != null && currentRecording.id == recording.id) {
            if (!isPlaying()) {
                startPlayback();
            } else {
                pausePlayback();
            }
        } else {
            if (currentRecording != null) {
                dispose();
            }
            currentRecording = recording;
            preparePlayback(currentRecording);
            startPlayback();
        }
    }

    public void preparePlayback(RecordingModel recordingModel) throws IOException {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnCompletionListener(onCompletionListener);
        /**
         * Interesting feature of MediaPlayer class.
         * MediaPlayer will refuse to open a file if there data source string is not prepended with
         * 'file://'.
         * As per:
         * http://stackoverflow.com/questions/15402201/mediaplayer-preparing-failed/15444165#15444165
         */
        mediaPlayer.setDataSource("file://" + recordingModel.path);
        mediaPlayer.setLooping(false);
        mediaPlayer.prepare();
    }

    public void seekTo(int progress) {
        if (mediaPlayer != null) {
            Log.d(TAG, "seekTo: seeking playback from: " + mediaPlayer.getCurrentPosition() + " out of: " + mediaPlayer.getDuration());
            mediaPlayer.seekTo(progress);
        }
    }

    public boolean isPlaying() {
        return mediaPlayer != null && mediaPlayer.isPlaying();
    }

    public int getDuration() {
        return mediaPlayer.getDuration();
    }

    public int getCurrentPosition() {
        if (mediaPlayer == null) {
            return -1;
        }
        return mediaPlayer.getCurrentPosition();
    }

    public void startPlayback() {
        if (mediaPlayer != null) {
            Log.d(TAG, "startPlayback: startying playback from: " + mediaPlayer.getCurrentPosition() + " out of: " + mediaPlayer.getDuration());
            mediaPlayer.start();
        }
    }

    public void pausePlayback() {
        if (mediaPlayer != null) {
            Log.d(TAG, "pause: pausing playback from: " + mediaPlayer.getCurrentPosition() + " out of: " + mediaPlayer.getDuration());
            mediaPlayer.pause();
        }
    }

    public void setOnCompletionListener(MediaPlayer.OnCompletionListener onCompletionListener) {
        this.onCompletionListener = onCompletionListener;
    }

    private void stopPlayback() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    public void dispose() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        mediaPlayer = null;
    }

    //endregion Methods
}
