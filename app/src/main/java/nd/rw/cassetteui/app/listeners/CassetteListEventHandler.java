package nd.rw.cassetteui.app.listeners;

import nd.rw.cassetteui.app.model.CassetteModel;

public interface CassetteListEventHandler {
    void onAddCassette(CassetteModel cassetteModel);
    void onUpdateCassette(CassetteModel cassetteModel);
    void onDeleteCassette(CassetteModel cassetteModel);
}
