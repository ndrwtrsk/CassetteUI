package nd.rw.cassetteui.data.repository;

import java.util.List;

import nd.rw.cassetteui.app.model.CassetteModel;
import nd.rw.cassetteui.data.entity.CassetteEntity;
import nd.rw.cassetteui.data.entity.mapper.CassetteMapper;
import nd.rw.cassetteui.data.repository.datasource.CassetteDataStore;
import nd.rw.cassetteui.domain.repository.CassetteRepository;

public class CassetteDataRepository implements CassetteRepository{

    private CassetteDataStore dataStore;

    public CassetteDataRepository(CassetteDataStore dataStore) {
        this.dataStore = dataStore;
    }

    //region CassetteRepository Methods
    @Override
    public List<CassetteModel> getAll() {
        return CassetteMapper.map(dataStore.getAll());
    }

    @Override
    public CassetteModel get(int id) {
        return CassetteMapper.map(dataStore.get(id));
    }

    @Override
    public CassetteModel create(CassetteModel cassetteModel) {
        if (cassetteModel == null) {
            return null;
        }
        CassetteEntity entity = new CassetteEntity();
        entity.setTitle(cassetteModel.getTitle());
        entity.setDescription(cassetteModel.getDescription());
        entity.setDate(cassetteModel.getDate().getTime());

        return CassetteMapper.map(dataStore.create(entity));
    }

    @Override
    public boolean update(CassetteModel cassetteModel) {
        if (cassetteModel == null) {
            return false;
        }
        return dataStore.update(cassetteModel.getId(), cassetteModel.getTitle(), cassetteModel.getDescription());
    }

    @Override
    public boolean delete(CassetteModel cassetteModel) {
        if (cassetteModel == null) {
            return false;
        }
        return dataStore.delete(cassetteModel.getId());
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
