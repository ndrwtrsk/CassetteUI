package nd.rw.cassetteui.domain.usecase;

import android.util.Log;

import nd.rw.cassetteui.app.model.CassetteModel;

public class AddCassetteUseCase extends AbstractUseCase{

    //region Fields

    private static final String TAG = "AddCasUseCase";

    //endregion Fields

    //region Methods

    public CassetteModel addCassetteModel(CassetteModel cassetteModel){
        CassetteModel result = cassetteRepository.create(cassetteModel);
        return result;
    }

    public CassetteModel addCassetteModel(String title, String description){
        if (title == null) {
            return null;
        }
        description = description != null ? description : "";
        CassetteModel newCassetteModel;

        newCassetteModel = new CassetteModel(title, description);

        Log.d(TAG, "addCassetteModel() called with: " + "title = [" + title + "], description = [" + description + "]");
        Log.d(TAG, "addCassetteModel() returned: " + newCassetteModel);
        return addCassetteModel(newCassetteModel);
    }

    //endregion Methods

}
