package net.a40two.pext.ui.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import net.a40two.pext.Constants;
import net.a40two.pext.R;
import net.a40two.pext.adapters.FirebaseClipboardHistoryViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PasteFromFirebasePopup extends DialogFragment implements AdapterView.OnClickListener {
    Activity activity;

    @BindView(R.id.clipboardHistoryRecyclerView) RecyclerView mRecyclerView;
    private FirebaseRecyclerAdapter mFirebaseAdapter;
    private DatabaseReference mClipboardHistoryReference;



    public PasteFromFirebasePopup() {}

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.clip_history_popup_list, container, false);
        Log.d("pasteFromFirebasePopup", "in on create view");

        activity = getActivity();
        ButterKnife.bind(this, rootView);
        mClipboardHistoryReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_CLIPBOARD_HISTORY);

        mFirebaseAdapter = new FirebaseRecyclerAdapter<String, FirebaseClipboardHistoryViewHolder>
                (String.class,
                        R.layout.clip_history_popup_item,
                        FirebaseClipboardHistoryViewHolder.class,
                        mClipboardHistoryReference) {

            @Override protected void populateViewHolder(FirebaseClipboardHistoryViewHolder viewHolder,
                                                        String item, int position) {
                Log.d("populateViewHolder", "POPULATING VIEW HOLDER");
                viewHolder.bindClipboardItem(item);
            }
        };

        mRecyclerView.setAdapter(mFirebaseAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        return rootView;
    }

    @Override public void onClick(View v) {

    }

    @Override public void onDestroy() {
        super.onDestroy();
        mFirebaseAdapter.cleanup();
    }
}

