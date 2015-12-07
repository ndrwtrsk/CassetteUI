package nd.rw.cassetteui.domain.usecase;

import java.util.List;

import nd.rw.cassetteui.app.model.CassetteModel;

public class ListCassettesUseCase extends AbstractUseCase {

    public List<CassetteModel> getCassettes(){
        return CassetteModel.getListOfCassettes(10, 5);
    }

}
