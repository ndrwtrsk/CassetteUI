package nd.rw.cassette.app.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collection;

import butterknife.Bind;
import butterknife.ButterKnife;
import nd.rw.cassette.R;
import nd.rw.cassette.app.model.CassetteModel;
import nd.rw.cassette.app.presenter.ListCassettePresenter;
import nd.rw.cassette.app.utils.CassetteAndRecordingFileDeleter;
import nd.rw.cassette.app.view.ListCassettesView;
import nd.rw.cassette.app.view.activity.AddCassetteActivity;
import nd.rw.cassette.app.view.activity.DetailCassetteActivity;
import nd.rw.cassette.app.view.adapter.CassettesAdapter;
import nd.rw.cassette.app.view.adapter.layoutmanagers.CassetteLayoutManager;
import nd.rw.cassette.app.view.decoration.DividerItemDecoration;

public class ListCassetteFragment
        extends BaseFragment
        implements ListCassettesView,
        CassettesAdapter.CassetteViewHolder.OnCassetteClickedHandler {

    //region Fields

    public static final String TAG = "ListCassetteFragment";
    public static final int LIST_TO_DETAILS_REQUEST_CODE = 1;

    @Bind(R.id.rv_cassettes)
    public RecyclerView rv_cassettes;
    @Bind(R.id.layout_cassettes)
    public CoordinatorLayout cl_layout;
    @Bind(R.id.fab_addCassette)
    public FloatingActionButton fab_addCassette;

    public ListCassettePresenter presenter;
    private CassetteAndRecordingFileDeleter cassetteAndRecordingDeleter;
    private CassettesAdapter cassettesAdapter;

    private CassettesAdapter.CassetteViewHolder.OnCassetteClickedHandler onCassetteClickedHandler;

    //endregion Fields

    //region Fragment overridden methods

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_section_cassettes, container, false);
        ButterKnife.bind(this, view);
        setupUI();
        return view;
    }

    private void setupUI() {
        CassetteLayoutManager layoutManager = new CassetteLayoutManager(this.getContext());
        rv_cassettes.setLayoutManager(layoutManager);
        onCassetteClickedHandler = this;
        cassettesAdapter = new CassettesAdapter(onCassetteClickedHandler);
        rv_cassettes.setAdapter(cassettesAdapter);
        fab_addCassette.setOnClickListener(fabAddListener);
        rv_cassettes.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.d(TAG, "OnActivityCreated");
        super.onActivityCreated(savedInstanceState);
        cassetteAndRecordingDeleter = new CassetteAndRecordingFileDeleter();
        presenter = new ListCassettePresenter(this);
        presenter.initialize();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LIST_TO_DETAILS_REQUEST_CODE) {
            if (resultCode == DetailCassetteActivity.DETAILS_TO_LIST_RESULT_CODE) {
                onDeletedCassette(data);
            }
        }
    }

    @Override
    public void onDestroy() {
        presenter.destroy();
    }

    //endregion Fragment overridden methods

    //region Private Methods

    private void onDeletedCassette(Intent data) {
        if (data == null) {
            Log.i(TAG, "onDeletedCassette: Received data was null.");
            return;
        }
        int cassetteId = data.getIntExtra(DetailCassetteActivity.INTENT_EXTRA_PARAM_CASSETTE_ID, -1);
        if (cassetteId == -1) {
            Log.i(TAG, "onDeletedCassette: No Cassette Id was passed");
            return;
        }

        presenter.setUpCassetteToBeDeleted(cassetteId);
        Snackbar undoDeleteSnackbar = Snackbar.make(cl_layout, "Cassette deleted", Snackbar.LENGTH_LONG);
        undoDeleteSnackbar.setAction("Undo", v -> presenter.undoDelete())
                .setCallback(undoDeleteCassetteSnackbarCallback)
                .show();
    }

    //endregion Private Methods

    //region ListCassetteView implemented methods

    @Override
    public void renderCassetteList(Collection<CassetteModel> cassetteModelCollection) {
        Log.d(TAG, "renderCassetteList");
        if (cassetteModelCollection == null) {
            throw new RuntimeException("Cassette model collection should not be null.");
        }
        cassettesAdapter.setCassetteSortedList(cassetteModelCollection);
    }

    @Override
    public void onAddedCassette(CassetteModel cassetteModel) {
        Log.d(TAG, "onAddedCassette");
        cassettesAdapter.addCassetteToTop(cassetteModel);
    }

    @Override
    public void onUpdatedCassette(CassetteModel cassetteModel) {
        Log.d(TAG, "onUpdatedCassette");
        cassettesAdapter.updateCassette(cassetteModel);
    }

    @Override
    public void onDeleteCassette(CassetteModel cassette) {
        Log.d(TAG, "onDeleteCassette");
        cassettesAdapter.deleteCassette(cassette);
    }

    //endregion ListCassetteView implemented methods

    //region LoadDataView implemented methods

    @Override
    public void showError(String message) {

    }

    //endregion LoadDataView implemented methods

    @Override
    public void onCassetteClicked(CassetteModel cassetteModel, View cassetteViewForTransition) {
        Log.i(TAG, "onCassetteClicked");
        Intent intent = DetailCassetteActivity.getCallingIntent(getContext(), cassetteModel.id);

//        ActivityOptionsCompat opts = ActivityOptionsCompat.makeCustomAnimation(getContext(),0,0);
//        ActivityCompat.startActivityForResult(getActivity(), intent, LIST_TO_DETAILS_REQUEST_CODE, opts.toBundle());
        startActivityForResult(intent, LIST_TO_DETAILS_REQUEST_CODE);
    }

    //region Listeners, Callbacks and Events

    /**
     * Snackbar for handling undo events. If the snackbar was dismissed by either swiping, expiring
     * after timeout or by being replaced by new snackbar - the cassette should be ACTUALLY deleted.
     */
    private Snackbar.Callback undoDeleteCassetteSnackbarCallback = new Snackbar.Callback() {
        @Override
        public void onDismissed(Snackbar snackbar, int event) {
            Log.d(TAG, "Snackbar#onDismissed:");
            if (event == DISMISS_EVENT_SWIPE
                    || event == DISMISS_EVENT_TIMEOUT
                    || event == DISMISS_EVENT_CONSECUTIVE) {
                CassetteModel deletedCassette = presenter.actuallyDeleteCassette();
                if (deletedCassette != null) {
                    cassetteAndRecordingDeleter.deleteCassetteContents(deletedCassette);
                }
            }
        }

        @Override
        public void onShown(Snackbar snackbar) {
            Log.d(TAG, "Snackbar#onShown");
            //  in case multiple cassettes are deleted at once preemptively delete whatever cassette
            //  presenter is pointing to right now
//            presenter.actuallyDeleteCassette();
        }
    };

    private View.OnClickListener fabAddListener = view -> {
        Intent intent = AddCassetteActivity.getCallingIntent(getContext());
        startActivity(intent);
    };

    //endregion Listeners, Callbacks and Events

    //region Static Methods

    public static ListCassetteFragment newInstance() {
        Bundle args = new Bundle();

        ListCassetteFragment fragment = new ListCassetteFragment();
        fragment.setArguments(args);
        return fragment;
    }

    //endregion Static Methods
}
