package com.parse.stafettvarvet15;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class InfoStartTid extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    TextView textViewTimer;
    CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_start_tid);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        textViewTimer = (TextView) findViewById(R.id.textViewTimer);

        bottomNavigationView.getMenu().getItem(0).setChecked(true);
        bottomNavigationView.getMenu().getItem(1).setChecked(false);
        bottomNavigationView.getMenu().getItem(2).setChecked(false);
        bottomNavigationView.getMenu().getItem(3).setChecked(false);
        bottomNavigationView.getMenu().getItem(4).setChecked(false);

        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        setBottomMenuBar();
        startTimer();
    }

    public void backButton(View view) {
        finish();
    }

    private void startTimer() {

        int hours = 504;
        long totalMilliSeconds = hours * 3600000;

        countDownTimer = new CountDownTimer(totalMilliSeconds, 1000) {

            public void onTick(long millisUntilFinished) {
                updateTimer((int) millisUntilFinished / 1000);
            }

            public void onFinish() {
                textViewTimer.setText("done!");
            }
        }.start();

    }

    private void updateTimer(int secondsLeft) {

        int hours = secondsLeft / 3600;
        int days = hours / 24;
        int hoursCompressed = hours - days * 24;
        int minutes = secondsLeft / 60;
        int minutesCompressed = minutes - hours * 60;
        int seconds = secondsLeft - minutes * 60;


        String secondString = Integer.toString(seconds);
        String hoursString = Integer.toString(hoursCompressed);
        String daysString = Integer.toString(days);

        if(seconds <= 9) {
            secondString = "0" + secondString;
        }
        if(hoursCompressed <= 9) {
            hoursString = "0" + hoursString;
        }
        if(days <= 9) {
            daysString = "0" + daysString;
        }

        textViewTimer.setText(daysString + ":" + hoursString + ":" + Integer.toString(minutesCompressed) + ":" + secondString);
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
