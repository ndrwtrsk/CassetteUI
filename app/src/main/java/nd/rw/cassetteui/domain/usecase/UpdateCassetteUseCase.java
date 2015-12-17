package nd.rw.cassetteui.domain.usecase;

import nd.rw.cassetteui.app.model.CassetteModel;
import nd.rw.cassetteui.data.repository.CassetteDataRepository;
import nd.rw.cassetteui.data.repository.datasource.DataSourceFactory;
import nd.rw.cassetteui.domain.repository.CassetteRepository;

public class UpdateCassetteUseCase extends AbstractUseCase {

    private CassetteRepository repository = new CassetteDataRepository(DataSourceFactory.getRealmCassetteDataStore());

    public boolean updateCassette(CassetteModel cassetteModel){
        return repository.update(cassetteModel);
    }

}
