package nd.rw.cassetteui.domain.usecase;

import java.util.List;

import nd.rw.cassetteui.app.model.CassetteModel;

public class DeleteCassetteUseCase extends AbstractUseCase{

    private List<CassetteModel> cassetteModelList = DataHolderSingleton.getCassetteModelList();

    public CassetteModel deleteCassetteModel(int id){

        CassetteModel foundCassetteModel;

        for (CassetteModel cassette :
                cassetteModelList) {
            if (cassette.getId() == id){
                cassetteModelList.remove(cassette);
                return cassette;
            }
        }

        return null;
    }

}
