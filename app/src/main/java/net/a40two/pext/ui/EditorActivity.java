package net.a40two.pext.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;

import net.a40two.pext.R;
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
//        try {
//            Intent intent = getIntent();
//            File file;
//            Uri uri = Parcels.unwrap(intent.getParcelableExtra("uri"));
//
//            URI newUri = new URI("file://"+uri.toString());
//            Log.d("uri", uri.toString());
//            Log.d("newUri", newUri.toString());
//            file = new File(newUri);
//            if (intent != null) {
//
//                Log.d("uriPath", uri.toString());
//                if (uri != null) {
//
//                    file = new File(uri.getPath());
//                    String text = readTextFile(file);
//                    if (text != null) {
//                        Log.d("in if text not null", text);
//                        mEditText.setText(text);
//                    }
////        mEditText.setText(readTextFile(file));
//                }
//            }
//        }
//        catch(URISyntaxException e) { e.printStackTrace(); }
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

    public static String readTextFile(File file) {
        InputStreamReader reader;
        BufferedReader in;
        StringBuffer text = new StringBuffer();
        int c;
        try {
            reader = new InputStreamReader(new FileInputStream(file),
                    detectCharSet(file.getAbsolutePath()));
            in = new BufferedReader(reader);
            do {
                c = in.read();
                if (c != -1) {
                    text.append((char) c);
                }
            } while (c != -1);
            in.close();
        } catch (IOException e) {
            Log.w(TAG, "Can't read file " + file.getName(), e);
            return null;
        } catch (OutOfMemoryError e) {
            Log.w(TAG, "File is to big to read", e);
            return null;
        }

        // detect end of lines
        String content = text.toString();
        int windows = content.indexOf("\r\n");
        int macos = content.indexOf("\r");

        if (windows != -1) {
//            Settings.END_OF_LINE = EOL_WINDOWS;
            content = content.replaceAll("\r\n", "\n");
        } else {
            if (macos != -1) {
//                Settings.END_OF_LINE = EOL_MAC;
                content = content.replaceAll("\r", "\n");
            } else {
//                Settings.END_OF_LINE = EOL_LINUX;
            }
        }

//        if (BuildConfig.DEBUG) {
//            Log.d(TAG, "Using End of Line : " + Settings.END_OF_LINE);
//        }
        return content;
    }

    public static String detectCharSet(String fileName) throws IOException {
        byte[] buf = new byte[4096];
        FileInputStream fis = new FileInputStream(fileName);

        UniversalDetector detector = new UniversalDetector(null);

        int nread;
        while ((nread = fis.read(buf)) > 0 && !detector.isDone()) {
            detector.handleData(buf, 0, nread);
        }
        detector.dataEnd();

        String encoding = detector.getDetectedCharset();

        detector.reset();
        return encoding;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.overflow_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
