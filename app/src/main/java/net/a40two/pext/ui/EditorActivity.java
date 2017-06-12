package net.a40two.pext.ui;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;

import net.a40two.pext.Constants;
import net.a40two.pext.R;
import net.a40two.pext.ui.fragments.PastebinPastePopup;
import net.a40two.pext.ui.views.AdvancedEditText;

import org.parceler.Parcels;

import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.ClipDescription.MIMETYPE_TEXT_PLAIN;

public class EditorActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.save_button) Button mSaveButton;
    @BindView(R.id.pastebin_button) Button mPastebinButton;
    @BindView(R.id.cut_button) Button mCutButton;
    @BindView(R.id.copy_button) Button mCopyButton;
    @BindView(R.id.paste_button) Button mPasteButton;
    @BindView(R.id.brackets_button) Button mBracketsButton;
    @BindView(R.id.github_button) Button mGithubButton;
    @BindView(R.id.editorAdvancedTextView) AdvancedEditText mEditText;

    private DatabaseReference mEditorStateReference;


    public static final String TAG = EditorActivity.class.getSimpleName();

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);


        mEditorStateReference = FirebaseDatabase
                .getInstance()
                .getReference()
                .child(Constants.FIREBASE_CHILD_SAVED_EDITOR_STATE);

        ButterKnife.bind(this);
        mSaveButton.setOnClickListener(this);
        mPastebinButton.setOnClickListener(this);
        mCutButton.setOnClickListener(this);
        mCopyButton.setOnClickListener(this);
        mPasteButton.setOnClickListener(this);
        mBracketsButton.setOnClickListener(this);
        mGithubButton.setOnClickListener(this);

        //set github button to use icon
        Typeface githubBottonFont = Typeface.createFromAsset( getAssets(), "fontawesome-webfont.ttf" );
        mGithubButton.setTypeface(githubBottonFont);
    }

    @Override public void onStart() {
        super.onStart();
        //get intent with the body of the paste you want to edit,
        // if it's not null (might be, if  something went wrong),
        // then set the AdvancedEditText text to that.
        Intent intent = getIntent();
        String editPasteBody = Parcels.unwrap(intent.getParcelableExtra("editPasteBody"));

        if (editPasteBody != null && editPasteBody.length() > 0) {
            mEditText.setText(editPasteBody);
        } else {
            //grab last thing from editor from firebase
        }
    }

    @Override public void onStop() {
        super.onStop();
        //TODO: add code to write current text in editor to firebase on activity exit
        saveEditorStateToFirebase(mEditText.getText().toString());
    }

    @Override public void onClick(View v) {
        if (v == mSaveButton) {
            //save locally
            //maybe this will work one day
        }
        if (v == mPastebinButton) {
            //open popup fragment for pushing to pastebin
            Bundle args = new Bundle();
            //put text to bundle so that the paste popup has access to it for submitting
            args.putString("body", mEditText.getText().toString());
            FragmentManager fm = getSupportFragmentManager();
            PastebinPastePopup ppp = new PastebinPastePopup();
            ppp.setArguments(args);
            ppp.show(fm, "Pastebin paste popup");
        }
        if (v == mCutButton) { copyOrCutSelection(true); }
        if (v == mCopyButton) { copyOrCutSelection(false); }
        if (v == mPasteButton) {
            //paste clipboard to location TODO: pasting over selection deletes selection?
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            String pasteData = "";
            if (!(clipboard.hasPrimaryClip())) {
                //nothing on the clipboard
                Toast.makeText(this, "Nothing on clipboard to paste!", Toast.LENGTH_SHORT).show();
            } else if (!(clipboard.getPrimaryClipDescription().hasMimeType(MIMETYPE_TEXT_PLAIN))) {
                // the clipboard has data but it is not plain text
                Toast.makeText(this, "Item on clipboard is not plain text!", Toast.LENGTH_SHORT).show();
            } else {
                //the clipboard contains plain text.
                ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0);
                // Gets the clipboard as a string.
                pasteData = item.getText().toString();
            }
            //put paste at current cursor selection
            mEditText.getText().insert(mEditText.getSelectionStart(), pasteData);
        }
        if (v == mBracketsButton) {
            //TODO: add code to popup brackets etc menu
        }
        if (v == mGithubButton) {
            //maybe should have deleted this button. Might find use for it?
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.overflow_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void copyOrCutSelection(boolean cut) {
        //TODO: add code to save last 5-10 copied text to firebase

        String copiedString = mEditText.getText().toString();
        int start = mEditText.getSelectionStart();
        int end = mEditText.getSelectionEnd();
        //do check, since if you start your selection on the right and highlight to the left,
        // your selectionStart index will be greater than your selectionEnd index and things will break
        if (mEditText.getSelectionStart() > mEditText.getSelectionEnd()) {
            start = mEditText.getSelectionEnd();
            end = mEditText.getSelectionStart();
        }
        //do the clipboard dance
        copiedString = copiedString.substring(start, end);
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

    public void saveEditorStateToFirebase(String body) {
        mEditorStateReference.setValue(body);
    }

}
