package nd.rw.cassette.app.view;

import java.util.Collection;

import nd.rw.cassette.app.model.CassetteModel;

public interface ListCassettesView extends LoadDataView {
    void renderCassetteList(Collection<CassetteModel> cassetteModelCollection);
    void onAddedCassette(CassetteModel cassetteModel);
    void onUpdatedCassette(CassetteModel cassetteModel);
    void onDeleteCassette(CassetteModel cassette);
}
