package nd.rw.cassetteui.domain.usecase;

import nd.rw.cassetteui.app.model.CassetteModel;

public class UpdateCassetteUseCase extends AbstractUseCase {

    public CassetteModel updateCassette(CassetteModel cassetteModel){
        for (CassetteModel iteratedCassette :
                CassetteModelList) {
            if (iteratedCassette.getId() == cassetteModel.getId()){
                iteratedCassette.update(cassetteModel);
                return iteratedCassette;
            }
        }

        return null;
    }

}
