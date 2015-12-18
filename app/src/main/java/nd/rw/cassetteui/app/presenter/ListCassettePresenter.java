package nd.rw.cassetteui.app.presenter;


import java.util.List;

import nd.rw.cassetteui.app.model.CassetteModel;
import nd.rw.cassetteui.app.view.DetailCassetteView;
import nd.rw.cassetteui.app.view.ListCassettesView;
import nd.rw.cassetteui.domain.usecase.DetailCassetteUseCase;
import nd.rw.cassetteui.domain.usecase.ListCassettesUseCase;

public class ListCassettePresenter implements Presenter{
    private static final String TAG = "LI_CAS_PRE";

    //region Field

    private ListCassettesUseCase useCase = new ListCassettesUseCase();
    private DetailCassetteUseCase detailsUseCase = new DetailCassetteUseCase();
    private ListCassettesView view;

    //endregion Field

    public ListCassettePresenter(ListCassettesView view) {
        this.view = view;
    }

    public ListCassettePresenter() {
    }

    //region Methods

    public void setView(ListCassettesView view) {
        if (view == null) {
            throw new RuntimeException("View shouldn't be null.");
        }
        this.view = view;
    }

    public void initialize(){
        List<CassetteModel> list = this.useCase.getCassettes();
        this.view.renderCassetteList(list);
    }

    public void onCassetteClicked(CassetteModel cassetteModel){
        this.view.viewCassette(cassetteModel);
    }

    public void queryForNewlyAddedCassette(int cassetteId){
        this.view.onAddedCassette(this.detailsUseCase.getCassetteById(cassetteId));
    }

    public void queryForNewlyUpdatedCassette(int cassetteId){
        this.view.onUpdatedCassette(this.detailsUseCase.getCassetteById(cassetteId));
    }

    //endregion Methods

    //region Presenter implemented methods

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {

    }

    //endregion Presenter implemented methods

}
