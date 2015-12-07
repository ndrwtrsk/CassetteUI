package nd.rw.cassetteui.app.di.modules;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import nd.rw.cassetteui.app.CassetteApplication;
import nd.rw.cassetteui.app.navigation.Navigator;

/**
 * Dagger module that provides objects which will live during the application lifecycle.
 */
@Module
public class ApplicationModule {
    private final CassetteApplication application;

    public ApplicationModule(CassetteApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return this.application;
    }

    @Provides
    @Singleton
    Navigator provideNavigator() {
        return new Navigator();
    }

}
