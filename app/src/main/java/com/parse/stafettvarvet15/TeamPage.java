package com.parse.stafettvarvet15;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import static com.parse.stafettvarvet15.InfoIndex.companiesFromShared;
import static com.parse.stafettvarvet15.InfoIndex.teamsFromShared;

public class TeamPage extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    TextView textViewTeam;
    TextView textViewCompany;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_page);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        textViewTeam = (TextView) findViewById(R.id.textViewTeam);
        textViewCompany = (TextView) findViewById(R.id.textViewCompany);

        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0);

        Log.i("info", String.valueOf(position));

        textViewTeam.setText(teamsFromShared.get(position));
        textViewCompany.setText(companiesFromShared.get(position));

        bottomNavigationView.getMenu().getItem(0).setChecked(false);
        bottomNavigationView.getMenu().getItem(1).setChecked(false);
        bottomNavigationView.getMenu().getItem(2).setChecked(false);
        bottomNavigationView.getMenu().getItem(3).setChecked(true);
        bottomNavigationView.getMenu().getItem(4).setChecked(false);

        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        setBottomMenuBar();
    }

    public void backButton(View view) {
        finish();
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
