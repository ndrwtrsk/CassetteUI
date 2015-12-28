package nd.rw.cassetteui.app.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import nd.rw.cassetteui.R;
import nd.rw.cassetteui.app.model.CassetteModel;
import nd.rw.cassetteui.app.model.RecordingModel;
import nd.rw.cassetteui.app.model.descriptors.CassetteModelDescriptor;
import nd.rw.cassetteui.app.presenter.ViewCassettePresenter;
import nd.rw.cassetteui.app.view.DetailCassetteView;
import nd.rw.cassetteui.app.view.adapter.RecordingLayoutManager;
import nd.rw.cassetteui.app.view.adapter.RecordingListViewAdapter;
import nd.rw.cassetteui.app.view.adapter.RecordingSwipeAdapter;
import nd.rw.cassetteui.app.view.decoration.DividerItemDecoration;

public class DetailsCassetteFragment extends BaseFragment implements DetailCassetteView{

    //region Fields

    private final static String ARGUMENT_KEY_CASSETTE_ID = "nd.rw.cassetteui.ARGUMENT_CASSETTE_ID";
    private static final String TAG = "DET_CAS_FRAG";

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

    private ViewCassettePresenter detailPresenter;

    private RecordingListViewAdapter recordingsAdapter;

    private RecordingSwipeAdapter recordingSwipeAdapter;

    private RecordingLayoutManager recordingLayoutManager;

    //endregion Fields

    //region Fragment Methods

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_detail_cassette, container, false);
        ButterKnife.bind(this, view);
        this.getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        this.et_title.setOnFocusChangeListener(titleFocusListener);
        this.et_description.setOnFocusChangeListener(descriptionFocusListener);
        this.setupUI();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.initialize();
    }

    //endregion Fragment Methods

    //region DetailCassetteView methods

    @Override
    public void renderCassetteAndRecordings(CassetteModel cassetteModel) {
        if (cassetteModel == null) {
            return;
        }
        CassetteModelDescriptor descriptor = cassetteModel.getDescriptor();
        this.et_title.setText(descriptor.title);
        this.et_description.setText(descriptor.description);
        this.tv_creationDate.setText(descriptor.dateCreated);
        this.tv_numberOfRecordings.setText(descriptor.numberOfRecordings);
        this.recordingsAdapter.setRecordingList(cassetteModel.getRecordingList());
        this.recordingSwipeAdapter.setRecordingList(cassetteModel.getRecordingList());
    }

    @Override
    public void refreshTitleAndDescription(CassetteModel cassetteModel) {
        this.et_title.setText(cassetteModel.getTitle());
        this.et_description.setText(cassetteModel.getDescription());
    }

    //endregion DetailCassetteView methods

    //region Private Methods

    private void setupUI(){
        this.recordingLayoutManager = new RecordingLayoutManager(this.getContext());
        this.rv_recordings.setLayoutManager(recordingLayoutManager);
        this.recordingsAdapter = new RecordingListViewAdapter(new ArrayList<RecordingModel>());
//        this.recordingSwipeAdapter = new RecordingSwipeAdapter(new ArrayList<RecordingModel>());
        this.rv_recordings.setAdapter(recordingSwipeAdapter);
        this.rv_recordings.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));
    }

    private void initialize(){
        Bundle args = this.getArguments();
        int cassetteId = args.getInt(ARGUMENT_KEY_CASSETTE_ID);
        Log.d(TAG, "initialize: cassetteId: " + cassetteId);
        this.detailPresenter = new ViewCassettePresenter(this);
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
        detailPresenter.updateCassette(title, description);
    }

    //endregion Private Methods

    //region Listeners and Events

    private View.OnFocusChangeListener titleFocusListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (!hasFocus) {
                if (isTitleEmpty()) {
                    detailPresenter.refreshTitleAndDescriptionInView();
//                    Log.d(TAG, "TITLE onFocusChange() lost focus and title is empty. May not update.");
                } else {
                    updateCassette();
//                    Log.d(TAG, "TITLE onFocusChange() lost focus and title is not empty. May update.");
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

    //endregion Listeners and Events

    //region Static Methods

    public static DetailsCassetteFragment newInstance(int cassetteId) {
        Bundle args = new Bundle();
        args.putInt(ARGUMENT_KEY_CASSETTE_ID, cassetteId);
        Log.d(TAG, "newInstance: cassetteid " + cassetteId);
        DetailsCassetteFragment fragment = new DetailsCassetteFragment();
        fragment.setArguments(args);
        return fragment;
    }

    //endregion Static Methods

    //region LoadDataView Methods

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

    //endregion LoadDataView Methods

}
