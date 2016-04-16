package nd.rw.cassette.data.repository;

import java.util.List;

import nd.rw.cassette.app.model.CassetteModel;
import nd.rw.cassette.data.entity.CassetteEntity;
import nd.rw.cassette.data.entity.mapper.CassetteMapper;
import nd.rw.cassette.data.repository.datasource.CassetteDataStore;
import nd.rw.cassette.domain.repository.CassetteRepository;

public class CassetteDataRepository implements CassetteRepository{

    private CassetteDataStore dataStore;

    public CassetteDataRepository(CassetteDataStore dataStore) {
        this.dataStore = dataStore;
    }

    //region CassetteRepository Methods

    @Override
    public List<CassetteModel> getAll() {
        return CassetteMapper.mapWithRecordings(dataStore.getAll());
    }

    @Override
    public CassetteModel get(int id) {
        return CassetteMapper.mapWithRecordings(dataStore.get(id));
    }

    @Override
    public CassetteModel create(CassetteModel cassetteModel) {
        if (cassetteModel == null) {
            return null;
        }
        CassetteEntity entity = new CassetteEntity();
        entity.setTitle(cassetteModel.title);
        entity.setDescription(cassetteModel.description);
        entity.setDate(cassetteModel.creationDate.getTime());

        return CassetteMapper.mapWithRecordings(dataStore.create(entity));
    }

    @Override
    public boolean update(CassetteModel cassetteModel) {
        if (cassetteModel == null) {
            return false;
        }
        return dataStore.update(cassetteModel.id, cassetteModel.title, cassetteModel.description);
    }

    @Override
    public boolean delete(CassetteModel cassetteModel) {
        if (cassetteModel == null) {
            return false;
        }
        return dataStore.delete(cassetteModel.id);
    }

    @Override
    public boolean delete(int id) {
        return dataStore.delete(id);
    }

    @Override
    public long count() {
        return dataStore.count();
    }

    //endregion CassetteRepository Methods
}
