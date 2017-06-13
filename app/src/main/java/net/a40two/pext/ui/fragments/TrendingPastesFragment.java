package net.a40two.pext.ui.fragments;

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

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TrendingPastesFragment extends Fragment {

    public static final String TAG = TrendingPastesFragment.class.getSimpleName();
    public ArrayList<Paste> mPastes = new ArrayList<>();

    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;

    public static TrendingPastesFragment newInstance(ArrayList<Paste> pastes) {
        TrendingPastesFragment tpFrag = new TrendingPastesFragment();
        Bundle args = new Bundle();
        args.putParcelable("pastes", Parcels.wrap(pastes));
        tpFrag.setArguments(args);
        return tpFrag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPastes = Parcels.unwrap(getArguments().getParcelable("pastes"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.trending_pastes_list, container, false);
        ButterKnife.bind(this, view);
        mRecyclerView.setAdapter(new PasteListAdapter(getContext(), mPastes));
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        //add snaphelper so our horizontal scrolling snaps to the screen
        SnapHelper helper = new LinearSnapHelper();
        helper.attachToRecyclerView(mRecyclerView);

        return view;
    }
}
