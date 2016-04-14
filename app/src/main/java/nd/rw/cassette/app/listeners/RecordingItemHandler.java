package nd.rw.cassette.app.listeners;

import nd.rw.cassette.app.model.RecordingModel;
import nd.rw.cassette.app.view.adapter.RecordingSwipeAdapter;

public interface RecordingItemHandler {

    void recordingItemClicked(RecordingModel recordingModel, RecordingSwipeAdapter.RecordingSwipeViewHolder viewHolder);
    void playClicked(RecordingModel recordingModel);
    void deleteClicked(RecordingModel recordingModel);

}
