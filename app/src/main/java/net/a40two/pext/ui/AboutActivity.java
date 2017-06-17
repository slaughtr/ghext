package net.a40two.pext.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import net.a40two.pext.Constants;
import net.a40two.pext.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.my_github_button) Button myGithubButton;
    @BindView(R.id.bitcoin_button) Button myBitcoinButton;
    @BindView(R.id.linkedin_button) Button myLinkedinButton;

    //ads
    private AdView mAdView;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        //initialize ads
        MobileAds.initialize(getApplicationContext(),
                "ca-app-pub-3940256099942544~3347511713");
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        ButterKnife.bind(this);

        myGithubButton.setOnClickListener(this);
        myBitcoinButton.setOnClickListener(this);
        myLinkedinButton.setOnClickListener(this);
    }

    @Override public void onClick(View v) {
        if (v == myGithubButton) {
            Intent webIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(Constants.MY_GITHUB));
            startActivity(webIntent);
        }
        if (v == myBitcoinButton) {
            //TODO: check if user has a bitcoin app
            //use PackageManager and resolveActivity() or queryIntentActivities() to see if there is anything matching your Intent
            //if no bitcoin app, copy address to clipboard and ask to send to play store with a search of "bitcoin"?
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager)
                    getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData
                    .newPlainText("Your OTP", Constants.MY_BITCOIN);
            clipboard.setPrimaryClip(clip);
            Toast.makeText(getApplicationContext(), "Bitcoin address copied to clipboard", Toast.LENGTH_SHORT).show();
        }
        if (v == myLinkedinButton) {
            Intent webIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(Constants.MY_LINKEDIN));
            startActivity(webIntent);
        }
    }
}
