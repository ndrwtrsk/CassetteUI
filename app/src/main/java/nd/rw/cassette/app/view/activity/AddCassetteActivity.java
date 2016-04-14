package nd.rw.cassette.app.view.activity;

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
import nd.rw.cassette.R;
import nd.rw.cassette.app.model.CassetteModel;
import nd.rw.cassette.app.presenter.AddCassettePresenter;
import nd.rw.cassette.app.view.AddCassetteView;

public class AddCassetteActivity extends BaseActivity implements AddCassetteView {

    //region Fields
    private static final String TAG = "ADD_CAS_ACT";

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
        // TODO: 25.12.2015 Notify is add operation was not successful; i.e. returend cassette is null
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
