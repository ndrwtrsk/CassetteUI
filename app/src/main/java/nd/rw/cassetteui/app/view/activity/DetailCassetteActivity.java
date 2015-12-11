package nd.rw.cassetteui.app.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Window;

import butterknife.Bind;
import butterknife.ButterKnife;
import nd.rw.cassetteui.R;
import nd.rw.cassetteui.app.model.CassetteModel;
import nd.rw.cassetteui.app.navigation.Navigator;
import nd.rw.cassetteui.app.view.fragment.BaseFragment;
import nd.rw.cassetteui.app.view.fragment.DetailsCassetteFragment;
import nd.rw.cassetteui.app.listeners.OnCassetteClickedHandler;

public class DetailCassetteActivity extends BaseActivity {

    //region Fields

    private static final String INTENT_EXTRA_PARAM_CASSETTE_ID = "andrewtorski.cassette.INTENT_PARAM_CASSETTE_ID";
    private static final String INSTANCE_STATE_PARAM_CASSETTE_ID = "andrewtorski.cassette.STATE_PARAM_CASSETTE_ID";
    private static final String TAG = "DET_CAS_ACT";

    @Bind(R.id.toolbar)
    public Toolbar toolbar;

    private int cassetteId;

    //endregion Fields

    //region AppCompatActivity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: savedInstanceState == null?: " + (savedInstanceState == null));
        this.getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_detail_cassette);
        this.initializeActivity(savedInstanceState);
        ButterKnife.bind(this);
        this.setSupportActionBar(this.toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.toolbar.setTitle("Cassette Details");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (outState != null) {
            outState.putInt(INSTANCE_STATE_PARAM_CASSETTE_ID, cassetteId);
        }
        super.onSaveInstanceState(outState);
    }

    //endregion AppCompatActivity

    //region Private Methods

    private void initializeActivity(Bundle savedInstanceState){
        Log.d(TAG, "initializeActivity: savedInstanceState == null?: " + (savedInstanceState == null));
        if (savedInstanceState == null) {
            this.cassetteId = this.getIntent().getIntExtra(INTENT_EXTRA_PARAM_CASSETTE_ID, -1);
            BaseFragment detailsFragment = DetailsCassetteFragment.newInstance(cassetteId);
            this.addFragment(R.id.activity_detail_cassette_layout, detailsFragment);
        } else {
            this.cassetteId = savedInstanceState.getInt(INSTANCE_STATE_PARAM_CASSETTE_ID);
        }
    }

    //endregion Private Methods

    //region Static Methods

    public static Intent getCallingIntent(Context context, int cassetteId){
        Intent intent = new Intent(context, DetailCassetteActivity.class);
        intent.putExtra(INTENT_EXTRA_PARAM_CASSETTE_ID, cassetteId);

        return intent;
    }

    //endregion Static Methods
}
