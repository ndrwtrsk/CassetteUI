package nd.rw.cassetteui.app.presenter;

import android.util.Log;

import nd.rw.cassetteui.app.model.CassetteModel;
import nd.rw.cassetteui.domain.usecase.DeleteCassetteUseCase;

public class DeleteCassettePresenter {

    //region Fields

    public static final String TAG = "DeleteCassettePresenter";
    private DeleteCassetteUseCase useCase = new DeleteCassetteUseCase();

    //endregion Fields

    public boolean deleteCassette(int id){
        Log.d(TAG, "deleteCassette: Deleting cassette of id = " + id);
        boolean wasDeleteSuccessful = useCase.deleteCassetteModel(id);
        Log.d(TAG, "deleteCassette() returned: " + wasDeleteSuccessful);
        return wasDeleteSuccessful;
    }

    public boolean deleteCassette(CassetteModel cassetteModel) {
        return this.deleteCassette(cassetteModel.getId());
    }
}
