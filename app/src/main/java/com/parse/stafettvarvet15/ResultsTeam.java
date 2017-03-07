package com.parse.stafettvarvet15;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class ResultsTeam extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    TextView textViewTeam;
    TextView textViewCompany;
    TextView textViewTimer;
    String team;
    String company;
    String time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results_team);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        textViewTeam = (TextView) findViewById(R.id.textViewTeam);
        textViewCompany = (TextView) findViewById(R.id.textViewCompany);
        textViewTimer = (TextView) findViewById(R.id.textViewTimer);

        Intent intent = getIntent();
        team = intent.getStringExtra("team");
        company = intent.getStringExtra("company");
        time = intent.getStringExtra("time");

        textViewTeam.setText(team);
        textViewCompany.setText(company);
        textViewTimer.setText(time);

        bottomNavigationView.getMenu().getItem(0).setChecked(false);
        bottomNavigationView.getMenu().getItem(1).setChecked(false);
        bottomNavigationView.getMenu().getItem(2).setChecked(false);
        bottomNavigationView.getMenu().getItem(3).setChecked(false);
        bottomNavigationView.getMenu().getItem(4).setChecked(true);

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
                            case R.id.map:
                                Intent intentMap= new Intent(getApplicationContext(), InfoArea.class);
                                startActivity(intentMap);
                                break;
                            case R.id.team:
                                Intent intentTeam = new Intent(getApplicationContext(), TeamIndex.class);
                                startActivity(intentTeam);
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
