package nd.rw.cassetteui.app.presenter;

import nd.rw.cassetteui.app.model.CassetteModel;
import nd.rw.cassetteui.app.view.DetailCassetteView;
import nd.rw.cassetteui.domain.usecase.DetailCassetteUseCase;
import nd.rw.cassetteui.domain.usecase.UpdateCassetteUseCase;

public class DetailUpdateCassettePresenter implements Presenter{

    //region Fields

    private DetailCassetteUseCase detailCassetteUseCase = new DetailCassetteUseCase();
    private UpdateCassetteUseCase updateUseCase = new UpdateCassetteUseCase();
    private DetailCassetteView view;

    private int cassetteId;
    private CassetteModel cassetteModel;

    //endregion Fields

    //region Constructors

    public DetailUpdateCassettePresenter(DetailCassetteView view) {
        this.view = view;
    }

    public DetailUpdateCassettePresenter() {
    }

    //endregion Constructors

    //region Methods

    public void initialize(int cassetteId){
        this.cassetteId = cassetteId;
        this.cassetteModel = detailCassetteUseCase.getCassetteById(this.cassetteId);
        this.view.renderCassetteAndRecordings(this.cassetteModel);
    }

    public void setView(DetailCassetteView view) {
        if (view == null) {
            throw new RuntimeException("View shouldn't be null!");
        }
        this.view = view;
    }

    public void updateCassette(String title, String description){
        this.cassetteModel.setTitle(title);
        this.cassetteModel.setDescription(description);
        this.cassetteModel = this.updateUseCase.updateCassette(cassetteModel);
        this.view.refreshTitleAndDescription(this.cassetteModel);
    }

    public void refreshTitleAndDescriptionInView(){
        this.view.refreshTitleAndDescription(this.cassetteModel);
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
