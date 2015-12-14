package nd.rw.cassetteui.domain.usecase;

import java.util.List;

import nd.rw.cassetteui.app.model.CassetteModel;

/**
 * Class which serves as base class for all classes which perform use cases. All business logic is
 * accessed through this classes.
 */
public abstract class AbstractUseCase {

    protected static List<CassetteModel> CassetteModelList = DataHolderSingleton.getCassetteModelList();

}
