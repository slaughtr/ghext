package net.a40two.pext.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.a40two.pext.R;
import net.a40two.pext.adapters.PasteListAdapter;
import net.a40two.pext.models.Paste;
import net.a40two.pext.services.PastebinListService;

import org.parceler.Parcels;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class UserPastesFragment extends Fragment {
    public static final String TAG = UserPastesFragment.class.getSimpleName();
    public ArrayList<Paste> mPastes = new ArrayList<>();
    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;

    Activity activity;

    public static UserPastesFragment newInstance(ArrayList<Paste> pastes) {
        UserPastesFragment upFrag = new UserPastesFragment();
        Bundle args = new Bundle();
        args.putParcelable("pastes", Parcels.wrap(pastes));
        upFrag.setArguments(args);
        return upFrag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        activity = getActivity();
        super.onCreate(savedInstanceState);
        mPastes = Parcels.unwrap(getArguments().getParcelable("pastes"));
        getOwnPastes();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.paste_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    private void setupRecyclerView() {
        activity.runOnUiThread(new Runnable() {
            @Override public void run() {
                mRecyclerView.setAdapter(new PasteListAdapter(getContext(), mPastes));
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                mRecyclerView.setLayoutManager(layoutManager);
                mRecyclerView.setHasFixedSize(true);
                SnapHelper helper = new LinearSnapHelper();
                helper.attachToRecyclerView(mRecyclerView);
            }
        });
    }

    private void getOwnPastes() {
        final PastebinListService pblService = new PastebinListService();

        pblService.buildListUrl("ownPastes", new Callback() {

            @Override public void onFailure(Call call, IOException e) { e.printStackTrace(); }

            @Override public void onResponse(Call call, Response response) throws IOException {

                mPastes = pblService.processResults("ownPastes", response);
                setupRecyclerView();
            }
        });
    }

}
