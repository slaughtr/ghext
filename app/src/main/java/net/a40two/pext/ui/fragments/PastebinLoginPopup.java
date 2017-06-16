package net.a40two.pext.ui.fragments;


import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import net.a40two.pext.Constants;
import net.a40two.pext.R;
import net.a40two.pext.Settings;
import net.a40two.pext.models.User;
import net.a40two.pext.services.PastebinLoginService;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class PastebinLoginPopup extends DialogFragment {
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    Activity activity;
    User user = new User("", "");


    public PastebinLoginPopup() {}

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().getWindow().getAttributes().windowAnimations = R.style.DialogAnimationInverse;
        View rootView = inflater.inflate(R.layout.pastebin_login_popup, container, false);

        activity = getActivity();

        final EditText mUsernameField = (EditText) rootView.findViewById(R.id.username_field);
        final EditText mPasswordField = (EditText) rootView.findViewById(R.id.password_field);
        final Button mLoginButton = (Button) rootView.findViewById(R.id.login_button);
        getDialog().setTitle("Login popup");

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        mEditor = mSharedPreferences.edit();

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String loginName = mUsernameField.getText().toString();
                String loginPass = mPasswordField.getText().toString();

                PastebinLoginService.buildLoginUrl(loginName, loginPass, new Callback() {
                    @Override public void onFailure(Call call, IOException e) { e.printStackTrace(); }
                    @Override public void onResponse(Call call, Response response) throws IOException {
                        String userApiKey = PastebinLoginService.processResult(response);

                        if (userApiKey.contains("invalid")) { badLogin(); }
                        else {
                            Constants.LOGGED_IN = true;
                            Constants.CURRENT_USER = user;
                            Constants.USER_API_KEY = userApiKey;
                            Constants.USER_NAME = loginName;

                            user.setUsername(loginName);
                            user.setUserApiKey(userApiKey);
                            addToSharedPreferences(userApiKey, loginName);
                            goodLogin();
                            dismiss();
                        }
                    }
                });
            }
        });
        return rootView;
    }

    private void badLogin() {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(activity, "Bad username or password", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void goodLogin() {
        Constants.LOGGED_IN = true;
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(activity, "You are now logged in as " + user.getUsername(), Toast.LENGTH_LONG).show();

                //on a good login, should pull settings from firebase
                //and save them to Settings and shared preferences
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference(Constants.USER_NAME).child("savedSettings");
                ref.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override public void onDataChange(DataSnapshot dataSnapshot) {
                        //TODO: add loading indicator while this value is retrieved
                        if (dataSnapshot.getValue() != null) {
                            Settings.EXPIRE = Integer.parseInt(dataSnapshot.child("EXPIRE").getValue().toString());
                            Settings.PRIVACY = Integer.parseInt(dataSnapshot.child("PRIVACY").getValue().toString());
                            Settings.SYNTAX = Integer.parseInt(dataSnapshot.child("SYNTAX").getValue().toString());
                            Settings.TEXT_SIZE = Integer.parseInt(dataSnapshot.child("TEXT_SIZE").getValue().toString());
                            Settings.RESULT_LIMIT = Integer.parseInt(dataSnapshot.child("RESULT_LIMIT").getValue().toString());

                            mEditor.putInt(Constants.PREFERENCES_EXPIRATION_KEY, Settings.EXPIRE).apply();
                            mEditor.putInt(Constants.PREFERENCES_PRIVACY_KEY, Settings.PRIVACY).apply();
                            mEditor.putInt(Constants.PREFERENCES_SYNTAX_KEY, Settings.SYNTAX).apply();
                            mEditor.putInt(Constants.PREFERENCES_TEXT_SIZE_KEY, Settings.TEXT_SIZE).apply();
                            mEditor.putInt(Constants.PREFERENCES_RESULT_LIMIT_KEY, Settings.RESULT_LIMIT).apply();

                            Toast.makeText(activity, "Settings loaded from the cloud!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(activity, "Error loading settings from the cloud\n"+databaseError.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void addToSharedPreferences(String userKey, String userName) {
        mEditor.putString(Constants.PREFERENCES_USER_API_KEY, userKey).apply();
        mEditor.putString(Constants.PREFERENCES_USER_NAME_KEY, userName).apply();
    }

}
