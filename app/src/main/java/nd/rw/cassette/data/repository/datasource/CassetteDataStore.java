package nd.rw.cassette.data.repository.datasource;

import java.util.List;

import nd.rw.cassette.data.entity.CassetteEntity;

public interface CassetteDataStore {

    List<CassetteEntity> getAll();

    CassetteEntity get(final int id);

    CassetteEntity create(CassetteEntity cassetteEntity);

    boolean update(CassetteEntity cassetteEntity);

    boolean update(final int id, final String title, final String description);

    boolean delete(CassetteEntity cassetteEntity);

    boolean delete(final int id);

    long count();

    int getNewPkValue();
    
}
