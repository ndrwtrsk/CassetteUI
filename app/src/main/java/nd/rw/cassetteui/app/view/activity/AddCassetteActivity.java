package nd.rw.cassetteui.app.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import nd.rw.cassetteui.R;
import nd.rw.cassetteui.app.presenter.AddCassettePresenter;
import nd.rw.cassetteui.app.view.AddCassetteView;

public class AddCassetteActivity extends BaseActivity implements AddCassetteView {
    private static final String TAG = "ADD_CAS_ACT";

    //region Fields

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
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.toolbar.setTitle("Cassette Details");

        this.presenter = new AddCassettePresenter(this);
        this.et_title.addTextChangedListener(this.titleTextWatcher);
        this.b_addCassette.setOnClickListener(this.addCassetteButtonListener);
    }

    //endregion Activity Methods

    //region Methods


    /** AddCassetteView Method */
    @Override
    public void notifyPresenter() {
        this.title = et_title.getText().toString();
        this.description = et_description.getText().toString();
        Log.i(TAG, "notifyPresenter: title = " + title + " desc = " + description);
        presenter.addCassette(title, description);
        // TODO: 11.12.2015 Leave activity.?
    }

    //endregion Methods

    //region Listeners and events

    private View.OnClickListener addCassetteButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            notifyPresenter();
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
