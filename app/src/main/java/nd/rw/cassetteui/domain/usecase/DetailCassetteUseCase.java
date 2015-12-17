package nd.rw.cassetteui.domain.usecase;

import nd.rw.cassetteui.app.model.CassetteModel;
import nd.rw.cassetteui.data.repository.CassetteDataRepository;
import nd.rw.cassetteui.data.repository.datasource.DataSourceFactory;
import nd.rw.cassetteui.domain.repository.CassetteRepository;

public class DetailCassetteUseCase extends AbstractUseCase{

    private CassetteRepository repository = new CassetteDataRepository(DataSourceFactory.getRealmCassetteDataStore());

    public CassetteModel getCassetteById(int id){
        return repository.get(id);
    }

}
