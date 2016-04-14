package nd.rw.cassette.app.utils;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.util.Log;

import java.io.IOException;
import java.util.GregorianCalendar;

import nd.rw.cassette.app.model.CassetteModel;
import nd.rw.cassette.app.presenter.RecordPresenter;

public class RecordingAssistant {

    //region Fields

    private static final String TAG = "RecordingAssistant";
    private RecordPresenter recordingPresenter = new RecordPresenter();
    private MediaRecorder mediaRecorder;
    private String directoryRoot;
    private String fileName;
    private GregorianCalendar dateOfRecording;
    private int duration;

    //endregion Fields


    public RecordingAssistant(String directoryRoot) {
        this.directoryRoot = directoryRoot;
    }

    public void changeCassette(CassetteModel cassetteModel){
        recordingPresenter.setSelectedCassette(cassetteModel);
    }

    public String getSelectedCassetteTitle(){
        return recordingPresenter.getCassetteTitle();
    }

    /**
     * Gets Cassette title, sets the date and builds the filename from these things..
     */
    private void initializeForRecording(){
        String cassetteTitle = recordingPresenter.getCassetteTitle();
        dateOfRecording = new GregorianCalendar();

        fileName = directoryRoot +  "recording_" + cassetteTitle + "_"
                + GregorianCalendarUtils.getISO8601DateFormat(dateOfRecording);
    }

    public void startRecording(){
        initializeForRecording();
        mediaRecorder = new MediaRecorder();
        // TODO: 09.01.2016 try catch for no avaiable audio source
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setOutputFile(this.fileName);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mediaRecorder.prepare();
        } catch (IOException e) {
            Log.e(TAG, "prepare() failed", e);
        }

        mediaRecorder.start();
    }

    public void stopRecording(){
        mediaRecorder.stop();
        mediaRecorder.release();
        mediaRecorder = null;
        communicateRecordingToPresenter();
    }

    private void initializeDuration(){
        MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            /**
             * Interesting feature of MediaPlayer class.
             * MediaPlayer will refuse to open a file if there data source string is not prepended with
             * 'file://'.
             * As per:
             * http://stackoverflow.com/questions/15402201/mediaplayer-preparing-failed/15444165#15444165
             */
            mediaPlayer.setDataSource("file://"+fileName);
            mediaPlayer.prepare();
            duration = mediaPlayer.getDuration();
        }catch (IOException e) {
            Log.e(TAG, "initializeDuration: prepare() failed", e);
        }
        mediaPlayer.release();
    }

    private void communicateRecordingToPresenter(){
        initializeDuration();
        this.recordingPresenter.addNewRecording(fileName, dateOfRecording, duration);
    }
}
