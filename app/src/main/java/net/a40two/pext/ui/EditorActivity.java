package net.a40two.pext.ui;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;

import net.a40two.pext.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditorActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.save_button) Button mSaveButton;
    @BindView(R.id.pastebin_button) Button mPastebinButton;
    @BindView(R.id.cut_button) Button mCutButton;
    @BindView(R.id.copy_button) Button mCopyButton;
    @BindView(R.id.paste_button) Button mPasteButton;
    @BindView(R.id.brackets_button) Button mBracketsButton;
    @BindView(R.id.github_button) Button mGithubButton;

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
        }
        if (v == mCutButton) {
            //cut selection from view
        }
        if (v == mCopyButton) {
            //copy selection to clipboard
        }
        if (v == mPasteButton) {
            //paste clipboard to location
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
}
