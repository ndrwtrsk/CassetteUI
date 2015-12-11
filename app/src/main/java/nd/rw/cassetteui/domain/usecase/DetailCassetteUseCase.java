package nd.rw.cassetteui.domain.usecase;

import java.util.List;

import nd.rw.cassetteui.app.model.CassetteModel;

public class DetailCassetteUseCase extends AbstractUseCase{

    private List<CassetteModel> cassetteModelList = DataHolderSingleton.getCassetteModelList();

    public CassetteModel getCassetteById(int id){
        for (CassetteModel cassetteModel :
                cassetteModelList) {
            if (id == cassetteModel.getId()){
                return cassetteModel;
            }
        }

        return null;
    }

}
