package net.a40two.pext.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import net.a40two.pext.Constants;
import net.a40two.pext.R;
import net.a40two.pext.ui.fragments.PastebinLoginPopup;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private String[] mMenuItems = new String[] {"My Pastes", "Trending Pastes", "Help", "About", "Settings"};
    //things for nav drawer
    private ListView mDrawerList;
    private CharSequence mTitle;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;

    private SharedPreferences mSharedPreferences;
    private String mUserApiKeyFromPrefs;
    private String mUserNameFromPrefs;

    private FirebaseAuth mAuth;

    @BindView(R.id.editor_jump_button) Button mJumpToEditorButton;
    @BindView(R.id.pb_login_button) Button mPastebinLoginButton;
    @BindView(R.id.username_text_view) TextView mUsernameTextView;
    @BindView(R.id.pext_text_view) TextView mPextTextView;

    @Override protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get firebase auth
        mAuth = FirebaseAuth.getInstance();

        ButterKnife.bind(this);

        Typeface andvari = Typeface.createFromAsset(getAssets(), "andvari.ttf");
        mPextTextView.setTypeface(andvari);
        mUsernameTextView.setTypeface(andvari);
        mJumpToEditorButton.setTypeface(andvari);
        mPastebinLoginButton.setTypeface(andvari);
        //attempt to get the user's API key from their shared preferences,
        //if it's there, mark user as logged in and set API key in constants
        //also get user name from sharedprefs for setting textview
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mUserApiKeyFromPrefs = mSharedPreferences.getString(Constants.PREFERENCES_USER_API_KEY, null);
        mUserNameFromPrefs = mSharedPreferences.getString(Constants.PREFERENCES_USER_NAME_KEY, null);

        if (mUserApiKeyFromPrefs != null) {
            Constants.LOGGED_IN = true;
            Constants.USER_API_KEY = mUserApiKeyFromPrefs;
            Constants.USER_NAME = mUserNameFromPrefs;
            mUsernameTextView.setText("Welcome back, "+mUserNameFromPrefs);
            mPastebinLoginButton.setVisibility(GONE);
            //TODO: make the toast not show every time you return to this activity, also move it on the screen?
            Toast.makeText(this, "You're already logged in!\n You only need to login again if you're having issues.", Toast.LENGTH_SHORT).show();
        }

        mDrawerList = (ListView) findViewById(R.id.main_drawer);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        // Set the adapter for the list view (drawer nav)
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mMenuItems));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        mJumpToEditorButton.setOnClickListener(this);
        mPastebinLoginButton.setOnClickListener(this);

        mTitle = mDrawerTitle = getTitle();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open, R.string.drawer_close) {
            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle("pext");
            }
            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("menu");
            }
        };
        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.addDrawerListener(mDrawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null)
        FirebaseUser currentUser = mAuth.getCurrentUser();
        signInAnonymously();
    }

    //used to sign in anonymously to firebase auth,
    // so that user has access to database without having to create an account for it
    private void signInAnonymously() {
        mAuth.signInAnonymously()
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(MainActivity.this, "Authentication to database failed. If problem persists, please contact the developer.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        //for the overflow menu
        int id = item.getItemId();
        if (id == R.id.action_login) {
            FragmentManager fm = getSupportFragmentManager();
            PastebinLoginPopup plp = new PastebinLoginPopup();
            plp.show(fm, "Pastebin login popup");
            //TODO: add listener to loginPopup/main activity to change mUsernameTextView when logged in
        } else if (id == R.id.action_logout) {
            Constants.LOGGED_IN = false;
            Constants.CURRENT_USER = null;
            Toast.makeText(MainActivity.this, "You are now logged out!", Toast.LENGTH_SHORT).show();
            mUsernameTextView.setText("You are not logged in");
            mPastebinLoginButton.setVisibility(View.VISIBLE);
        } else if (id == R.id.action_settings) {
            //open settings
            //TODO: make a settings activity
        }
        return super.onOptionsItemSelected(item);
    }

    @Override public void onClick(View v) {
        if (v == mJumpToEditorButton) {
            Intent intent = new Intent(MainActivity.this, EditorActivity.class);
            startActivity(intent);
        }
        if (v == mPastebinLoginButton) {
            FragmentManager fm = getSupportFragmentManager();
            PastebinLoginPopup plp = new PastebinLoginPopup();
            plp.show(fm, "Pastebin login popup");
        }
    }

    public class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        { selectItem(position); }
    }

    private void selectItem(int position) {
        // Highlight the selected item, update the title, and close the drawer
        mDrawerList.setItemChecked(position, true);
        setTitle(mMenuItems[position]);
        mDrawerLayout.closeDrawer(mDrawerList);

        if (mMenuItems[position] == "My Pastes") {
            if (Constants.LOGGED_IN) {
                Intent intent = new Intent(MainActivity.this, PastesActivity.class);
                intent.putExtra("fragToLoad", "ownPastes");
                startActivity(intent);
            } else {
                Toast.makeText(MainActivity.this, "You must be logged in to do this.", Toast.LENGTH_LONG).show();
            }
        }
        if (mMenuItems[position] == "Trending Pastes") {
            Intent intent = new Intent(MainActivity.this, PastesActivity.class);
            intent.putExtra("fragToLoad", "trending");
            startActivity(intent);
        }
        if (mMenuItems[position] == "About") {
            startActivity(new Intent(MainActivity.this, AboutActivity.class));
        }
        if (mMenuItems[position] == "Help") {
            startActivity(new Intent(MainActivity.this, HelpActivity.class));
        }
        if (mMenuItems[position] == "Settings") {
            startActivity(new Intent(MainActivity.this, SettingsActivity.class));
        }
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.overflow_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
