package nd.rw.cassetteui.app.view.fragment;

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
import nd.rw.cassetteui.R;
import nd.rw.cassetteui.app.listeners.OnCassetteClickedHandler;
import nd.rw.cassetteui.app.model.CassetteModel;
import nd.rw.cassetteui.app.presenter.ListCassettePresenter;
import nd.rw.cassetteui.app.view.ListCassettesView;
import nd.rw.cassetteui.app.view.activity.AddCassetteActivity;
import nd.rw.cassetteui.app.view.activity.DetailCassetteActivity;
import nd.rw.cassetteui.app.view.adapter.layoutmanagers.CassetteLayoutManager;
import nd.rw.cassetteui.app.view.adapter.CassettesAdapter;
import nd.rw.cassetteui.app.view.decoration.DividerItemDecoration;

public class ListCassetteFragment
        extends BaseFragment
        implements ListCassettesView,
        OnCassetteClickedHandler {

    //region Fields

    public static final String TAG = "LI_CAS_FRAG";
    public static final int LIST_TO_DETAILS_REQUEST_CODE = 1;

    @Bind(R.id.rv_cassettes)
    public RecyclerView rv_cassettes;
    @Bind(R.id.layout_cassettes)
    public CoordinatorLayout cl_layout;
    @Bind(R.id.fab_addCassette)
    public FloatingActionButton fab_addCassette;

    public ListCassettePresenter presenter;

    private CassettesAdapter cassettesAdapter;

    private CassetteLayoutManager layoutManager;

    private OnCassetteClickedHandler onCassetteClickedHandler;


    //endregion Fields

    //region Constructor

    public ListCassetteFragment() {

    }

    //endregion Constructor

    //region Private Methods

    private void setupUI() {
        this.layoutManager = new CassetteLayoutManager(this.getContext());
        this.rv_cassettes.setLayoutManager(layoutManager);
        this.onCassetteClickedHandler = this;
        this.cassettesAdapter = new CassettesAdapter(onCassetteClickedHandler);
        this.rv_cassettes.setAdapter(cassettesAdapter);
        this.fab_addCassette.setOnClickListener(fabAddListener);
        this.rv_cassettes.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));
    }

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
        //  set up delete in presenter
        presenter.setUpCassetteToBeDeleted(cassetteId);
        //  launch snackbar with undo action and set actions for what happens when something happens
        Snackbar.make(cl_layout, "Cassette deleted", Snackbar.LENGTH_LONG)
                .setAction("Undo", v -> presenter.undoDelete())
                .setCallback(undoDeleteCassetteSnackbarCallback)
                .show();
        // piss off
    }

    //endregion Private Methods

    //region ListCassetteView implemented methods

    @Override
    public void renderCassetteList(Collection<CassetteModel> cassetteModelCollection) {
        Log.d(TAG, "renderCassetteList");
        if (cassetteModelCollection == null) {
            throw new RuntimeException("Cassette model collection should not be null.");
        }
        this.cassettesAdapter.initializeAdapterList(cassetteModelCollection);
    }

    @Override
    public void viewCassette(CassetteModel cassetteModel) {
    }

    @Override
    public void setOnCassetteClicked(OnCassetteClickedHandler onCassetteClickedHandler) {
        this.onCassetteClickedHandler = this;
    }

    @Override
    public void onAddedCassette(CassetteModel cassetteModel) {
        Log.d(TAG, "onAddedCassette");
        this.cassettesAdapter.addCassetteToTop(cassetteModel);
    }

    @Override
    public void onUpdatedCassette(CassetteModel cassetteModel) {
        Log.d(TAG, "onUpdatedCassette");
        this.cassettesAdapter.updateCassette(cassetteModel);
    }

    @Override
    public void onDeleteCassette(int cassetteId) {

    }

    @Override
    public void onDeleteCassette(CassetteModel cassette) {
        Log.d(TAG, "onDeleteCassette");
        this.cassettesAdapter.deleteCassette(cassette);
    }

    //endregion ListCassetteView implemented methods

    //region LoadDataView implemented methods

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

    //endregion LoadDataView implemented methods

    //region Fragment overridden methods

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_section_cassettes, container, false);
        ButterKnife.bind(this, view);
        this.setupUI();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.d(TAG, "OnActivityCreated");
        super.onActivityCreated(savedInstanceState);

        //  why exactly is presenter instantiated here?
        //  could it be done elsewhere?
        this.presenter = new ListCassettePresenter(this);
        this.presenter.initialize();
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

    //endregion Fragment overridden methods

    @Override
    public void onCassetteClicked(CassetteModel cassetteModel, View cassetteViewForTransition) {
        Log.i(TAG, "onCassetteClicked");
        Intent intent = DetailCassetteActivity.getCallingIntent(this.getContext(), cassetteModel.getId());
        startActivityForResult(intent, LIST_TO_DETAILS_REQUEST_CODE);
    }

    //region Listeners, Callbacks and Events

    private Snackbar.Callback undoDeleteCassetteSnackbarCallback = new Snackbar.Callback() {
        @Override
        public void onDismissed(Snackbar snackbar, int event) {
            Log.d(TAG, "Snackbar#onDismissed:");
            if (event == DISMISS_EVENT_SWIPE
                    || event == DISMISS_EVENT_TIMEOUT
                    || event == DISMISS_EVENT_CONSECUTIVE) {
                presenter.actuallyDeleteCassette();
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
        Intent intent = AddCassetteActivity.getCallingIntent(ListCassetteFragment.this.getContext());
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
