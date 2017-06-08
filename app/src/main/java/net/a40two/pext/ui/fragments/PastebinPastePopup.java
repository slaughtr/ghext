package net.a40two.pext.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import net.a40two.pext.Constants;
import net.a40two.pext.R;
import net.a40two.pext.models.User;
import net.a40two.pext.services.PastebinLoginService;
import net.a40two.pext.services.PastebinPasteService;

import java.io.IOException;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class PastebinPastePopup extends DialogFragment implements AdapterView.OnItemSelectedListener {

    public PastebinPastePopup() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.pastebin_paste_popup, container, false);
        getDialog().setTitle("Paste options");

        final Button mSubmitPasteButton = (Button) rootView.findViewById(R.id.submitPasteButton);

        Spinner mExpireSpinner = (Spinner) rootView.findViewById(R.id.expireSpinner);
        ArrayAdapter<CharSequence> expireAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.expire_date_select, android.R.layout.simple_spinner_item);
        expireAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mExpireSpinner.setAdapter(expireAdapter);

        Spinner mPrivacySpinner = (Spinner) rootView.findViewById(R.id.privacySpinner);
        ArrayAdapter<CharSequence> privacyAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.private_select, android.R.layout.simple_spinner_item);
        privacyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mPrivacySpinner.setAdapter(privacyAdapter);

        Spinner mSyntaxSpinner = (Spinner) rootView.findViewById(R.id.syntaxSpinner);
        ArrayAdapter<CharSequence> syntaxAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.syntax_highlight_select, android.R.layout.simple_spinner_item);
        syntaxAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSyntaxSpinner.setAdapter(syntaxAdapter);


        return rootView;
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

}
