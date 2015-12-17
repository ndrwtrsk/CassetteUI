package nd.rw.cassetteui.domain.usecase;

import android.util.Log;

import java.util.GregorianCalendar;

import nd.rw.cassetteui.app.model.CassetteModel;
import nd.rw.cassetteui.data.repository.CassetteDataRepository;
import nd.rw.cassetteui.data.repository.datasource.DataSourceFactory;
import nd.rw.cassetteui.domain.repository.CassetteRepository;

public class AddCassetteUseCase extends AbstractUseCase{
    //region Fields
    private static final String TAG = "AD_CAS_UC";

    private CassetteRepository repository = new CassetteDataRepository(DataSourceFactory.getRealmCassetteDataStore());

    //endregion Fields

    //region Methods
    public CassetteModel addCassetteModel(CassetteModel cassetteModel){
        CassetteModel result = repository.create(cassetteModel);
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
