package nd.rw.cassetteui.app.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import nd.rw.cassetteui.R;
import nd.rw.cassetteui.app.di.components.CassetteComponent;
import nd.rw.cassetteui.app.model.CassetteModel;
import nd.rw.cassetteui.app.presenter.ListCassettePresenter;
import nd.rw.cassetteui.app.view.ListCassettesView;
import nd.rw.cassetteui.app.view.adapter.CassetteLayoutManager;
import nd.rw.cassetteui.app.view.adapter.CassettesAdapter;

public class ListCassetteFragment extends BaseFragment implements ListCassettesView{

    //region Fields

    @Bind(R.id.rv_cassettes)
    private RecyclerView rv_cassettes;

    @Inject
    private ListCassettePresenter presenter;

    private CassettesAdapter cassettesAdapter;

    private CassetteLayoutManager layoutManager;


    //endregion Fields

    //region Constructor

    @Inject
    public ListCassetteFragment(){
    }

    //endregion Constructor

    //region Private Methods

    private void initialize(){
        CassetteComponent component = this.getComponent(CassetteComponent.class);
        if (component != null) {
            component.inject(this);
        }
        this.presenter.setView(this);
    }

    private void setupUI(){
        this.layoutManager = new CassetteLayoutManager(this.getContext());
        this.rv_cassettes.setLayoutManager(layoutManager);
        this.cassettesAdapter = new CassettesAdapter(new ArrayList<CassetteModel>());
    }

    //endregion Private Methods

    //region ListCassetteView implemented methods

    @Override
    public void renderCassetteList(Collection<CassetteModel> cassetteModelCollection) {
        if (cassetteModelCollection == null) {
            throw new RuntimeException("Cassette model collection should not be null.");
        }
        this.cassettesAdapter.setCassetteModelList(cassetteModelCollection);
    }

    @Override
    public void viewCassette(CassetteModel cassetteModel) {

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
        View view = inflater.inflate(R.layout.fragment_section_cassettes, container, false);
        ButterKnife.bind(this, view);
        this.setupUI();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.initialize();
        this.presenter.initialize();
    }


    //endregion Fragment overridden methods

    //region Static Methods

    public static ListCassetteFragment newInstance() {

        Bundle args = new Bundle();

        ListCassetteFragment fragment = new ListCassetteFragment();
        fragment.setArguments(args);
        return fragment;
    }

    //endregion Static Methods
}
