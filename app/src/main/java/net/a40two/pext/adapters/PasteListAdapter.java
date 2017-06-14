package net.a40two.pext.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.a40two.pext.R;
import net.a40two.pext.models.Paste;
import net.a40two.pext.ui.EditorActivity;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PasteListAdapter extends RecyclerView.Adapter<PasteListAdapter.PasteViewHolder> {
    private ArrayList<Paste> mPastes = new ArrayList<>();

    private Context mContext;
    public PasteListAdapter(Context context, ArrayList<Paste> pastes) {
        mContext = context;
        mPastes = pastes;
    }

    @Override public PasteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trending_pastes_item, parent, false);
        PasteViewHolder pvh = new PasteViewHolder(view);
        return pvh;
    }


    @Override public void onBindViewHolder(PasteViewHolder holder, int position) {
        holder.bindPaste(mPastes.get(position));
    }

    @Override public int getItemCount() { return mPastes.size(); }

    public class PasteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.trendingListTitleTextView) TextView mTitleTextView;
        @BindView(R.id.trendingListBodyTextView) TextView mBodyTextView;
        @BindView(R.id.openInEditorFAB) FloatingActionButton mOpenInEditorFAB;

        public PasteViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();

            mOpenInEditorFAB.setOnClickListener(this);
        }

        public void bindPaste(Paste paste) {
            mTitleTextView.setText(paste.getTitle());
            mBodyTextView.setText(paste.getBody());

        }

        @Override public void onClick(View v) {
            int position = getAdapterPosition();
            Intent intent = new Intent(mContext, EditorActivity.class);
            intent.putExtra("editPasteBody", Parcels.wrap(mBodyTextView.getText()));
            mContext.startActivity(intent);
        }
    }
}
