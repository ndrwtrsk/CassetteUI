package nd.rw.cassetteui.app.di.modules;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import nd.rw.cassetteui.app.di.PerActivity;
import nd.rw.cassetteui.domain.usecase.ListCassettesUseCase;

/**
 * Dagger module that provides Cassette related collaborators.
 */
@Module
public class CassetteModule {

    private long cassetteId = -1;

    public CassetteModule() {
    }

    public CassetteModule(long cassetteId) {
        this.cassetteId = cassetteId;
    }

    @Provides
    @PerActivity
    @Named("listCassettes")
    ListCassettesUseCase provideListCassetteUseCase(ListCassettesUseCase useCase) {
        return useCase;
    }

}
