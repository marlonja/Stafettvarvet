package com.parse.stafettvarvet15;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class InfoForetagsbyn extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_foretagsbyn);

        mWebView = (WebView) findViewById(R.id.webView);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.loadUrl("file:///android_asset/foretagsbyn.html");


        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        setBottomMenuBar();
    }

    public void backButton(View view) {
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0,0);
    }

    private void setBottomMenuBar() {

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.info:
                                Intent intentMain = new Intent(getApplicationContext(), InfoIndex.class);
                                startActivity(intentMain);
                                break;

                            case R.id.race:
                                Intent intentLoppet = new Intent(getApplicationContext(), RaceIndex.class);
                                startActivity(intentLoppet);
                                break;
                            case R.id.team:
                                Intent intentTeam = new Intent(getApplicationContext(), TeamIndex.class);
                                startActivity(intentTeam);
                                break;
                            case R.id.map:
                                Intent intentMap= new Intent(getApplicationContext(), InfoArea.class);
                                startActivity(intentMap);
                                break;
                            case R.id.result:
                                Intent intentResults= new Intent(getApplicationContext(), ResultsIndex.class);
                                startActivity(intentResults);
                                break;
                        }
                        return false;
                    }
                });

    }
}
