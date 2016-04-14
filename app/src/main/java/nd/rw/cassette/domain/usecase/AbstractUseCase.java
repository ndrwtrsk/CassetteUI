package nd.rw.cassette.domain.usecase;

import java.util.List;

import nd.rw.cassette.app.model.CassetteModel;
import nd.rw.cassette.data.repository.CassetteDataRepository;
import nd.rw.cassette.data.repository.RecordingDataRepository;
import nd.rw.cassette.data.repository.datasource.DataSourceFactory;
import nd.rw.cassette.domain.repository.CassetteRepository;
import nd.rw.cassette.domain.repository.RecordingRepository;

/**
 * Class which serves as base class for all classes which perform use cases. All business logic is
 * accessed through this classes.
 */
public abstract class AbstractUseCase {

    protected CassetteRepository cassetteRepository = new CassetteDataRepository(DataSourceFactory.getRealmCassetteDataStore());
    protected RecordingRepository recordingRepository
            = new RecordingDataRepository(DataSourceFactory.getRealmRecordingDataStore());

    protected static List<CassetteModel> CassetteModelList = DataHolderSingleton.getCassetteModelList();

}
