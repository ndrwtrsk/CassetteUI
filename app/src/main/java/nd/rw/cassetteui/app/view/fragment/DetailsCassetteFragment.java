package nd.rw.cassetteui.app.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import nd.rw.cassetteui.R;
import nd.rw.cassetteui.app.model.CassetteModel;
import nd.rw.cassetteui.app.presenter.DetailCassettePresenter;
import nd.rw.cassetteui.app.view.DetailCassetteView;

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

    private DetailCassettePresenter presenter;

    //endregion Fields

    //region Private Methods

    private void setupUI(){
    }

    //endregion Private Methods

    //region Fragment Methods

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_cassette, container, false);
        ButterKnife.bind(this, view);
        this.getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
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
        String id = Integer.toString(cassetteModel.getId());
        String recordings = cassetteModel.getNumberOfRecordings() + " recordings in total";
//        this.tv_id.setText(id);
        this.et_title.setText(cassetteModel.getTitle());
        this.et_description.setText(cassetteModel.getDescription());
        this.tv_creationDate.setText(cassetteModel.getDate().toString());
        this.tv_numberOfRecordings.setText(recordings);
    }

    //endregion DetailCassetteView methods

    //region Private Methods

    private void initialize(){
        Bundle args = this.getArguments();
        int cassetteId = args.getInt(ARGUMENT_KEY_CASSETTE_ID);
        Log.d(TAG, "initialize: cassetteId: " + cassetteId);
        this.presenter = new DetailCassettePresenter(this);
        this.presenter.initialize(cassetteId);
    }

    //endregion Private Methods

    //region Static Methods

    public static DetailsCassetteFragment newInstance(int cassetteId) {
        Bundle args = new Bundle();
        args.putInt(ARGUMENT_KEY_CASSETTE_ID, cassetteId);
        Log.d(TAG, "newInstance: cassetteid " + cassetteId);
        DetailsCassetteFragment fragment = new DetailsCassetteFragment();
        fragment.setArguments(args);
        return fragment;
    }

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

    //endregion Static Methods

}
