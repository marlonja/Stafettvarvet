package com.parse.stafettvarvet15;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class RaceIndex extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    ListView listViewRaceInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_race_index);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        listViewRaceInfo = (ListView) findViewById(R.id.listViewRaceInfo);

        bottomNavigationView.getMenu().getItem(0).setChecked(false);
        bottomNavigationView.getMenu().getItem(1).setChecked(true);
        bottomNavigationView.getMenu().getItem(2).setChecked(false);
        bottomNavigationView.getMenu().getItem(3).setChecked(false);
        bottomNavigationView.getMenu().getItem(4).setChecked(false);

        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        setBottomMenuBar();
        ArrayList<String> menuItemsRace = new ArrayList<>();
        menuItemsRace.add("Bansträckningen");
        menuItemsRace.add("Starttider");
        menuItemsRace.add("Växling och målgång");


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menuItemsRace);

        listViewRaceInfo.setAdapter(arrayAdapter);

        listViewRaceInfo.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                if (position == 0) {

                }
                if (position == 1) {
                    Intent intentStartTime = new Intent(getApplicationContext(), InfoStartTid.class);
                    startActivity(intentStartTime);
                }
                if (position == 2) {
                    Intent intentExchange = new Intent(getApplicationContext(), RaceExchange.class);
                    startActivity(intentExchange);

                }

            }
        });

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
