package nd.rw.cassetteui.domain.usecase;

import android.util.Log;

import java.util.GregorianCalendar;

import nd.rw.cassetteui.app.model.CassetteModel;

public class AddCassetteUseCase extends AbstractUseCase{

    //region Fields
    private static final String TAG = "AD_CAS_UC";

    //endregion Fields

    //region Methods
    public CassetteModel addCasseteModel(CassetteModel cassetteModel){
        CassetteModelList.add(cassetteModel);
        return cassetteModel;
    }

    public CassetteModel addCassetteModel(String title, String description){
        if (title == null) {
            return null;
        }
        description = description != null ? description : "";
        CassetteModel newCassetteModel;

        int newId = CassetteModelList.size()+1;
        GregorianCalendar newDate = new GregorianCalendar();
        newCassetteModel = new CassetteModel(newId, title, description, newDate);

        Log.d(TAG, "addCassetteModel() called with: " + "title = [" + title + "], description = [" + description + "]");
        Log.d(TAG, "addCassetteModel() returned: " + newCassetteModel);
        return addCasseteModel(newCassetteModel);
    }

    //endregion Methods

}
