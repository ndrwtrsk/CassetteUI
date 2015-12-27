package nd.rw.cassetteui.app.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import nd.rw.cassetteui.R;
import nd.rw.cassetteui.app.listeners.OnRecordingClickedHandler;
import nd.rw.cassetteui.app.model.CassetteModel;
import nd.rw.cassetteui.app.model.RecordingModel;
import nd.rw.cassetteui.app.model.descriptors.CassetteModelDescriptor;
import nd.rw.cassetteui.app.presenter.DeleteCassettePresenter;
import nd.rw.cassetteui.app.presenter.DetailUpdateCassettePresenter;
import nd.rw.cassetteui.app.utils.RecordingPlayer;
import nd.rw.cassetteui.app.view.DetailCassetteView;
import nd.rw.cassetteui.app.view.adapter.RecordingLayoutManager;
import nd.rw.cassetteui.app.view.adapter.RecordingSwipeAdapter;
import nd.rw.cassetteui.app.view.decoration.DividerItemDecoration;
import nd.rw.cassetteui.app.view.fragment.DeleteCassetteDialogFragment;

public class DetailCassetteActivity
        extends BaseActivity
        implements DetailCassetteView, DeleteCassetteDialogFragment.DeleteCassetteNoticeListener,
        OnRecordingClickedHandler{

    //region Fields

    private static final String TAG = "DetailCassetteActivity";

    public static final String INTENT_EXTRA_PARAM_CASSETTE_ID = "andrewtorski.cassette.INTENT_PARAM_CASSETTE_ID";
    public static final String INSTANCE_STATE_PARAM_CASSETTE_ID = "andrewtorski.cassette.STATE_PARAM_CASSETTE_ID";

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

    private RecordingSwipeAdapter recordingSwipeAdapter;
    private RecordingPlayer recordingPlayer = new RecordingPlayer();

    private int cassetteId;
    private DetailUpdateCassettePresenter detailPresenter;
    private boolean wasCassetteUpdated = false;

    //endregion Fields

    //region AppCompatActivity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        this.getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        this.setContentView(R.layout.activity_detail_cassette);
        ButterKnife.bind(this);
        this.setSupportActionBar(this.toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.toolbar.setTitle("New Cassette");
        this.toolbar.setNavigationOnClickListener(homeButtonClickListener);
        this.et_title.setOnFocusChangeListener(titleFocusListener);
        this.et_description.setOnFocusChangeListener(descriptionFocusListener);
        this.et_title.setOnEditorActionListener(editTextOnEditorListener);
        this.et_description.setOnEditorActionListener(editTextOnEditorListener);
        this.setUpRecyclerView();
        this.initializeActivity(savedInstanceState);
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
            this.showDeleteDialog();
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
        this.recordingPlayer.dispose();
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

    //endregion DetailCassetteView Methods

    //region OnRecordingClickedHandler Method

    @Override
    public void onRecordingClickedHandler(RecordingModel recording) {
        try {
            this.recordingPlayer.playRecording(recording);
        } catch (IOException e) {
            this.showError("Something went wrong while playing the recording.");
        }
    }


    //endregion OnRecordingClickedHandler Method

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

    private void setUpRecyclerView(){
        Log.i(TAG, "setUpRecyclerView beginning");
        RecordingLayoutManager recordingLayoutManager = new RecordingLayoutManager(this);
        this.rv_recordings.setLayoutManager(recordingLayoutManager);
        this.recordingSwipeAdapter = new RecordingSwipeAdapter(new ArrayList<RecordingModel>(), this);
        this.rv_recordings.setAdapter(recordingSwipeAdapter);
        this.rv_recordings.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        Log.i(TAG, "setUpRecyclerView ending");
    }

    private void initializeActivity(Bundle savedInstanceState){
        Log.i(TAG, "initializeActivity");
        if (savedInstanceState == null) {
            this.cassetteId = this.getIntent().getIntExtra(INTENT_EXTRA_PARAM_CASSETTE_ID, -1);
        } else {
            this.cassetteId = savedInstanceState.getInt(INSTANCE_STATE_PARAM_CASSETTE_ID);
        }
        this.detailPresenter = new DetailUpdateCassettePresenter(this);
        this.detailPresenter.initialize(cassetteId);
    }

    private boolean isEmpty(EditText editTextToCheck){
        return editTextToCheck.getText().length() == 0;
    }

    private boolean isTitleEmpty(){
        return isEmpty(et_title);
    }

    private void updateCassette(){
        String  title = et_title.getText().toString(),
                description = et_description.getText().toString();
        wasCassetteUpdated = detailPresenter.updateCassette(title, description);
        Log.d(TAG, "updateCassette() wasCassetteUpdate = [" + wasCassetteUpdated +"]");
    }

    //endregion Private Methods

    //region Listeners and Events

    private void showDeleteDialog(){
        DialogFragment dialog = new DeleteCassetteDialogFragment();
        dialog.show(this.getSupportFragmentManager(), "DeleteCassetteDialogFragment");
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        Log.d(TAG, "onDialogPositiveClick: POSITIVE");
        boolean deleteWasSuccessful = this.detailPresenter.deleteCassette();
        if (deleteWasSuccessful) {
            // TODO: 25.12.2015 notify about delete not being successful?
        }
        this.finish();
    }

    public View.OnClickListener homeButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };

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

    private View.OnFocusChangeListener descriptionFocusListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (!hasFocus) {
                updateCassette();
            }
        }
    };

    private EditText.OnEditorActionListener editTextOnEditorListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            boolean handled = false;
            if(actionId == EditorInfo.IME_ACTION_DONE){
                Log.d(TAG, "onEditorAction: ime_action_done");
                v.clearFocus();
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                handled = true;
            }
            return handled;
        }
    };

    //endregion Listeners and Events

    //region Static Methods

    public static Intent getCallingIntent(Context context, int cassetteId){
        Intent intent = new Intent(context, DetailCassetteActivity.class);
        intent.putExtra(INTENT_EXTRA_PARAM_CASSETTE_ID, cassetteId);
        return intent;
    }

    //endregion Static Methods
}
