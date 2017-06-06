package net.a40two.pext.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import net.a40two.pext.R;
import net.a40two.pext.ui.fragments.TrendingPastesFragment;
import net.a40two.pext.ui.fragments.UserPastesFragment;

public class PastesActivity extends AppCompatActivity {
    String fragToLoad = "";
    private TrendingPastesFragment trendingFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pastes);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        fragToLoad = getIntent().getStringExtra("fragToLoad");

        if (fragToLoad.equals("")) {
            Log.d("onCreatePastesActivity", "in empty if");

            //go back home, there was a problem
        } else if (fragToLoad.equals("trending")) {
            Log.d("onCreatePastesActivity", "in trending if");
            trendingFrag = new TrendingPastesFragment();

            ft.add(R.id.viewPager, trendingFrag).commit();
//            ft.replace(R.id.viewPager, new TrendingPastesFragment());
//            ft.commit();
        } else if (fragToLoad.equals("ownPastes")) {
            Log.d("onCreatePastesActivity", "in own if");

            ft.replace(R.id.viewPager, new UserPastesFragment());
            ft.commit();
        }

    }

}
