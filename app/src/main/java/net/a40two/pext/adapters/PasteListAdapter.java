package net.a40two.pext.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.a40two.pext.R;
import net.a40two.pext.models.Paste;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PasteListAdapter extends RecyclerView.Adapter<PasteListAdapter.PasteViewHolder> {
    private ArrayList<Paste> mPastes = new ArrayList<>();

    public PasteListAdapter(ArrayList<Paste> pastes) {
        mPastes = pastes;
    }

    @Override public PasteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trending_pastes_item, parent, false);
        PasteViewHolder pvh = new PasteViewHolder(view);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PasteViewHolder holder, int position) {
        holder.bindPaste(mPastes.get(position));
    }

    @Override public int getItemCount() { return mPastes.size(); }

    public class PasteViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.trendingListTitleTextView) TextView mTitleTextView;
        @BindView(R.id.trendingListBodyTextView) TextView mBodyTextView;

        private Context mContext;

        public PasteViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
        }

        public void bindPaste(Paste paste) {
            mTitleTextView.setText(paste.getTitle());
            mBodyTextView.setText(paste.getBody());

        }
    }
}
