package nd.rw.cassetteui.domain.usecase;

import nd.rw.cassetteui.app.model.CassetteModel;

public class DetailCassetteUseCase extends AbstractUseCase{

    public CassetteModel getCassetteById(int id){
        for (CassetteModel cassetteModel :
                CassetteModelList) {
            if (id == cassetteModel.getId()){
                return cassetteModel;
            }
        }

        return null;
    }

}
