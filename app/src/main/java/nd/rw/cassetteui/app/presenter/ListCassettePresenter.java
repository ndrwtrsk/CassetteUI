package nd.rw.cassetteui.app.presenter;


import java.util.List;

import nd.rw.cassetteui.app.model.CassetteModel;
import nd.rw.cassetteui.app.view.ListCassettesView;
import nd.rw.cassetteui.domain.usecase.ListCassettesUseCase;

public class ListCassettePresenter implements Presenter{

    //region Field

    private ListCassettesUseCase useCase = new ListCassettesUseCase();

    private ListCassettesView view;

    //endregion Field

    //region Methods

    public void setView(ListCassettesView view) {
        if (view == null) {
            throw new RuntimeException("View shouldn't be null.");
        }
        this.view = view;
    }

    public void initialize(){
        List<CassetteModel> list = this.useCase.getCassettes();
        view.renderCassetteList(list);
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
