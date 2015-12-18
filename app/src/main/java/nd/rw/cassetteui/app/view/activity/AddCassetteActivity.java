package nd.rw.cassetteui.app.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import nd.rw.cassetteui.R;
import nd.rw.cassetteui.app.model.CassetteModel;
import nd.rw.cassetteui.app.presenter.AddCassettePresenter;
import nd.rw.cassetteui.app.view.AddCassetteView;

public class AddCassetteActivity extends BaseActivity implements AddCassetteView {

    //region Fields
    private static final String TAG = "ADD_CAS_ACT";
    public static final String INTENT_EXTRA_PARAM__WAS_ADDED = "nd.rw.cassetteui.INTENT_PARAM_WAS_ADDED";
    public static final String INTENT_EXTRA_PARAM_NEW_CASSETTE_ID = "nd.rw.cassetteui.INTENT_PARAM_NEW_CASSETTE_ID";
    public static final int ADD_ACTIVITY_LIST_FRAGMENT_RESULT_CODE = 1;

    private AddCassettePresenter presenter;

    @Bind(R.id.toolbar)
    public Toolbar toolbar;

    @Bind(R.id.add_cassette_toolbar_button)
    public Button b_addCassette;

    @Bind(R.id.add_cassette_title)
    public EditText et_title;

    @Bind(R.id.add_cassette_description)
    public EditText et_description;

    private String title;

    private String description;

    //endregion Fields

    //region Activity Methods

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_add_cassette);
        ButterKnife.bind(this);
        this.setSupportActionBar(this.toolbar);
        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.toolbar.setTitle("Cassette Details");

        this.toolbar.setNavigationOnClickListener(this.toolbarNavigationButtonListener);
        this.presenter = new AddCassettePresenter(this);
        this.et_title.addTextChangedListener(this.titleTextWatcher);
        this.b_addCassette.setOnClickListener(this.addCassetteButtonListener);
    }

    //endregion Activity Methods

    //region Methods


    /** AddCassetteView Method */
    @Override
    public void addCassette() {
        this.title = et_title.getText().toString();
        this.description = et_description.getText().toString();
        CassetteModel cassetteModel = presenter.addCassette(title, description);
        Intent intent = new Intent();
        boolean wasAdded = cassetteModel != null;
        intent.putExtra(INTENT_EXTRA_PARAM__WAS_ADDED, wasAdded);
        if (cassetteModel != null) {
            intent.putExtra(INTENT_EXTRA_PARAM_NEW_CASSETTE_ID, cassetteModel.getId());
        }
        setResult(ADD_ACTIVITY_LIST_FRAGMENT_RESULT_CODE, intent);
        finish();
    }

    //endregion Methods

    //region Listeners and events

    private View.OnClickListener addCassetteButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            addCassette();
        }
    };

    private View.OnClickListener toolbarNavigationButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            AddCassetteActivity.this.finish();
        }
    };

    private TextWatcher titleTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            boolean shouldAddCassetteButtonBeEnabled = editable.length() > 0;
            AddCassetteActivity.this.b_addCassette.setEnabled(shouldAddCassetteButtonBeEnabled);
        }
    };

    //endregion Listeners and events

    //region Static Methods

    public static Intent getCallingIntent(Context context){
        return new Intent(context, AddCassetteActivity.class);
    }

    //endregion Static Methods

}
