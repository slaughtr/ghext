package net.a40two.pext.ui.fragments;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import net.a40two.pext.R;

public class PasteFromFirebasePopup extends DialogFragment implements AdapterView.OnClickListener {
    String pasteCallResponse = "";
    Activity activity;

    public PasteFromFirebasePopup() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        activity = getActivity();

        View rootView = inflater.inflate(R.layout.pastebin_paste_popup, container, false);
        return rootView;
    }

    @Override
    public void onClick(View v) {

    }
}

