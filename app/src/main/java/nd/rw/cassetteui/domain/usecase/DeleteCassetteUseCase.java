package nd.rw.cassetteui.domain.usecase;

import nd.rw.cassetteui.app.model.CassetteModel;

public class DeleteCassetteUseCase extends AbstractUseCase{

    public CassetteModel deleteCassetteModel(int id){

        CassetteModel foundCassetteModel;

        for (CassetteModel cassette :
                CassetteModelList) {
            if (cassette.getId() == id){
                CassetteModelList.remove(cassette);
                return cassette;
            }
        }

        return null;
    }

}
