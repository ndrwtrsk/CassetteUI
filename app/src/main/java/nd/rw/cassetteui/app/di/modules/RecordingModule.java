package nd.rw.cassetteui.app.di.modules;

import dagger.Module;

/**
 * Dagger module that provides Cassette related collaborators.
 */
@Module
public class RecordingModule {
    private int recordingId = -1;

    public RecordingModule() {
    }

    public RecordingModule(int recordingId) {
        this.recordingId = recordingId;
    }
}
