package com.parse.stafettvarvet15;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class InfoIndex extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    ListView listView;
    static SharedPreferences sharedPreferences;
    ArrayList<String> teams;
    ArrayList<String> companies;
    ArrayList<String> genders;
    static ArrayList<String> teamsFromShared;
    static ArrayList<String> gendersFromShared;
    static ArrayList<String> companiesFromShared;
    static ArrayList<Integer> positions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_index);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        listView = (ListView) findViewById(R.id.listView);

        bottomNavigationView.getMenu().getItem(0).setChecked(true);
        bottomNavigationView.getMenu().getItem(1).setChecked(false);
        bottomNavigationView.getMenu().getItem(2).setChecked(false);
        bottomNavigationView.getMenu().getItem(3).setChecked(false);
        bottomNavigationView.getMenu().getItem(4).setChecked(false);

        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        sharedPreferences = this.getSharedPreferences("com.parse.stafettvarvet12", Context.MODE_PRIVATE);

        getDataFromShared();
        setBottomMenuBar();
        setMenuBar();

    }

    public void setMenuBar() {

        ArrayList<String> menuItemsInfo = new ArrayList<>();
        menuItemsInfo.add("När startar vi?");
        menuItemsInfo.add("Nummerlapp");
        menuItemsInfo.add("Kollektivtrafik");
        menuItemsInfo.add("På området");
        menuItemsInfo.add("Våra partners");
        menuItemsInfo.add("Företagsbyn");
        menuItemsInfo.add("Bra att veta");

        CustomAdapterMain adapter = new CustomAdapterMain(this, menuItemsInfo);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                if (position == 0) {
                    Intent intentStartTime = new Intent(getApplicationContext(), InfoStartTid.class);
                    startActivity(intentStartTime);
                }

                if (position == 1) {
                    Intent intentTicket = new Intent(getApplicationContext(), InfoTicket.class);
                    startActivity(intentTicket);
                }
                if (position == 2) {
                    Intent intentKollektiv = new Intent(getApplicationContext(), InfoKollektivtrafik.class);
                    startActivity(intentKollektiv);
                }
                if (position == 3) {
                    Intent intentAreaInfo = new Intent(getApplicationContext(), InfoArea.class);
                    startActivity(intentAreaInfo);
                }
                if (position == 4) {
                    Intent intentPartners = new Intent(getApplicationContext(), InfoPartners.class);
                    startActivity(intentPartners);
                }
                if (position == 5) {
                    Intent intentForetagsbyn = new Intent(getApplicationContext(), InfoForetagsbyn.class);
                    startActivity(intentForetagsbyn);
                }
                if (position == 6) {
                    Intent intentOvrigt = new Intent(getApplicationContext(), InfoOvrigt.class);
                    startActivity(intentOvrigt);
                }

            }
        });
    }

    public void getDataFromShared() {

        teamsFromShared = new ArrayList<>();
        gendersFromShared = new ArrayList<>();
        positions = new ArrayList<>();

        try {

            teamsFromShared = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("teams", ObjectSerializer.serialize(new ArrayList<String>())));
            gendersFromShared = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("genders", ObjectSerializer.serialize(new ArrayList<String>())));
            companiesFromShared = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("companies", ObjectSerializer.serialize(new ArrayList<String>())));

        } catch (IOException e) {

            e.printStackTrace();

        }

        if (teamsFromShared.size() < 25 || gendersFromShared.isEmpty() || companiesFromShared.isEmpty()) {

            DownloadTask task = new DownloadTask();

            try {

                task.execute("http://portal.mikatiming.de/ah/rest/appapi/meetinginfo/meeting/9999991A136FC7000000049D/participations?apiKey=hiq-5a85a8ea&pageStart=0&pageMaxCount=25&idMeeting=9999991A136FC7000000049D&noCache=true");


            } catch (Exception e) {

                e.printStackTrace();

            }

        }

        try {

            positions = (ArrayList<Integer>) ObjectSerializer.deserialize(sharedPreferences.getString("positions", ObjectSerializer.serialize(new ArrayList<String>())));

        } catch (IOException e) {

            e.printStackTrace();

        }

        if (positions.isEmpty()) {

            for (int i = 0; i < teamsFromShared.size(); i++) {

                positions.add(0);

            }

            try {

                sharedPreferences.edit().putString("positions", ObjectSerializer.serialize(positions)).apply();

            } catch (IOException e) {

                e.printStackTrace();
            }
        }

    }

    public void processData(String result) {

        teams = new ArrayList<>();
        genders = new ArrayList<>();
        companies = new ArrayList<>();
        String team;
        String gender;
        String company;

        try {

            JSONObject jsonObject = new JSONObject(result);

            String results = jsonObject.getString("participations");

            JSONArray arr = new JSONArray(results);

            for (int i = 0; i < arr.length(); i++) {

                JSONObject jsonPart = arr.getJSONObject(i);

                try {

                    gender = jsonPart.getString("sex");
                    genders.add(gender);

                    team = jsonPart.getString("club");
                    teams.add(team);


                } catch (Exception e) {
                    Log.i("info", "addar i catchen");
                    teams.add("-No teamname data-");

                }

                try {

                    company = jsonPart.getString("team");
                    companies.add(company);




                } catch (Exception e) {
                    Log.i("info", "addar companies i catchen");
                    companies.add("-No company data-");

                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {

            sharedPreferences.edit().putString("teams", ObjectSerializer.serialize(teams)).apply();
            sharedPreferences.edit().putString("genders", ObjectSerializer.serialize(genders)).apply();
            sharedPreferences.edit().putString("companies", ObjectSerializer.serialize(companies)).apply();


        } catch (IOException e) {

            e.printStackTrace();
        }

        try {

            teamsFromShared = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("teams", ObjectSerializer.serialize(new ArrayList<String>())));
            gendersFromShared = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("genders", ObjectSerializer.serialize(new ArrayList<String>())));
            companiesFromShared = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("companies", ObjectSerializer.serialize(new ArrayList<String>())));

        } catch (IOException e) {

            e.printStackTrace();

        }

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

    public class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {

            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try {
                url = new URL(urls[0]);

                urlConnection = (HttpURLConnection) url.openConnection();

                InputStream in = urlConnection.getInputStream();

                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();

                while (data != -1) {

                    char current = (char) data;

                    result += current;

                    data = reader.read();

                }

                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            processData(s);
        }
    }
}
