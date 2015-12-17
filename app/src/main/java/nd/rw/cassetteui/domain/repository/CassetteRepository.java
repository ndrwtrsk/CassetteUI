package nd.rw.cassetteui.domain.repository;

import java.util.List;

import nd.rw.cassetteui.app.model.CassetteModel;

/**
 * Defines contract for all implementing classes to realize in order to become a repository
 * for Cassettes.
 * Serves as an interface for communicating with Data Access Layer.
 */
public interface CassetteRepository {

    List<CassetteModel> getAll();

    CassetteModel get(final int id);

    CassetteModel create(CassetteModel cassetteModel);

    boolean update(CassetteModel cassetteModel);

    boolean delete(CassetteModel cassetteModel);

    boolean delete(final int id);

    long count();

}
