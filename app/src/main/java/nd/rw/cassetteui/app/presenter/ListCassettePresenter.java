package nd.rw.cassetteui.app.presenter;


import android.util.Log;

import java.util.List;

import nd.rw.cassetteui.app.model.CassetteModel;
import nd.rw.cassetteui.app.presenter.dp.ListCassettePresenterSubject;
import nd.rw.cassetteui.app.view.ListCassettesView;
import nd.rw.cassetteui.domain.usecase.ListCassettesUseCase;

public class ListCassettePresenter implements Presenter{

    //region Field

    private static final String TAG = "ListCasPresenter";
    private ListCassettesUseCase useCase = new ListCassettesUseCase();
    private ListCassettesView view;
    private CassetteModel cassetteToBeDeleted;

    //endregion Field

    //region Constructors

    public ListCassettePresenter(ListCassettesView view) {
        this();
        this.view = view;
    }

    public ListCassettePresenter() {
        ListCassettePresenterSubject.getInstance().attach(this);
    }

    //endregion Constructors

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

    /**
     * Refresh this Presenter's associated View.
     * This method takes part in Observer design part.
     */
    public void onAddCassette(CassetteModel cassette){
        this.view.onAddedCassette(cassette);
    }

    /**
     * Refresh this Presenter's associated View.
     * This method takes part in Observer design part.
     */
    public void onUpdateCassette(CassetteModel cassette){
        CassetteModel updatedCassette = this.useCase.getCassetteById(cassette.getId());
        //  old cassette doesn't have it's recordings updated, hence the call to get the new one
        this.view.onUpdatedCassette(updatedCassette);
    }

    /**
     * Refresh this Presenter's associated View.
     * This method takes part in Observer design part.
     */
    public void onDeleteCassette(CassetteModel cassette){
        Log.d(TAG, "onDeleteCassette() called with: " + "cassette = [" + cassette + "]");
        this.view.onDeleteCassette(cassette);
    }

    public void setUpCassetteToBeDeleted(int cassetteId){
        Log.d(TAG, "setUpCassetteToBeDeleted() called with: " + "cassetteId = [" + cassetteId + "]");
        cassetteToBeDeleted = useCase.getCassetteById(cassetteId);
        ListCassettePresenterSubject.getInstance().notifyAboutDeletedCassette(cassetteToBeDeleted);
        //onDeleteCassette(cassetteToBeDeleted);
    }

    public void actuallyDeleteCassette(){
        Log.d(TAG, "actuallyDeleteCassette:");
        if (cassetteToBeDeleted == null) {
            Log.d(TAG, "actuallyDeleteCassette: Cassette to be deleted is null.");
            return;
        }
        useCase.deleteCassette(cassetteToBeDeleted);
    }

    public void undoDelete(){
        ListCassettePresenterSubject.getInstance().notifyAboutAddedCassette(cassetteToBeDeleted);
        cassetteToBeDeleted = null;
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
        ListCassettePresenterSubject.getInstance().detach(this);
    }

    //endregion Presenter implemented methods

}
