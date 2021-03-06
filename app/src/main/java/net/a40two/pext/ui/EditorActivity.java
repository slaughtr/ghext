package net.a40two.pext.ui;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import net.a40two.pext.Constants;
import net.a40two.pext.R;
import net.a40two.pext.ui.fragments.PasteFromFirebasePopup;
import net.a40two.pext.ui.fragments.PastebinPastePopup;
import net.a40two.pext.ui.views.AdvancedEditText;

import org.parceler.Parcels;

import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.content.ClipDescription.MIMETYPE_TEXT_PLAIN;

public class EditorActivity extends AppCompatActivity implements PasteFromFirebasePopup.OnItemSelectedListener {

    AdvancedEditText mEditText;

    private DatabaseReference mEditorStateReference;
    private DatabaseReference mClipboardReference;

    //if user isn't logged in, we'll try to get
    //the editor state from shared preferences
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    public static final String TAG = EditorActivity.class.getSimpleName();

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        mEditText = (AdvancedEditText) this.findViewById(R.id.editorAdvancedTextView);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPreferences.edit();

        //check if user is logged in before
        //getting firebase instance, as there's
        //no point if not
        if (Constants.LOGGED_IN) {
            mEditorStateReference = FirebaseDatabase
                    .getInstance()
                    .getReference()
                    .child(Constants.USER_NAME)
                    .child(Constants.FIREBASE_CHILD_SAVED_EDITOR_STATE);

            mClipboardReference = FirebaseDatabase
                    .getInstance()
                    .getReference()
                    .child(Constants.USER_NAME)
                    .child(Constants.FIREBASE_CHILD_CLIPBOARD_HISTORY);
        }
    }

    @Override public void onStart() {
        super.onStart();
        //get intent
        Intent intent = getIntent();

        //get action and type for checking
        String action = intent.getAction();
        String type = intent.getType();

        //get parcel with body sent from edit
        String editPasteBody = Parcels.unwrap(intent.getParcelableExtra("editPasteBody"));

        // if parcelable not null (might be, if opening activity another way),
        // then set the AdvancedEditText text to that.
        if (editPasteBody != null && editPasteBody.length() > 0) {
            if (!editPasteBody.equals("") || !editPasteBody.equals(" ")) {
                mEditText.setText(editPasteBody);
            }
            //handle intents coming from outside the app,
            //checking that they're the right kind of intent
        } else if (Intent.ACTION_SEND.equals(action) && type != null && type.startsWith("text/")) {
                mEditText.setText(intent.getStringExtra(Intent.EXTRA_TEXT));
        } else {
            //another logged in check, to be safe
            if (Constants.LOGGED_IN) {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference(Constants.USER_NAME).child("savedFromEditor");
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override public void onDataChange(DataSnapshot dataSnapshot) {
                        //TODO: add loading indicator while this value is retrieved
                        if (dataSnapshot.getValue() != null) {
                            mEditText.setText(dataSnapshot.getValue().toString());
                        }
                    }
                    @Override public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(getApplicationContext(), "Error:\n"+databaseError.toString(), Toast.LENGTH_LONG).show();
                    }
                });
            } else {
                //set editor body to text form shared prefs
                mEditText.setText(mSharedPreferences.getString(Constants.PREFERENCES_EDITOR_BODY_KEY, null));
            }
        }
    }


    @Override public void onStop() {
        super.onStop();
        if (Constants.LOGGED_IN) {
            saveEditorStateToFirebase(mEditText.getText().toString());
        } else {
            mEditor.putString(Constants.PREFERENCES_EDITOR_BODY_KEY, mEditText.getText().toString());
        }
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.editor_overflow_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        //for the overflow menu
        int id = item.getItemId();
        if (id == R.id.action_copy) { copyOrCutSelection(false); }
        else if (id == R.id.action_cut) { copyOrCutSelection(true); }
        else if (id == R.id.action_paste) { paste(null); }
        else if (id == R.id.action_paste_history) {
            if (Constants.LOGGED_IN) {
                FragmentManager fm = getSupportFragmentManager();
                PasteFromFirebasePopup pffb = new PasteFromFirebasePopup();
                pffb.show(fm, "Pastebin paste popup");
            } else {
                Toast.makeText(this, "Sorry, you must be logged in to do this.", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.action_pastebin) {
            //open popup fragment for pushing to pastebin
            Bundle args = new Bundle();
            //put text to bundle so that the paste popup has access to it for submitting
            args.putString("body", mEditText.getText().toString());
            FragmentManager fm = getSupportFragmentManager();
            PastebinPastePopup ppp = new PastebinPastePopup();
            ppp.setArguments(args);
            ppp.show(fm, "Pastebin paste popup");
        } else if (id == R.id.action_clear) { mEditText.setText(""); } //run when clicking clear

        return super.onOptionsItemSelected(item);
    }

    private void copyOrCutSelection(boolean cut) {
        String copiedString = mEditText.getText().toString();
        int start = mEditText.getSelectionStart();
        int end = mEditText.getSelectionEnd();
        //do check, since if you start your selection
        // on the right and highlight to the left,
        // your selectionStart index will be greater than
        // your selectionEnd index and things will break
        if (mEditText.getSelectionStart() > mEditText.getSelectionEnd()) {
            start = mEditText.getSelectionEnd();
            end = mEditText.getSelectionStart();
        }

        copiedString = copiedString.substring(start, end);

        //if logged in, save clipboard to firebase
        //TODO: add check for number of items in firebase?
        if (Constants.LOGGED_IN) { saveClipboardToFirebase(copiedString); }

        //do the clipboard dance
        android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text From pext", copiedString);
        clipboard.setPrimaryClip(clip);

        if (cut) {
            //a somewhat gross way of cutting. Copy the selected text,
            // then make a whole new string out of the entire body, minus what you selected.
            String allText = mEditText.getText().toString();
            String newText = allText.substring(0, start)+allText.substring(end, allText.length());
            mEditText.setText(newText);
        }
    }

    public void paste(@Nullable String text) {
        //paste clipboard to location TODO: pasting over selection deletes selection?
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        String pasteData = "";

        if (text != null) { mEditText.getText().insert(mEditText.getSelectionStart(), text); }
        else if (!(clipboard.hasPrimaryClip())) {
            //nothing on the clipboard
            Toast.makeText(this, "Nothing on clipboard to paste!", Toast.LENGTH_SHORT).show();
        } else if (!(clipboard.getPrimaryClipDescription().hasMimeType(MIMETYPE_TEXT_PLAIN))) {
            // the clipboard has data but it is not plain text
            Toast.makeText(this, "Item on clipboard is not plain text!", Toast.LENGTH_SHORT).show();
        } else {
            //the clipboard contains plain text.
            ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0);
            pasteData = item.getText().toString();
        }
        //put paste at current cursor selection
        mEditText.getText().insert(mEditText.getSelectionStart(), pasteData);
    }

    public void saveEditorStateToFirebase(String body) { mEditorStateReference.setValue(body); }

    public void saveClipboardToFirebase(String clip) {
        //TODO: add code to limit this to last 5-10 copied text
        mClipboardReference.push().setValue(clip);
    }

    @Override public void clickItemFromFirebase(String text) { paste(text); }
}
