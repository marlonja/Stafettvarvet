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

public class ResultsIndex extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    ListView listViewResults;
    ListView listViewGrafer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results_index);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        listViewResults = (ListView) findViewById(R.id.listViewResults);
        listViewGrafer = (ListView) findViewById(R.id.listViewGrafer);

        bottomNavigationView.getMenu().getItem(0).setChecked(false);
        bottomNavigationView.getMenu().getItem(1).setChecked(false);
        bottomNavigationView.getMenu().getItem(2).setChecked(false);
        bottomNavigationView.getMenu().getItem(3).setChecked(false);
        bottomNavigationView.getMenu().getItem(4).setChecked(true);

        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        setBottomMenuBar();
        setResultsMenu();
        setGraferMenu();
    }

    public void setResultsMenu() {

        ArrayList<String> menuItemsInfo = new ArrayList<>();
        menuItemsInfo.add("Topplistan");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menuItemsInfo);

        listViewResults.setAdapter(arrayAdapter);

        listViewResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                if (position == 0) {
                    Intent intentTopList = new Intent(getApplicationContext(), ResultsTopList.class);
                    startActivity(intentTopList);
                }

            }
        });
    }

    public void setGraferMenu() {

        ArrayList<String> menuItemsInfo = new ArrayList<>();
        menuItemsInfo.add("Placering");
        menuItemsInfo.add("Segmentering");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menuItemsInfo);

        listViewGrafer.setAdapter(arrayAdapter);

        listViewGrafer.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                if (position == 0) {

                }
                if (position == 1) {

                }

            }
        });
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

                                break;
                        }
                        return false;
                    }
                });

    }
}
