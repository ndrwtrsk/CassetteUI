package nd.rw.cassetteui.app.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import butterknife.Bind;
import butterknife.ButterKnife;
import nd.rw.cassetteui.R;
import nd.rw.cassetteui.app.presenter.DeleteCassettePresenter;
import nd.rw.cassetteui.app.view.fragment.BaseFragment;
import nd.rw.cassetteui.app.view.fragment.DeleteCassetteDialogFragment;
import nd.rw.cassetteui.app.view.fragment.DetailsCassetteFragment;

public class DetailCassetteActivity
        extends BaseActivity
        implements DeleteCassetteDialogFragment.DeleteCassetteNoticeListener{

    //region Fields

    private static final String INTENT_EXTRA_PARAM_CASSETTE_ID = "andrewtorski.cassette.INTENT_PARAM_CASSETTE_ID";
    private static final String INSTANCE_STATE_PARAM_CASSETTE_ID = "andrewtorski.cassette.STATE_PARAM_CASSETTE_ID";
    private static final String TAG = "DET_CAS_ACT";

    @Bind(R.id.toolbar)
    public Toolbar toolbar;

    private int cassetteId;

    private DeleteCassettePresenter presenter = new DeleteCassettePresenter();

    //endregion Fields

    //region AppCompatActivity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_detail_cassette);
        this.initializeActivity(savedInstanceState);
        ButterKnife.bind(this);
        this.setSupportActionBar(this.toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.toolbar.setTitle("New Cassette");
        this.toolbar.setNavigationOnClickListener(homeButtonClickListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.menu_detail_cassette, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_delete_cassette) {
            this.showDeleteDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
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

    //region Listeners and Events

    private void showDeleteDialog(){
        DialogFragment dialog = new DeleteCassetteDialogFragment();
        dialog.show(this.getSupportFragmentManager(), "DeleteCassetteDialogFragment");
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        Log.d(TAG, "onDialogPositiveClick: POSITIVE");
        this.presenter.deleteCassette(this.cassetteId);
        this.finish();
    }

    public View.OnClickListener homeButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            DetailCassetteActivity.this.finish();
        }
    };

    //endregion Listeners and Events

    //region Static Methods

    public static Intent getCallingIntent(Context context, int cassetteId){
        Intent intent = new Intent(context, DetailCassetteActivity.class);
        intent.putExtra(INTENT_EXTRA_PARAM_CASSETTE_ID, cassetteId);

        return intent;
    }

    //endregion Static Methods
}
