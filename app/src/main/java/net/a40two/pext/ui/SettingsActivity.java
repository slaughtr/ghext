package net.a40two.pext.ui;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import net.a40two.pext.Constants;
import net.a40two.pext.R;
import net.a40two.pext.Settings;

public class SettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    Spinner mExpireSpinner;
    Spinner mPrivacySpinner;
    Spinner mSyntaxSpinner;
    Spinner mTextSizeSpinner;
    Spinner mResultLimitSpinner;
    Button mSaveSettingsButton;

    //for saving to shared prefs
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    //for saving to firebase
    private DatabaseReference mSettingsReference;

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

        //set spinners to values loaded from settings
        mExpireSpinner.setSelection(Settings.EXPIRE);
        mPrivacySpinner.setSelection(Settings.PRIVACY);
        mSyntaxSpinner.setSelection(Settings.SYNTAX);
        mTextSizeSpinner.setSelection(getIndex(mTextSizeSpinner, Settings.TEXT_SIZE));
        mResultLimitSpinner.setSelection(getIndex(mResultLimitSpinner, Settings.RESULT_LIMIT));

        mSaveSettingsButton = (Button) this.findViewById(R.id.save_settings_button);
        mSaveSettingsButton.setOnClickListener(this);

        mSettingsReference = FirebaseDatabase
                .getInstance()
                .getReference()
                .child(Constants.USER_NAME)
                .child(Constants.FIREBASE_CHILD_SETTINGS);


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) { }

    @Override
    public void onNothingSelected(AdapterView<?> parent) { }

    @Override
    public void onClick(View v) {
        if (v == mSaveSettingsButton) {
            saveSettings();
        }
    }

    private void saveSettings() {
        //set values in Settings
        Settings.EXPIRE = mExpireSpinner.getSelectedItemPosition();
        Settings.PRIVACY = mPrivacySpinner.getSelectedItemPosition();
        Settings.SYNTAX = mSyntaxSpinner.getSelectedItemPosition();
        Settings.TEXT_SIZE = Integer.parseInt(mTextSizeSpinner.getSelectedItem().toString());
        Settings.RESULT_LIMIT = Integer.parseInt(mResultLimitSpinner.getSelectedItem().toString());

        //set values in shared preferences
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPreferences.edit();
        mEditor.putInt(Constants.PREFERENCES_EXPIRATION_KEY, Settings.EXPIRE).apply();
        mEditor.putInt(Constants.PREFERENCES_PRIVACY_KEY, Settings.PRIVACY).apply();
        mEditor.putInt(Constants.PREFERENCES_SYNTAX_KEY, Settings.SYNTAX).apply();
        mEditor.putInt(Constants.PREFERENCES_TEXT_SIZE_KEY, Settings.TEXT_SIZE).apply();
        mEditor.putInt(Constants.PREFERENCES_RESULT_LIMIT_KEY, Settings.RESULT_LIMIT).apply();

        //set values in firebase
        mSettingsReference.child("EXPIRE").setValue(Settings.EXPIRE);
        mSettingsReference.child("PRIVACY").setValue(Settings.PRIVACY);
        mSettingsReference.child("SYNTAX").setValue(Settings.SYNTAX);
        mSettingsReference.child("TEXT_SIZE").setValue(Settings.TEXT_SIZE);
        mSettingsReference.child("RESULT_LIMIT").setValue(Settings.RESULT_LIMIT);
    }

    //used to get position of spinner by provided value
    //useful for text size and result limit
    private int getIndex(Spinner spinner, int value){
        int index = 0;
        for (int i=0;i<spinner.getCount();i++){
            if (Integer.parseInt(spinner.getItemAtPosition(i).toString()) == value) {
                index = i;
            }
        }
        return index;
    }
}
