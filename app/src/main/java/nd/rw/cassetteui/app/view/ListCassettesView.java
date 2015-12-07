package nd.rw.cassetteui.app.view;

import java.util.Collection;

import nd.rw.cassetteui.app.model.CassetteModel;

public interface ListCassettesView extends LoadDataView {
    void renderCassetteList(Collection<CassetteModel> cassetteModelCollection);

    void viewCassette(CassetteModel cassetteModel);
}
