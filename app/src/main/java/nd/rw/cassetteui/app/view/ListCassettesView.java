package nd.rw.cassetteui.app.view;

import java.util.Collection;

import nd.rw.cassetteui.app.listeners.OnCassetteClickedHandler;
import nd.rw.cassetteui.app.model.CassetteModel;

public interface ListCassettesView extends LoadDataView {
    void renderCassetteList(Collection<CassetteModel> cassetteModelCollection);
    void viewCassette(CassetteModel cassetteModel);
    void setOnCassetteClicked(OnCassetteClickedHandler onCassetteClickedHandlerListener);
    void onAddedCassette(CassetteModel cassetteModel);
    void onUpdatedCassette(CassetteModel cassetteModel);
    void onDeleteCassette(int cassetteId);
}
