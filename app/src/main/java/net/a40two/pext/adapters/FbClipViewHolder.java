// // / // // // / / // // // / /LEAVING THIS HERE IN CASE OF FUTURE USE FOR THE MOMENT


//package net.a40two.pext.adapters;
//
//import android.content.Context;
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//import android.view.View;
//import android.widget.TextView;
//
//import net.a40two.pext.R;
//
//public class FbClipViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//
//    View mView;
//    Context mContext;
//
//    public FbClipViewHolder(View itemView) {
//        super(itemView);
//        Log.d("viewholder", "hi");
//        mView = itemView;
//        mContext = itemView.getContext();
//        itemView.setOnClickListener(this);
//    }
//
//    public void bindClipboardItem(FirebaseString item) {
//        final TextView mClipboardTextView = (TextView) mView.findViewById(R.id.clipboardHistoryTextView);
//
////        mClipboardTextView.setText(FirebaseString.getText());
//
////            DatabaseReference ref = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_CLIPBOARD_HISTORY);
////            ref.addListenerForSingleValueEvent(new ValueEventListener() {
////
////                @Override public void onDataChange(DataSnapshot dataSnapshot) {
////                    Log.d("bindClipboardItem", dataSnapshot.getValue().toString());
////                    mClipboardTextView.setText(dataSnapshot.getValue().toString());
////
////                    int itemPosition = getLayoutPosition();
////                }
////
////                @Override public void onCancelled(DatabaseError databaseError) {}
////
////            });
//
//
//    }
//
//    @Override public void onClick(View v) {
//        Log.d("fbvhonClick", "click");
//
//    }
//}
