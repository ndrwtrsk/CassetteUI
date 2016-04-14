package nd.rw.cassette.domain.usecase;

import java.util.List;

import nd.rw.cassette.app.model.CassetteModel;
import nd.rw.cassette.data.repository.CassetteDataRepository;
import nd.rw.cassette.data.repository.datasource.DataSourceFactory;
import nd.rw.cassette.domain.repository.CassetteRepository;

public class ListCassettesUseCase extends AbstractUseCase {

    private CassetteRepository repository = new CassetteDataRepository(DataSourceFactory.getRealmCassetteDataStore());

    public List<CassetteModel> getCassettes(){
        return repository.getAll();
    }
    public CassetteModel getCassetteById(int id){
        return repository.get(id);
    }

    public boolean deleteCassette(CassetteModel cassetteToBeDeleted) {
        return repository.delete(cassetteToBeDeleted);
    }
}
