package nd.rw.cassetteui.app.view.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;

import nd.rw.cassetteui.app.di.HasComponent;

/**
 * Created by andrew on 07.12.2015.
 */
public class BaseFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRetainInstance(true);
    }

    /**
     * Gets a component for dependency injection by its type.
     */
    @SuppressWarnings("unchecked")
    protected <C> C getComponent(Class<C> componentType) {
        C component = ((HasComponent<C>) getActivity()).getComponent();

        if (component == null) {
        }

        return componentType.cast(component);
    }
}
