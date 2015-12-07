package nd.rw.cassetteui.app.di.components;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import nd.rw.cassetteui.app.di.modules.ApplicationModule;
import nd.rw.cassetteui.app.view.activity.BaseActivity;

/**
 * A component whose lifetime is the life of the application.
 */
@Singleton // Constraints this component to one-per-application or unscoped bindings.
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(BaseActivity activity);

    //Exposed to sub-graphs.
    Context context();

}
