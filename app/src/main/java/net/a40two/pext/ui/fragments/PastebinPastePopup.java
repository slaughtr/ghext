package net.a40two.pext.ui.fragments;


import android.app.Activity;
import android.content.Context;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import net.a40two.pext.Constants;
import net.a40two.pext.R;
import net.a40two.pext.Settings;
import net.a40two.pext.adapters.PasteHelper;
import net.a40two.pext.services.PastebinPasteService;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class PastebinPastePopup extends DialogFragment implements AdapterView.OnItemSelectedListener {
    String pasteCallResponse = "";
    Activity activity;

    Spinner mPrivacySpinner;

    public PastebinPastePopup() {}

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        activity = getActivity();

        View rootView = inflater.inflate(R.layout.pastebin_paste_popup, container, false);
        getDialog().setTitle("Paste options");

        final Button mSubmitPasteButton = (Button) rootView.findViewById(R.id.submitPasteButton);
        final TextView mTitleText = (TextView) rootView.findViewById(R.id.pasteTitleField);

        //set all the spinners
        final Spinner mExpireSpinner = (Spinner) rootView.findViewById(R.id.expireSpinner);
        ArrayAdapter<CharSequence> expireAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.expire_date_select, android.R.layout.simple_spinner_item);
        expireAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mExpireSpinner.setAdapter(expireAdapter);

        mPrivacySpinner = (Spinner) rootView.findViewById(R.id.privacySpinner);
        ArrayAdapter<CharSequence> privacyAdapter;
        if (!Constants.LOGGED_IN) {
            //if not logged in, don't show "Private" option
            privacyAdapter = ArrayAdapter.createFromResource(getContext(),
                    R.array.private_select_not_logged_in, android.R.layout.simple_spinner_item);
        } else {
        privacyAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.private_select, android.R.layout.simple_spinner_item);
        }
        privacyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mPrivacySpinner.setAdapter(privacyAdapter);

        final Spinner mSyntaxSpinner = (Spinner) rootView.findViewById(R.id.syntaxSpinner);
        ArrayAdapter<CharSequence> syntaxAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.syntax_highlight_select, android.R.layout.simple_spinner_item);
        syntaxAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSyntaxSpinner.setAdapter(syntaxAdapter);
        //set spinner click listeners
        mSyntaxSpinner.setOnItemSelectedListener(this);
        mPrivacySpinner.setOnItemSelectedListener(this);
        mExpireSpinner.setOnItemSelectedListener(this);

        //set spinners to values from settings
        mExpireSpinner.setSelection(Settings.EXPIRE);
        mPrivacySpinner.setSelection(Settings.PRIVACY);
        mSyntaxSpinner.setSelection(Settings.SYNTAX);

        //get the body of the editText so it can be submitted to pastebin
        final String pasteBody = getArguments().getString("body");

        mSubmitPasteButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                final PastebinPasteService ppService = new PastebinPasteService();

                ppService.buildPasteUrl(pasteBody,
                        mTitleText.getText().toString(),
                        PasteHelper.getExpire(mExpireSpinner.getSelectedItem().toString()),
                        PasteHelper.getPrivacy(mPrivacySpinner.getSelectedItem().toString()),
                        PasteHelper.getSyntax(mSyntaxSpinner.getSelectedItem().toString()), new Callback() {

                            @Override public void onFailure(Call call, IOException e) { e.printStackTrace(); }

                            @Override public void onResponse(Call call, Response response) throws IOException {
                                pasteCallResponse = ppService.processResult(response);
                                showResultToast();
                            }
                        });
                dismiss();
            }
        });
        return rootView;
    }


    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) { }

    public void onNothingSelected(AdapterView<?> parent) { }

    public void showResultToast() {
        activity.runOnUiThread(new Runnable() {
            public void run() {

                if (pasteCallResponse != null) {

                    if (pasteCallResponse.contains("pastebin.com")) {
                        //if we get a URL, set it to the clipboard
                        android.content.ClipboardManager clipboard = (android.content.ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
                        android.content.ClipData clip = android.content.ClipData
                                .newPlainText("New Paste URL", pasteCallResponse);
                        clipboard.setPrimaryClip(clip);
                        Toast.makeText(activity.getApplicationContext(),
                                "New paste URL copied to clipboard\n" + pasteCallResponse,
                                Toast.LENGTH_SHORT).show();
                        //TODO: option to share paste URL to other apps

                    } else {
                        //if something went wrong, let the user know (the returnedURL in this case
                        // would hold the error (I.E. hit daily limit on pastes)
                        Toast.makeText(activity.getApplicationContext(),
                                "Something went wrong:\n"+pasteCallResponse,
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
