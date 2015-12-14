package nd.rw.cassetteui.app.view.fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.ButterKnife;
import nd.rw.cassetteui.R;
import nd.rw.cassetteui.app.listeners.MainViewPagerMotionBlocker;
import nd.rw.cassetteui.app.utils.AudioPlayer;
import nd.rw.cassetteui.app.utils.VoiceRecorder;

public class RecordingFragment
        extends Fragment
        implements MainViewPagerMotionBlocker{

    enum ActivationMethod{
        ScreenButtonActivated,
        VolumeUpDownActivated
    }

    //region Fields

    private static String TAG = "REC_ACT";

    // TODO: 14.12.2015 Create a separate class for recording button and move some of the behaviour
    // defined for the button in this fragment class to aforementioned recording button class.
    @Bind(R.id.recording_main_button)
    public Button b_recording;

    @Bind(R.id.play_button)
    public Button b_play;

    @Bind(R.id.recording_layout)
    public RelativeLayout layout;

    public int c_white = Color.WHITE;

    @BindColor(R.color.accent)
    public int c_accentRed;

    @BindColor(R.color.primary)
    public int c_primaryIndigo;

    private boolean isRecording;
    private String fileName;

    private ActivationMethod howWasRecordingActivated;

    private VoiceRecorder voiceRecorder = new VoiceRecorder();
    private AudioPlayer audioPlayer = new AudioPlayer();

    //endregion Fields

    //region Fragment overridden methods

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_section_recording, container, false);
        ButterKnife.bind(this, rootView);
        this.setUpListeners();
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fileName = context.getFilesDir() + "/audio";
    }

    //endregion Fragment overridden methods

    //region Helper methods

    private void setUpListeners(){
        b_recording.setOnTouchListener(speakTouchListener);
        b_play.setOnClickListener(playButtonListener);
    }

    private void stopRecording(){
        if (this.isRecording){
            animateBackgroundColor(c_accentRed, c_white);
            setButtonToIdle();
            vibrateEnd();
            isRecording = !isRecording;
            voiceRecorder.stopRecording();
        }

    }

    private void startRecording(){
        if(!isRecording){
            animateBackgroundColor(c_white, c_accentRed);
            setButtonToActive();
            vibrateBegin();
            isRecording = !isRecording;
            voiceRecorder.startRecording(fileName);
        }
    }

    private void vibrateBegin(){
        Vibrator v = (Vibrator) this.getActivity().getSystemService(this.getActivity().VIBRATOR_SERVICE);
        long[] pattern = { 0, 25, 75, 25};
        v.vibrate(pattern, -1);
    }

    private void vibrateEnd(){
        Vibrator v = (Vibrator) this.getActivity().getSystemService(this.getActivity().VIBRATOR_SERVICE);
        v.vibrate(25);
    }

    private void animateBackgroundColor(@ColorInt final int fromColor, @ColorInt final int targetColor){
        ColorDrawable[] color = {new ColorDrawable(fromColor), new ColorDrawable(targetColor)};
        TransitionDrawable trans = new TransitionDrawable(color);
        layout.setBackground(trans);
        trans.startTransition(300);
    }

    private void setButtonToActive(){
        TransitionDrawable transition = (TransitionDrawable) b_recording.getBackground();
        transition.startTransition(300);
    }

    private void setButtonToIdle(){
        TransitionDrawable transition = (TransitionDrawable) b_recording.getBackground();
        transition.reverseTransition(25);
    }

    //endregion Helper methods

    //region Listeners and events

    private View.OnTouchListener speakTouchListener = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View pView, MotionEvent pEvent) {
            //pView.onTouchEvent(pEvent);
            if (howWasRecordingActivated == ActivationMethod.VolumeUpDownActivated){
                return false;
            }
            if (pEvent.getAction() == MotionEvent.ACTION_UP) {
                stopRecording();
                howWasRecordingActivated = null;
            }
            else if (pEvent.getAction() == MotionEvent.ACTION_DOWN){
                startRecording();
                howWasRecordingActivated = ActivationMethod.ScreenButtonActivated;
            }
            return false;
        }
    };

    private boolean isPlaying = false;

    private View.OnClickListener playButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!isPlaying){
                audioPlayer.startPlaybackFile(fileName);
            }else{
                audioPlayer.stopPlayback();
            }
            isPlaying = !isPlaying;
        }
    };

    /*
        Returning true from the two following methods will prevent the event from being propagated further
        and thus the volume won't be increased or decreased.
     */

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return processKeyEvent(true, keyCode, event);
    }

    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return processKeyEvent(false, keyCode, event);
    }

    public boolean processKeyEvent(boolean isDown, int keyCode, KeyEvent event){

        // FIXME: 04.12.2015    Figure out what happens when two buttons are pressed simultaneously
        //                      or when one of them is pressed when is already held and fix this!
        if (howWasRecordingActivated == ActivationMethod.ScreenButtonActivated){
            return true;
        }

        if  (isDown){
            if (keyCode == KeyEvent.KEYCODE_VOLUME_UP || keyCode == KeyEvent.KEYCODE_VOLUME_DOWN){
                startRecording();
                howWasRecordingActivated = ActivationMethod.VolumeUpDownActivated;
            }
        } else {
            if (keyCode == KeyEvent.KEYCODE_VOLUME_UP || keyCode == KeyEvent.KEYCODE_VOLUME_DOWN){
                stopRecording();
                howWasRecordingActivated = null;
            }
        }
        return true;
    }

    //endregion Listeners and events

    @Override
    public boolean blockOrNot() {
        return isRecording;
    }

    //region Static Methods

    public static RecordingFragment newInstance(){
        return new RecordingFragment();
    }

    //endregion Static Methods

}
