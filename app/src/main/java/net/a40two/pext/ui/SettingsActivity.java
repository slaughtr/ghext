package net.a40two.pext.ui;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import net.a40two.pext.R;

public class SettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner mExpireSpinner;
    Spinner mPrivacySpinner;
    Spinner mSyntaxSpinner;
    Spinner mTextSizeSpinner;
    Spinner mResultLimitSpinner;

    //these will be used to save spinner positions to firebase/shared prefs
    //so that they can later be used to load defaults
    int expireSpinnerPos;
    int privacySpinnerPos;
    int syntaxSpinnerPos;

    //and here we just want values, as there's no correlating
    //spinners for these items
    int textSizeSpinnerVal;
    int resultLimitSpinnerVal;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //set all the spinners
        mExpireSpinner = (Spinner) this.findViewById(R.id.default_expiration_spinner);
        ArrayAdapter<CharSequence> expireAdapter = ArrayAdapter.createFromResource(this,
                R.array.expire_date_select, android.R.layout.simple_spinner_item);
        expireAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mExpireSpinner.setAdapter(expireAdapter);

        mPrivacySpinner = (Spinner) this.findViewById(R.id.default_privacy_spinner);
        ArrayAdapter<CharSequence> privacyAdapter = ArrayAdapter.createFromResource(this,
                R.array.private_select, android.R.layout.simple_spinner_item);
        privacyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mPrivacySpinner.setAdapter(privacyAdapter);

        mSyntaxSpinner = (Spinner) this.findViewById(R.id.default_syntax_spinner);
        ArrayAdapter<CharSequence> syntaxAdapter = ArrayAdapter.createFromResource(this,
                R.array.syntax_highlight_select, android.R.layout.simple_spinner_item);
        syntaxAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSyntaxSpinner.setAdapter(syntaxAdapter);

        mTextSizeSpinner = (Spinner) this.findViewById(R.id.text_size_spinner);
        ArrayAdapter<CharSequence> textSizeAdapter = ArrayAdapter.createFromResource(this, R.array.editor_text_size_select, android.R.layout.simple_spinner_dropdown_item);
        mTextSizeSpinner.setAdapter(textSizeAdapter);

        mResultLimitSpinner = (Spinner) this.findViewById(R.id.results_limit_spinner);
        ArrayAdapter<CharSequence> resultLimitAdapter = ArrayAdapter.createFromResource(this, R.array.result_limit_select, android.R.layout.simple_spinner_dropdown_item);
        mResultLimitSpinner.setAdapter(resultLimitAdapter);

        //set spinner click listeners
        mExpireSpinner.setOnItemSelectedListener(this);
        mPrivacySpinner.setOnItemSelectedListener(this);
        mSyntaxSpinner.setOnItemSelectedListener(this);
        mTextSizeSpinner.setOnItemSelectedListener(this);
        mResultLimitSpinner.setOnItemSelectedListener(this);


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//        ((TextView) view).setTextColor(Color.RED);
        if (view == mExpireSpinner) { }
        if (view == mPrivacySpinner) { }
        if (view == mSyntaxSpinner) { }
        if (view == mTextSizeSpinner) { }
        if (view == mResultLimitSpinner) { }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
