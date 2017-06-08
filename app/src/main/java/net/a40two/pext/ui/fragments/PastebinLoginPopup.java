package net.a40two.pext.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import net.a40two.pext.Constants;
import net.a40two.pext.R;
import net.a40two.pext.models.User;
import net.a40two.pext.services.PastebinLoginService;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class PastebinLoginPopup extends DialogFragment {
    User user = new User("", "");

    public PastebinLoginPopup() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.pastebin_login_popup, container, false);
        final EditText mUsernameField = (EditText) rootView.findViewById(R.id.username_field);
        final EditText mPasswordField = (EditText) rootView.findViewById(R.id.password_field);
        final Button mLoginButton = (Button) rootView.findViewById(R.id.login_button);
        getDialog().setTitle("Login popup");

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
                            user.setUsername(loginName);
                            user.setUserApiKey(userApiKey);
                            Constants.CURRENT_USER = user;
                            goodLogin();
                            Log.d("userapikeyafterlogin", Constants.CURRENT_USER.getUserApiKey());
                            dismiss();
                        }
                    }
                });
            }
        });
        return rootView;
    }

    private void badLogin() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(), "Bad username or password", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void goodLogin() {
        Constants.LOGGED_IN = true;
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(), "You are now logged in as " + user.getUsername(), Toast.LENGTH_LONG).show();
            }
        });

    }

}
