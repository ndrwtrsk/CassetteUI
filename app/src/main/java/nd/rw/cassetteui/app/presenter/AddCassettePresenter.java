package nd.rw.cassetteui.app.presenter;

import nd.rw.cassetteui.app.model.CassetteModel;
import nd.rw.cassetteui.app.presenter.dp.ListCassettePresenterSubject;
import nd.rw.cassetteui.app.view.AddCassetteView;
import nd.rw.cassetteui.domain.usecase.AddCassetteUseCase;

public class AddCassettePresenter implements Presenter{

    private AddCassetteUseCase useCase = new AddCassetteUseCase();

    private AddCassetteView view;

    public AddCassettePresenter(AddCassetteView view) {
        this.view = view;
    }

    //region Methods

    public CassetteModel addCassette(String title, String description){
        CassetteModel newCassette = useCase.addCassetteModel(title, description);
        ListCassettePresenterSubject.getInstance().notifyAboutAddedCassette(newCassette);
        return newCassette;
    }

    //endregion Methods

    //region Presenter Methods

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {

    }

    //endregion Presenter Methods

}
