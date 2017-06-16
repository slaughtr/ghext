package net.a40two.pext.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import net.a40two.pext.R;

public class SettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //set all the spinners
        final Spinner mExpireSpinner = (Spinner) this.findViewById(R.id.default_expiration_spinner);
        ArrayAdapter<CharSequence> expireAdapter = ArrayAdapter.createFromResource(this,
                R.array.expire_date_select, android.R.layout.simple_spinner_item);
        expireAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mExpireSpinner.setAdapter(expireAdapter);

        final Spinner mPrivacySpinner = (Spinner) this.findViewById(R.id.default_privacy_spinner);
        ArrayAdapter<CharSequence> privacyAdapter = ArrayAdapter.createFromResource(this,
                R.array.private_select, android.R.layout.simple_spinner_item);
        privacyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mPrivacySpinner.setAdapter(privacyAdapter);

        final Spinner mSyntaxSpinner = (Spinner) this.findViewById(R.id.default_syntax_spinner);
        ArrayAdapter<CharSequence> syntaxAdapter = ArrayAdapter.createFromResource(this,
                R.array.syntax_highlight_select, android.R.layout.simple_spinner_item);
        syntaxAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSyntaxSpinner.setAdapter(syntaxAdapter);
        //set spinner click listeners
        mSyntaxSpinner.setOnItemSelectedListener(this);
        mPrivacySpinner.setOnItemSelectedListener(this);
        mExpireSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
