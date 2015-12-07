package nd.rw.cassetteui.app.di.components;

import dagger.Component;
import nd.rw.cassetteui.app.di.PerActivity;
import nd.rw.cassetteui.app.di.modules.ActivityModule;
import nd.rw.cassetteui.app.di.modules.CassetteModule;
import nd.rw.cassetteui.app.view.fragment.ListCassetteFragment;

/**
 * A scope {@link andrewtorski.casette.app.di.PerActivity} component.
 * Injects Cassette specific Fragments.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, CassetteModule.class})
public interface CassetteComponent extends ActivityComponent {
    void inject(ListCassetteFragment listCassettesFragment);
}
