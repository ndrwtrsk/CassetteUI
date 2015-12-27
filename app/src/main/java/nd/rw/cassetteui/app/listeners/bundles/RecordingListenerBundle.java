package nd.rw.cassetteui.app.listeners.bundles;

import nd.rw.cassetteui.app.listeners.OnRecordingClickedHandler;
import nd.rw.cassetteui.app.listeners.OnRecordingDeleteClickedHandler;

/**
 * Container for a multitude of Listeners which are needed in RecordingSwipeAdapter and underlying
 * RecordingSwipeViewHolder.
 */
public class RecordingListenerBundle {
    private OnRecordingClickedHandler onRecordingClickedHandler;
    private OnRecordingDeleteClickedHandler onRecordingDeleteClickedHandler;

    public RecordingListenerBundle(OnRecordingClickedHandler onRecordingClickedHandler,
                                   OnRecordingDeleteClickedHandler onRecordingDeleteClickedHandler) {
        this.onRecordingClickedHandler = onRecordingClickedHandler;
        this.onRecordingDeleteClickedHandler = onRecordingDeleteClickedHandler;
    }

    public OnRecordingClickedHandler getOnRecordingClickedHandler() {
        return onRecordingClickedHandler;
    }

    public OnRecordingDeleteClickedHandler getOnRecordingDeleteClickedHandler() {
        return onRecordingDeleteClickedHandler;
    }
}
