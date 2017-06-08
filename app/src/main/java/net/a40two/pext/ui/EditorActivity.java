package net.a40two.pext.ui;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;

import net.a40two.pext.R;
import net.a40two.pext.ui.fragments.PastebinPastePopup;
import net.a40two.pext.ui.views.AdvancedEditText;

import org.mozilla.universalchardet.UniversalDetector;
import org.parceler.Parcels;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

import org.mozilla.universalchardet.UniversalDetector;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.Toast;

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

    public static final String TAG = EditorActivity.class.getSimpleName();

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        ButterKnife.bind(this);
        mSaveButton.setOnClickListener(this);
        mPastebinButton.setOnClickListener(this);
        mCutButton.setOnClickListener(this);
        mCopyButton.setOnClickListener(this);
        mPasteButton.setOnClickListener(this);
        mBracketsButton.setOnClickListener(this);
        mGithubButton.setOnClickListener(this);
        //to set github button to use icon
        Typeface githubBottonFont = Typeface.createFromAsset( getAssets(), "fontawesome-webfont.ttf" );
        Button button = (Button)findViewById(R.id.github_button);
        button.setTypeface(githubBottonFont);

    }

    @Override public void onClick(View v) {
        if (v == mSaveButton) {
            //save locally
        }
        if (v == mPastebinButton) {
            //open menu for pushing to pastebin
            FragmentManager fm = getSupportFragmentManager();
            PastebinPastePopup ppp = new PastebinPastePopup();
            ppp.show(fm, "Pastebin paste popup");
        }
        if (v == mCutButton) {
            //cut selection from view
            copyOrCutSelection(true);
        }
        if (v == mCopyButton) {
            //copy selection to clipboard
            copyOrCutSelection(false);
        }
        if (v == mPasteButton) {
            //paste clipboard to location
            //leave this here as I only need it once
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
                // Gets the clipboard as text.
                pasteData = item.getText().toString();
            }
            //put paste at current selection
            mEditText.getText().insert(mEditText.getSelectionStart(), pasteData);
        }
        if (v == mBracketsButton) {
            //popup brackets etc menu
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
        String copiedString = mEditText.getText().toString();
        int start = mEditText.getSelectionStart();
        int end = mEditText.getSelectionEnd();
        //do check, since if you start your selection on the right and highlight to the left, your selectionStart index will be greater than your selectionEnd index and things will break
        if (mEditText.getSelectionStart() > mEditText.getSelectionEnd()) {
            start = mEditText.getSelectionEnd();
            end = mEditText.getSelectionStart();
        }

        copiedString = copiedString.substring(start, end);
        android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text From pext", copiedString);
        clipboard.setPrimaryClip(clip);

        if (cut) {
            String allText = mEditText.getText().toString();
            String newText = allText.substring(0, start)+allText.substring(end, allText.length());
            mEditText.setText(newText);
        }
    }

}
