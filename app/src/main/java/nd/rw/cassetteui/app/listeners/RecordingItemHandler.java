package nd.rw.cassetteui.app.listeners;

import nd.rw.cassetteui.app.model.RecordingModel;
import nd.rw.cassetteui.app.view.adapter.RecordingSwipeAdapter;

public interface RecordingItemHandler {

    void recordingItemClicked(RecordingModel recordingModel, RecordingSwipeAdapter.RecordingSwipeViewHolder viewHolder);
    void playClicked(RecordingModel recordingModel);
    void deleteClicked(RecordingModel recordingModel);

}
