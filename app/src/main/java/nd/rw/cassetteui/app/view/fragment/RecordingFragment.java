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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.ButterKnife;
import nd.rw.cassetteui.R;
import nd.rw.cassetteui.app.listeners.MainViewPagerMotionBlocker;
import nd.rw.cassetteui.app.listeners.OnCassetteClickedHandler;
import nd.rw.cassetteui.app.model.CassetteModel;
import nd.rw.cassetteui.app.presenter.ListCassettePresenter;
import nd.rw.cassetteui.app.utils.AudioPlayer;
import nd.rw.cassetteui.app.utils.RecordingAssistant;
import nd.rw.cassetteui.app.utils.VoiceRecorder;
import nd.rw.cassetteui.app.view.ListCassettesView;
import nd.rw.cassetteui.app.view.adapter.CassetteSpinnerAdapter;

public class RecordingFragment
        extends Fragment
        implements MainViewPagerMotionBlocker,
        ListCassettesView{

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

    @Bind(R.id.recording_layout)
    public RelativeLayout layout;

    public int c_white = Color.WHITE;

    @BindColor(R.color.accent)
    public int c_accentRed;

    @BindColor(R.color.primary)
    public int c_primaryIndigo;

    @Bind(R.id.cassette_spinner)
    public Spinner sp_cassettesToChoose;
    private CassetteSpinnerAdapter cassetteSpinnerAdapter;
    private ListCassettePresenter presenter;
    private RecordingAssistant recordingAssistant;

    private boolean isRecording;

    private ActivationMethod howWasRecordingActivated;

    //endregion Fields

    //region Fragment overridden methods

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_section_recording, container, false);
        ButterKnife.bind(this, rootView);
        this.setUpListeners();
        this.cassetteSpinnerAdapter = new CassetteSpinnerAdapter(new LinkedList<CassetteModel>());
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.presenter = new ListCassettePresenter(this);
        this.presenter.initialize();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        String internalSotrageDirectoryRoot
                = context.getFilesDir()+ "/";
        recordingAssistant = new RecordingAssistant(internalSotrageDirectoryRoot);
    }

    //endregion Fragment overridden methods

    //region ListCassetteView Methods

    @Override
    public void renderCassetteList(Collection<CassetteModel> cassetteModelCollection) {
        if (cassetteModelCollection == null) {
            throw new RuntimeException("Cassette model collection should not be null.");
        }
        this.cassetteSpinnerAdapter.setCassetteModelList((List<CassetteModel>) cassetteModelCollection);
        this.sp_cassettesToChoose.setAdapter(cassetteSpinnerAdapter);
    }

    @Override
    public void viewCassette(CassetteModel cassetteModel) {

    }

    @Override
    public void setOnCassetteClicked(OnCassetteClickedHandler onCassetteClickedHandlerListener) {

    }

    @Override
    public void onAddedCassette(CassetteModel cassetteModel) {
        cassetteSpinnerAdapter.addCassette(cassetteModel);
        refreshSpinner();
    }

    @Override
    public void onUpdatedCassette(CassetteModel cassetteModel) {
        cassetteSpinnerAdapter.updateCassette(cassetteModel);
        refreshSpinner();
    }

    @Override
    public void onDeleteCassette(int cassetteId) {
        cassetteSpinnerAdapter.deleteCassette(cassetteId);
        refreshSpinner();
    }

    @Override
    public void onDeleteCassette(CassetteModel cassette) {
        cassetteSpinnerAdapter.deleteCassette(cassette);
        refreshSpinner();
    }

    private void refreshSpinner(){
        sp_cassettesToChoose.setAdapter(cassetteSpinnerAdapter);
        sp_cassettesToChoose.postInvalidate();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showRetry() {

    }

    @Override
    public void hideRetry() {

    }

    @Override
    public void showError(String message) {

    }

    //endregion ListCassetteView Methods

    //region Helper methods

    //region Recording Helper Methods

    private void setUpListeners(){
        b_recording.setOnTouchListener(speakTouchListener);
        sp_cassettesToChoose.setOnItemSelectedListener(cassetteSpinnerSelectionListener);
    }

    private void stopRecording(){
        if (this.isRecording){
            animateBackgroundColor(c_accentRed, c_white);
            setButtonToIdle();
            vibrateEnd();
            isRecording = !isRecording;
            recordingAssistant.stopRecording();
        }
    }

    private void startRecording(){
        if(!isRecording){
            animateBackgroundColor(c_white, c_accentRed);
            setButtonToActive();
            vibrateBegin();
            isRecording = !isRecording;
            recordingAssistant.startRecording();
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

    //endregion Recording Helper Methods

    //endregion Helper methods

    //region Listeners and events

    private AdapterView.OnItemSelectedListener cassetteSpinnerSelectionListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            CassetteModel cassetteAtPosition = (CassetteModel)cassetteSpinnerAdapter.getItem(position);
            recordingAssistant.changeCassette(cassetteAtPosition);
            Log.d(TAG, "onItemSelected: RecordingAssistant current cassette title: "
                    + recordingAssistant.getSelectedCassetteTitle());
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

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
