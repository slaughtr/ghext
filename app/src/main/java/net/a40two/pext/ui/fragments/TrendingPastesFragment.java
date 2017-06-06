package net.a40two.pext.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.a40two.pext.R;
import net.a40two.pext.adapters.PasteListAdapter;
import net.a40two.pext.models.Paste;
import net.a40two.pext.services.PastebinListService;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class TrendingPastesFragment extends Fragment {
    public static final String TAG = TrendingPastesFragment.class.getSimpleName();
    public ArrayList<Paste> mPastes = new ArrayList<>();
    private PasteListAdapter mAdapter;
    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;
    @BindView(R.id.trendingListTitleTextView) TextView mTitleTextView;

    public TrendingPastesFragment() {} //empty constructor

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.trending_pastes_list, container, false);
        ButterKnife.bind(this, view);
        // Inflate the layout for this fragment

        getTrendingPastes();
        return view;
    }

    private void getTrendingPastes() {
        final PastebinListService pblService = new PastebinListService();

        pblService.buildListUrl("trending", new Callback() {

            @Override public void onFailure(Call call, IOException e) { e.printStackTrace(); }

            @Override public void onResponse(Call call, Response response) throws IOException {
                mPastes = pblService.processResults("trending", response);

                getActivity().runOnUiThread(new Runnable() {

                    @Override public void run() {
                        mAdapter = new PasteListAdapter(getActivity(), mPastes);
                        mRecyclerView.setAdapter(mAdapter);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                        mRecyclerView.setLayoutManager(layoutManager);
                        mRecyclerView.setHasFixedSize(true);
                    }
                });
            }
        });
    }

}
