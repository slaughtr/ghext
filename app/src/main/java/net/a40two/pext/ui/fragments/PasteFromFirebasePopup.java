package net.a40two.pext.ui.fragments;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import net.a40two.pext.Constants;
import net.a40two.pext.R;
import net.a40two.pext.ui.EditorActivity;


public class PasteFromFirebasePopup extends DialogFragment  {
    Activity activity;

    private FirebaseListAdapter mFirebaseAdapter;
    private DatabaseReference mClipboardHistoryReference;
    private ListView mListView;

    public PasteFromFirebasePopup() {}

    // ...
    // Define the listener of the interface type
    // listener will the activity instance containing fragment
    private OnItemSelectedListener listener;

    // Define the events that the fragment will use to communicate
    public interface OnItemSelectedListener {
        // This can be any number of events to be sent to the activity
        public void clickItemFromFirebase(String text);
    }

    @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                                       Bundle savedInstanceState) {
        getDialog().getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        View rootView = inflater.inflate(R.layout.clip_history_popup_list, container, false);
        mListView = (ListView) rootView.findViewById(R.id.clipboardHistoryListView);

        activity = getActivity();
        mClipboardHistoryReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_CLIPBOARD_HISTORY);

        mFirebaseAdapter = new FirebaseListAdapter<String>
                (activity, String.class, R.layout.clip_history_popup_item, mClipboardHistoryReference) {
                TextView mTextView;

            @Override protected void populateView(View v, String text, int position) {
//                String shorterText = "";
                mTextView = (TextView) v.findViewById(R.id.clipboardHistoryTextView);

//                if(text.length() > 50) {
                    //if the returned text is too long, shorten it and put an ellipsis
//                    shorterText = text.substring(0, 51) + "...";
//                    mTextView.setText(shorterText);
//                } else {
                mTextView.setText(text);
//            }
            }
        };

        mListView.setAdapter(mFirebaseAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override public void onItemClick(AdapterView av, View v, int index, long id) {
                TextView mTextView = (TextView) v.findViewById(R.id.clipboardHistoryTextView);
                String textFromFirebase = mTextView.getText().toString();
//                Log.d("text from firebase", textFromFirebase);
//                android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
//                android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text From pext", textFromFirebase);
                listener.clickItemFromFirebase(textFromFirebase);
                dismiss();
            }
        });
        return rootView;
    }


    // Store the listener (activity) that will have events fired once the fragment is attached
    @Override public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnItemSelectedListener) {
            listener = (OnItemSelectedListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement MyListFragment.OnItemSelectedListener");
        }
    }

    @Override public void onDestroy() {
        super.onDestroy();
        mFirebaseAdapter.cleanup();
    }

}

