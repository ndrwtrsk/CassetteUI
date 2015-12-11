package nd.rw.cassetteui.app.presenter;

import nd.rw.cassetteui.app.model.CassetteModel;
import nd.rw.cassetteui.app.view.DetailCassetteView;
import nd.rw.cassetteui.domain.usecase.DetailCassetteUseCase;

public class DetailCassettePresenter implements Presenter{

    //region Fields

    private DetailCassetteUseCase useCase = new DetailCassetteUseCase();
    private DetailCassetteView view;

    private int cassetteId;
    private CassetteModel cassetteModel;

    //endregion Fields

    //region Constructors

    public DetailCassettePresenter(DetailCassetteView view) {
        this.view = view;
    }

    public DetailCassettePresenter() {
    }

    //endregion Constructors

    //region Methods

    public void initialize(int cassetteId){
        this.cassetteId = cassetteId;
        this.cassetteModel = useCase.getCassetteById(this.cassetteId);
        this.view.renderCassetteAndRecordings(this.cassetteModel);
    }

    public void setView(DetailCassetteView view) {
        if (view == null) {
            throw new RuntimeException("View shouldn't be null!");
        }
        this.view = view;
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
