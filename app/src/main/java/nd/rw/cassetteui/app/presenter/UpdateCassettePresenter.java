package nd.rw.cassetteui.app.presenter;

import nd.rw.cassetteui.app.model.CassetteModel;
import nd.rw.cassetteui.domain.usecase.UpdateCassetteUseCase;

public class UpdateCassettePresenter implements Presenter{

    private UpdateCassetteUseCase useCase = new UpdateCassetteUseCase();

    public CassetteModel updateCassette(CassetteModel cassetteModel){
        return this.useCase.updateCassette(cassetteModel);
    }

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
