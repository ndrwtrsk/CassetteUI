package nd.rw.cassetteui.domain.usecase;

import java.util.List;

import nd.rw.cassetteui.app.model.CassetteModel;
import nd.rw.cassetteui.data.repository.CassetteDataRepository;
import nd.rw.cassetteui.data.repository.datasource.DataSourceFactory;
import nd.rw.cassetteui.domain.repository.CassetteRepository;

public class ListCassettesUseCase extends AbstractUseCase {

    private CassetteRepository repository = new CassetteDataRepository(DataSourceFactory.getRealmCassetteDataStore());

    public List<CassetteModel> getCassettes(){
        return repository.getAll();
    }
    public CassetteModel getCassetteById(int id){
        return repository.get(id);
    }

}
