package net.a40two.pext.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;

import net.a40two.pext.R;
import net.a40two.pext.models.Paste;
import net.a40two.pext.ui.fragments.TrendingPastesFragment;
import net.a40two.pext.ui.fragments.UserPastesFragment;

import java.util.ArrayList;

import butterknife.BindView;

public class PastesActivity extends AppCompatActivity {

    String fragToLoad = "";
    public ArrayList<Paste> mPastes = new ArrayList<>();
    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pastes);

        fragToLoad = getIntent().getStringExtra("fragToLoad");

        if (fragToLoad.equals("")) {
            //TODO: send user back home, toast that there was a problem
        } else if (fragToLoad.equals("trending")) {
            loadTrendingFragment();
            setTitle("Today's Trending Pastes");
        } else if (fragToLoad.equals("ownPastes")) {
            loadOwnFragment();
            setTitle("Your Pastes");

        }
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.overflow_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void loadTrendingFragment() {
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().setTransition(FragmentTransaction.TRANSIT_ENTER_MASK).replace(R.id.fragmentHolder, TrendingPastesFragment.newInstance(mPastes)).commit();
    }

    private void loadOwnFragment() {
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.fragmentHolder, UserPastesFragment.newInstance(mPastes)).commit();
    }
}
