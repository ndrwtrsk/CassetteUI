package nd.rw.cassetteui.app.di.components;

import android.app.Activity;

import dagger.Component;
import nd.rw.cassetteui.app.di.PerActivity;
import nd.rw.cassetteui.app.di.modules.ActivityModule;

/**
 * A base component upon which fragment's components may depend.
 * Activity-level components should extend this component.
 * <p/>
 * Subtypes of ActivityComponent should be decorated with annotation:
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    Activity activity();
}
