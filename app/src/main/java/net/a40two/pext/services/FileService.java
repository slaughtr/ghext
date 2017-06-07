package net.a40two.pext.services;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

public class FileService {
    private static final int READ_REQUEST_CODE = 42;
    private static final int EDIT_REQUEST_CODE = 44;


    /**
     * Fires an intent to spin up the "file chooser" UI and select a text file.
     */
    public void performFileSearch() {
        Log.d("fileservice", "in performFileSearch");
        // ACTION_OPEN_DOCUMENT is the intent to choose a file via the system's file
        // browser.
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        // Filter to only show results that can be "opened", such as a
        // file (as opposed to a list of contacts or timezones)
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        // Filter to show only text files, using the image MIME data type.
        intent.setType("text/*");

        startActivityForResult(intent, READ_REQUEST_CODE);
    }

   void startActivityForResult(Intent intent, int code) {} //???

     public void onActivityResult(int requestCode, int resultCode,
                                           Intent resultData) {
        // The ACTION_OPEN_DOCUMENT intent was sent with the request code
        // READ_REQUEST_CODE. If the request code seen here doesn't match, it's the
        // response to some other intent, and the code below shouldn't run at all.
        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // The document selected by the user won't be returned in the intent.
            // Instead, a URI to that document will be contained in the return intent
            // provided to this method as a parameter.
            // Pull that URI using resultData.getData().
            Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();
                Log.i("onActivityResult", "Uri: " + uri.toString());
                //this is where I'll call some kind of constructor to pass the URI to the editor activity
            }
        }
    }
}
