package nd.rw.cassetteui.app;

import android.app.Application;

import nd.rw.cassetteui.app.di.components.ApplicationComponent;
import nd.rw.cassetteui.app.di.components.DaggerApplicationComponent;
import nd.rw.cassetteui.app.di.modules.ApplicationModule;

public class CassetteApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        this.initializeInjector();
    }

    private void initializeInjector() {
        this.applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();

    }

    public ApplicationComponent getApplicationComponent() {
        return this.applicationComponent;
    }
}
