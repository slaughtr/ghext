package net.a40two.pext.ui.fragments;


import android.content.Intent;
import android.graphics.Color;
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
import android.widget.TextView;
import android.widget.Toast;

import net.a40two.pext.Constants;
import net.a40two.pext.R;
import net.a40two.pext.adapters.PasteSyntaxHelper;
import net.a40two.pext.models.User;
import net.a40two.pext.services.PastebinLoginService;
import net.a40two.pext.services.PastebinPasteService;

import org.parceler.Parcels;
import org.w3c.dom.Text;

import java.io.IOException;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class PastebinPastePopup extends DialogFragment implements AdapterView.OnItemSelectedListener {
        String pasteReturnedURL = "";

    public PastebinPastePopup() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.pastebin_paste_popup, container, false);
        getDialog().setTitle("Paste options");

        final Button mSubmitPasteButton = (Button) rootView.findViewById(R.id.submitPasteButton);
        final TextView mTitleText = (TextView) rootView.findViewById(R.id.pasteTitleField);

        final Spinner mExpireSpinner = (Spinner) rootView.findViewById(R.id.expireSpinner);
        ArrayAdapter<CharSequence> expireAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.expire_date_select, android.R.layout.simple_spinner_item);
        expireAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mExpireSpinner.setAdapter(expireAdapter);

        final Spinner mPrivacySpinner = (Spinner) rootView.findViewById(R.id.privacySpinner);
        ArrayAdapter<CharSequence> privacyAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.private_select, android.R.layout.simple_spinner_item);
        privacyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mPrivacySpinner.setAdapter(privacyAdapter);

        final Spinner mSyntaxSpinner = (Spinner) rootView.findViewById(R.id.syntaxSpinner);
        ArrayAdapter<CharSequence> syntaxAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.syntax_highlight_select, android.R.layout.simple_spinner_item);
        syntaxAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSyntaxSpinner.setAdapter(syntaxAdapter);

        mSyntaxSpinner.setOnItemSelectedListener(this);
        mPrivacySpinner.setOnItemSelectedListener(this);
        mExpireSpinner.setOnItemSelectedListener(this);

        final String pasteBody = getArguments().getString("body");

        mSubmitPasteButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Log.d("clickListenerer", "heard that");
                final PastebinPasteService ppService = new PastebinPasteService();
                ppService.buildPasteUrl(pasteBody,
                        mTitleText.getText().toString(),
                        getExpire(mExpireSpinner.getSelectedItem().toString()),
                        getPrivacy(mPrivacySpinner.getSelectedItem().toString()),
                        PasteSyntaxHelper.getSyntax(mSyntaxSpinner.getSelectedItem().toString()), new Callback() {

                    @Override public void onFailure(Call call, IOException e) { e.printStackTrace(); }

                    @Override public void onResponse(Call call, Response response) throws IOException {

                        pasteReturnedURL = ppService.processResult(response);
                        dismiss();
                        Log.d("returnedURL", pasteReturnedURL);
                    }
                });
            }
        });

        return rootView;
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        ((TextView) view).setTextColor(Color.RED);
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    public String getPrivacy(String privacy) {
        String fixedPrivacy = "0";

        if (privacy.equals("Public")) { fixedPrivacy = "0"; }
        else if (privacy.equals("Unlisted")) { fixedPrivacy = "1"; }
        else if (privacy.equals("Private")) { fixedPrivacy = "2"; }

        return fixedPrivacy;
    }


    public String getExpire(String expires) {
        String fixedExpires = "N";

        if (expires.equals("Never")) { fixedExpires = "N"; }
        else if (expires.equals("10 Minutes")) { fixedExpires = "10m"; }
        else if (expires.equals("1 Hour")) { fixedExpires = "1H"; }
        else if (expires.equals("1 Day")) { fixedExpires = "1D"; }
        else if (expires.equals("1 Week")) { fixedExpires = "1W"; }
        else if (expires.equals("2 Weeks")) { fixedExpires = "2W"; }
        else if (expires.equals("1 Month")) { fixedExpires = "1M"; }

        return fixedExpires;
    }
}
