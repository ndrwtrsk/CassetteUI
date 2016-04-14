package nd.rw.cassette.app.view.activity;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import nd.rw.cassette.R;
import nd.rw.cassette.app.listeners.RecordingItemHandler;
import nd.rw.cassette.app.model.CassetteModel;
import nd.rw.cassette.app.model.RecordingModel;
import nd.rw.cassette.app.model.descriptors.CassetteModelDescriptor;
import nd.rw.cassette.app.model.descriptors.DurationFormatter;
import nd.rw.cassette.app.presenter.ViewCassettePresenter;
import nd.rw.cassette.app.utils.AndroidFileUtils;
import nd.rw.cassette.app.utils.CassetteAndRecordingDeleter;
import nd.rw.cassette.app.utils.RecordingPlayer;
import nd.rw.cassette.app.view.DetailCassetteView;
import nd.rw.cassette.app.listeners.RecordingRecyclerItemClickListener;
import nd.rw.cassette.app.view.adapter.layoutmanagers.RecordingLayoutManager;
import nd.rw.cassette.app.view.adapter.RecordingSwipeAdapter;
import nd.rw.cassette.app.view.decoration.DividerItemDecoration;
import nd.rw.cassette.app.view.fragment.DeleteCassetteDialogFragment;

public class DetailCassetteActivity
        extends BaseActivity
        implements DetailCassetteView, DeleteCassetteDialogFragment.DeleteCassetteNoticeListener,
        RecordingItemHandler,
        SeekBar.OnSeekBarChangeListener,
        MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {

    //region Fields

    private static final String TAG = "DetailCassetteActivity";

    public static final String INTENT_EXTRA_PARAM_CASSETTE_ID = "andrewtorski.cassette.INTENT_PARAM_CASSETTE_ID";
    public static final String INSTANCE_STATE_PARAM_CASSETTE_ID = "andrewtorski.cassette.STATE_PARAM_CASSETTE_ID";

    public static final int DETAILS_TO_LIST_RESULT_CODE = 1;

    @Bind(R.id.toolbar)
    public Toolbar toolbar;
    @Bind(R.id.card_view_and_details_cassette_title)
    public EditText et_title;
    @Bind(R.id.cassette_details_description)
    public EditText et_description;
    @Bind(R.id.cassette_details_no_of_recordings)
    public TextView tv_numberOfRecordings;
    @Bind(R.id.cassette_details_creation_date)
    public TextView tv_creationDate;
    @Bind(R.id.rv_recordings)
    public RecyclerView rv_recordings;
    @Bind(R.id.coordinator_layout)
    public CoordinatorLayout cl_layout;

    private SeekBar sb_playback;
    private TextView tv_currentPlaybackProgress;
    private TextView tv_durationLeft;
    private ImageButton ib_playPause;

    private RecordingSwipeAdapter recordingSwipeAdapter;
    public RecordingPlayer recordingPlayer = new RecordingPlayer();
    private Handler playbackHandler = new Handler();

    private int cassetteId;
    private ViewCassettePresenter detailPresenter;
    private CassetteAndRecordingDeleter cassetteAndRecordingDeleter;
    private boolean wasCassetteUpdated = false;

    //endregion Fields

    //region AppCompatActivity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);
        setUpWindowParams();
        setContentView(R.layout.activity_detail_cassette);
        ButterKnife.bind(this);
        setUpActionBar();
        setUpListeners();
        setUpRecyclerView();
        initializeActivity(savedInstanceState);
        AndroidFileUtils androidFileUtils = new AndroidFileUtils(this);
        cassetteAndRecordingDeleter = new CassetteAndRecordingDeleter(androidFileUtils);
        recordingPlayer.setOnCompletionListener(this);
    }

    private void setUpListeners() {
        this.toolbar.setNavigationOnClickListener(homeButtonClickListener);
        this.et_title.setOnFocusChangeListener(titleFocusListener);
        this.et_description.setOnFocusChangeListener(descriptionFocusListener);
        this.et_title.setOnEditorActionListener(editTextOnEditorListener);
        this.et_description.setOnEditorActionListener(editTextOnEditorListener);
    }

    private void setUpActionBar() {
        this.setSupportActionBar(this.toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.toolbar.setTitle("New Cassette");
    }

    private void setUpWindowParams() {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        this.getWindow().setEnterTransition(TransitionInflater.from(this).inflateTransition(android.R.transition.slide_right));
        this.getWindow().setExitTransition(TransitionInflater.from(this).inflateTransition(android.R.transition.slide_left));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.menu_detail_cassette, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_delete_cassette) {
            showDeleteDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (outState != null) {
            outState.putInt(INSTANCE_STATE_PARAM_CASSETTE_ID, cassetteId);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onPause() {
        super.onPause();
        recordingPlayer.dispose();
    }

    //endregion AppCompatActivity

    //region DetailCassetteView Methods

    @Override
    public void renderCassetteAndRecordings(CassetteModel cassetteModel) {
        Log.i(TAG, "renderCassetteAndRecordings");
        if (cassetteModel == null) {
            return;
        }
        CassetteModelDescriptor descriptor = cassetteModel.getDescriptor();
        this.et_title.setText(descriptor.title);
        this.et_description.setText(descriptor.description);
        this.tv_creationDate.setText(descriptor.dateCreated);
        this.tv_numberOfRecordings.setText(descriptor.numberOfRecordings);
        this.recordingSwipeAdapter.setRecordingList(cassetteModel.getRecordingList());
    }

    @Override
    public void refreshTitleAndDescription(CassetteModel cassetteModel) {
        this.et_title.setText(cassetteModel.getTitle());
        this.et_description.setText(cassetteModel.getDescription());
    }

    @Override
    public void addUndidDeleteRecording(RecordingModel recordingModel) {
        recordingSwipeAdapter.addRecording(recordingModel);
    }

    //endregion DetailCassetteView Methods

    //region OnRecording{...} handlers

    @Override
    public void recordingItemClicked(RecordingModel recording, RecordingSwipeAdapter.RecordingSwipeViewHolder viewHolder) {
        if (!recordingPlayer.isPlaying()) {
            sb_playback = viewHolder.sb_progress;
            sb_playback.setOnSeekBarChangeListener(this);
            tv_currentPlaybackProgress = viewHolder.tv_currentProgress;
            tv_durationLeft = viewHolder.tv_duration;
            ib_playPause = viewHolder.ib_play_stop_button;
            try {
                recordingPlayer.preparePlayback(recording);
            } catch (IOException e) {
                this.showError("Something went wrong while playing the recording.");
            }
            runOnUiThread(seekBarMediaPlayerUpdater);
            sb_playback.setMax(recordingPlayer.getDuration());
        } else {
            this.ib_playPause.setBackgroundResource(R.drawable.ic_play_arrow_black_24dp);
            this.recordingPlayer.pausePlayback();
        }
    }

    @Override
    public void playClicked(RecordingModel recordingModel) {
        if (!recordingPlayer.isPlaying()) {
            this.ib_playPause.setBackgroundResource(R.drawable.ic_pause_black_24dp);
            this.recordingPlayer.startPlayback();
            runOnUiThread(seekBarMediaPlayerUpdater);
        } else {
            this.ib_playPause.setBackgroundResource(R.drawable.ic_play_arrow_black_24dp);
            this.recordingPlayer.pausePlayback();
        }
    }

    @Override
    public void deleteClicked(RecordingModel recording) {
        recordingSwipeAdapter.deleteRecording(recording);
        detailPresenter.setUpRecordingToBeDeleted(recording);
        Snackbar.make(cl_layout, "Recording deleted", Snackbar.LENGTH_LONG)
                .setAction("Undo", v -> detailPresenter.undoDeleteRecording())
                .setCallback(undoDeleteRecordingCallback)
                .show();
    }

    //endregion OnRecording{...} handlers

    //region SeekBar related methods

    private Runnable seekBarMediaPlayerUpdater = new Runnable() {
        @Override
        public void run() {
            if (recordingPlayer.isPlaying()) {
                int mCurrentPosition = recordingPlayer.getCurrentPosition();
                sb_playback.setProgress(mCurrentPosition);
                //  set playback progress
                String currentPositionString = DurationFormatter.formatTimeInMilliseconds(mCurrentPosition);
                tv_currentPlaybackProgress.setText(currentPositionString);
                //  set duration left
                int durationLeft = recordingPlayer.getDuration() - mCurrentPosition;
                String durationLeftString = "-" + DurationFormatter.formatTimeInMilliseconds(durationLeft);
                tv_durationLeft.setText(durationLeftString);
            }
            playbackHandler.postDelayed(this, 100);
        }
    };

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        Log.d(TAG, "onProgressChanged() called with: progress = [" + progress + "], fromUser = [" + fromUser + "]");
        if (fromUser) {
            recordingPlayer.seekTo(progress);
            if (!recordingPlayer.isPlaying()) {
                //  set playback progress
                String currentPositionString = DurationFormatter.formatTimeInMilliseconds(progress);
                tv_currentPlaybackProgress.setText(currentPositionString);
                //  set duration left
                int durationLeft = recordingPlayer.getDuration() - progress;
                String durationLeftString = "-" + DurationFormatter.formatTimeInMilliseconds(durationLeft);
                tv_durationLeft.setText(durationLeftString);
            }
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onPrepared(MediaPlayer mp) {

    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        Log.d(TAG, "onCompletion: ");
        ib_playPause.setBackgroundResource(R.drawable.ic_play_arrow_black_24dp);
        mp.start();
        mp.pause();
        playbackHandler.removeCallbacks(seekBarMediaPlayerUpdater);
    }

    //endregion SeekBar related methods

    //region LoadDataView Methods

    /**
     * Show a view with a progress bar indicating a loading process.
     */
    @Override
    public void showLoading() {

    }

    /**
     * Hide a loading view.
     */
    @Override
    public void hideLoading() {

    }

    /**
     * Show a retry view in case of an error when retrieving data.
     */
    @Override
    public void showRetry() {

    }

    /**
     * Hide a retry view shown if there was an error when retrieving data.
     */
    @Override
    public void hideRetry() {

    }

    /**
     * Show an error message
     *
     * @param message A string representing an error.
     */
    @Override
    public void showError(String message) {
        Snackbar.make(cl_layout, message, Snackbar.LENGTH_LONG).show();
    }

    /**
     * Get a {@link Context}.
     */
    @Override
    public Context getContext() {
        return this;
    }

    //endregion LoadDataView Methods

    //region Private Methods

    private void setUpRecyclerView() {
        Log.i(TAG, "setUpRecyclerView beginning");
        RecordingLayoutManager recordingLayoutManager = new RecordingLayoutManager(this);
        this.rv_recordings.setLayoutManager(recordingLayoutManager);
        this.recordingSwipeAdapter = new RecordingSwipeAdapter(new ArrayList<RecordingModel>(), this);
        this.rv_recordings.setAdapter(recordingSwipeAdapter);
        this.rv_recordings.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        this.rv_recordings.addOnItemTouchListener(new RecordingRecyclerItemClickListener(this));
        Log.i(TAG, "setUpRecyclerView ending");
    }

    private void initializeActivity(Bundle savedInstanceState) {
        Log.i(TAG, "initializeActivity");
        if (savedInstanceState == null) {
            this.cassetteId = this.getIntent().getIntExtra(INTENT_EXTRA_PARAM_CASSETTE_ID, -1);
        } else {
            this.cassetteId = savedInstanceState.getInt(INSTANCE_STATE_PARAM_CASSETTE_ID);
        }
        this.detailPresenter = new ViewCassettePresenter(this);
        this.detailPresenter.initialize(cassetteId);
    }

    private boolean isEmpty(EditText editTextToCheck) {
        return editTextToCheck.getText().length() == 0;
    }

    private boolean isTitleEmpty() {
        return isEmpty(et_title);
    }

    private void updateCassette() {
        String title = et_title.getText().toString(),
                description = et_description.getText().toString();
        wasCassetteUpdated = detailPresenter.updateCassette(title, description);
        Log.d(TAG, "updateCassette() wasCassetteUpdate = [" + wasCassetteUpdated + "]");
    }

    //endregion Private Methods

    //region Listeners and Events

    private Snackbar.Callback undoDeleteRecordingCallback = new Snackbar.Callback() {
        @Override
        public void onDismissed(Snackbar snackbar, int event) {
            if (event == DISMISS_EVENT_SWIPE
                    || event == DISMISS_EVENT_TIMEOUT
                    || event == DISMISS_EVENT_CONSECUTIVE) {
                RecordingModel deletedRecording = detailPresenter.actuallyDeleteRecording();
                if (deletedRecording != null) {
                    cassetteAndRecordingDeleter.deleteRecordingContent(deletedRecording);
//                    recordingSwipeAdapter.deleteRecording(deletedRecording);
                }
            }
        }
    };

    private void showDeleteDialog() {
        DialogFragment dialog = new DeleteCassetteDialogFragment();
        dialog.show(this.getSupportFragmentManager(), "DeleteCassetteDialogFragment");
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        Log.d(TAG, "onDialogPositiveClick: POSITIVE");
        /*boolean deleteWasSuccessful = this.detailPresenter.deleteCassette();
        if (deleteWasSuccessful) {
        }*/
        Intent data = new Intent();
        data.putExtra(INTENT_EXTRA_PARAM_CASSETTE_ID, cassetteId);
        setResult(DETAILS_TO_LIST_RESULT_CODE, data);
        this.finish();
    }

    public View.OnClickListener homeButtonClickListener = view -> finish();

    private View.OnFocusChangeListener titleFocusListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (!hasFocus) {
                if (isTitleEmpty()) {
                    detailPresenter.refreshTitleAndDescriptionInView();
                } else {
                    updateCassette();
                }
            }
        }
    };

    private View.OnFocusChangeListener descriptionFocusListener = (v, hasFocus) -> {
        if (!hasFocus) {
            updateCassette();
        }
    };

    private EditText.OnEditorActionListener editTextOnEditorListener = (v, actionId, event) -> {
        boolean handled = false;
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            v.clearFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            handled = true;
        }
        return handled;
    };

    //endregion Listeners and Events

    //region Static Methods

    public static Intent getCallingIntent(Context context, int cassetteId) {
        Intent intent = new Intent(context, DetailCassetteActivity.class);
        intent.putExtra(INTENT_EXTRA_PARAM_CASSETTE_ID, cassetteId);
        return intent;
    }

    //endregion Static Methods
}
