package com.tapfortap.sampleapp;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.tapfortap.sdk.Banner;
import com.tapfortap.sdk.Interstitial;
import com.tapfortap.sdk.TapForTap;

import java.net.MalformedURLException;
import java.net.URL;


public class MainActivity extends ActionBarActivity {

    private Interstitial.InterstitialListener interstitialListener;
    URL rewardIconUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TapForTap.setEnvironment("production");
        TapForTap.initialize(this, "3d323e6d58c83e06dba2547ec54f8afc");

        interstitialListener = new Interstitial.InterstitialListener() {
            @Override
            public void interstitialDidReceiveAd(Interstitial interstitial) {
                Log.i("MainActivity", "interstitialDidReceiveAd");
                interstitial.show();
            }

            @Override
            public void interstitialDidFail(Interstitial interstitial, String reason, Throwable throwable) {
                Log.i("MainActivity", "interstitialDidFail because: " + reason);
            }

            @Override
            public void interstitialDidShow(Interstitial interstitial) {
                Log.i("MainActivity", "interstitialDidShow");
            }

            @Override
            public void interstitialWasTapped(Interstitial interstitial) {
                Log.i("MainActivity", "interstitialWasTapped");
            }

            @Override
            public void interstitialWasDismissed(Interstitial interstitial) {
                Log.i("MainActivity", "interstitialWasDismissed");
            }

            @Override
            public void interstitialAdWasRewarded(Interstitial interstitial) {
                Log.i("MainActivity", "interstitialAdWasRewarded");
            }
        };

        try {
            rewardIconUrl = new URL("http://files.softicons.com/download/game-icons/brain-games-icons-by-quizanswers/png/64x64/Board-Games-red.png");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void showBreakAd(View v) {
        Interstitial.loadBreakInterstitial(this, interstitialListener);
    }

    public void showAchievementAd(View v) {
        Interstitial.loadAchievementInterstitial(this, "description", "rewardDescription", rewardIconUrl, interstitialListener);
    }

    public void showRescueAd(View v) {
        Interstitial.loadRescueInterstitial(this, "title", "brandingText", "enticementText", "rewardDescription", rewardIconUrl, "optInButtonText", interstitialListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
