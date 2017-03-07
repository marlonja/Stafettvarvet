package com.parse.stafettvarvet15;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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

import static com.parse.stafettvarvet15.InfoIndex.sharedPreferences;

public class ResultsTopList extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    Button buttonAll;
    Button buttonMen;
    Button buttonWomen;
    ListView listViewResults;
    ArrayList<ModelToplist> listTeams;
    ArrayList<String> topTeamsFromShared;
    ArrayList<String> topGendersFromShared;
    ArrayList<String> topCompaniesFromShared;
    ArrayList<String> topRanksFromShared;
    ArrayList<String> topTimesFromShared;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results_top_list);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        buttonAll = (Button) findViewById(R.id.buttonAll);
        buttonMen = (Button) findViewById(R.id.buttonMen);
        buttonWomen = (Button) findViewById(R.id.buttonWomen);
        listViewResults = (ListView) findViewById(R.id.listViewResults);

        bottomNavigationView.getMenu().getItem(0).setChecked(false);
        bottomNavigationView.getMenu().getItem(1).setChecked(false);
        bottomNavigationView.getMenu().getItem(2).setChecked(false);
        bottomNavigationView.getMenu().getItem(3).setChecked(false);
        bottomNavigationView.getMenu().getItem(4).setChecked(true);

        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        setBottomMenuBar();
        getResults();
    }

    public void showAll(View view) {

        buttonAll.setBackgroundColor(Color.parseColor("#e10832"));
        buttonAll.setTextColor(Color.parseColor("#ffffff"));
        buttonMen.setBackgroundResource(R.drawable.button_border);
        buttonMen.setTextColor(Color.parseColor("#e10832"));
        buttonWomen.setBackgroundResource(R.drawable.button_border);
        buttonWomen.setTextColor(Color.parseColor("#e10832"));

        addToListTeams();

    }

    public void showMen(View view) {

        listTeams = new ArrayList<>();
        int rank = 1;
        String rankWithZeroes = "";

        buttonAll.setBackgroundResource(R.drawable.button_border);
        buttonAll.setTextColor(Color.parseColor("#e10832"));
        buttonMen.setBackgroundColor(Color.parseColor("#e10832"));
        buttonMen.setTextColor(Color.parseColor("#ffffff"));
        buttonWomen.setBackgroundResource(R.drawable.button_border);
        buttonWomen.setTextColor(Color.parseColor("#e10832"));


        for (int i = 0; i < topGendersFromShared.size(); i++) {

            if (topGendersFromShared.get(i).equals("M")) {

                if (rank < 10) {
                    rankWithZeroes = "00" + rank;
                } else if (rank >= 10 && rank < 100) {
                    rankWithZeroes = "0" + rank;
                }

                ModelToplist model = new ModelToplist(rankWithZeroes, topTeamsFromShared.get(i), topTimesFromShared.get(i), topCompaniesFromShared.get(i));
                listTeams.add(model);
                rank++;

            }

        }

        printTeams(listTeams);

    }

    public void showWomen(View view) {

        listTeams = new ArrayList<>();
        int rank = 1;
        String rankWithZeroes = "";

        buttonAll.setBackgroundResource(R.drawable.button_border);
        buttonAll.setTextColor(Color.parseColor("#e10832"));
        buttonMen.setBackgroundResource(R.drawable.button_border);
        buttonMen.setTextColor(Color.parseColor("#e10832"));
        buttonWomen.setBackgroundColor(Color.parseColor("#e10832"));
        buttonWomen.setTextColor(Color.parseColor("#ffffff"));

        for (int i = 0; i < topGendersFromShared.size(); i++) {

            if (topGendersFromShared.get(i).equals("W")) {

                if (rank < 10) {
                    rankWithZeroes = "00" + rank;
                } else if (rank >= 10 && rank < 100) {
                    rankWithZeroes = "0" + rank;
                }

                ModelToplist model = new ModelToplist(rankWithZeroes, topTeamsFromShared.get(i), topTimesFromShared.get(i), topCompaniesFromShared.get(i));
                listTeams.add(model);
                rank++;

            }

        }

        printTeams(listTeams);

    }

    public void getResults() {

        topRanksFromShared = new ArrayList<>();
        topTeamsFromShared = new ArrayList<>();
        topGendersFromShared = new ArrayList<>();
        topTimesFromShared = new ArrayList<>();
        topCompaniesFromShared = new ArrayList<>();

        try {

            topRanksFromShared = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("topranks", ObjectSerializer.serialize(new ArrayList<String>())));
            topTeamsFromShared = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("topteams", ObjectSerializer.serialize(new ArrayList<String>())));
            topGendersFromShared = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("topgenders", ObjectSerializer.serialize(new ArrayList<String>())));
            topTimesFromShared = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("toptimes", ObjectSerializer.serialize(new ArrayList<String>())));
            topCompaniesFromShared = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("topcompanies", ObjectSerializer.serialize(new ArrayList<String>())));

        } catch (IOException e) {

            e.printStackTrace();

        }

        if (topRanksFromShared.isEmpty()) {

            DownloadResults task = new DownloadResults();
            Log.i("info", "will dl results");

            try {

                task.execute("http://portal.mikatiming.de/ah/rest/appapi/meetinginfo/race/9999991A136FC7000000044D/results?apiKey=hiq-5a85a8ea&pageStart=0&pageMaxCount=25&idMeeting=9999991A136FC7000000049D&noCache=true");


            } catch (Exception e) {

                e.printStackTrace();

            }

        }else {
            Log.i("info", "results in shared");
            addToListTeams();
        }
    }

    public void processDataToShared(String result) {
        topRanksFromShared = new ArrayList<>();
        topTeamsFromShared = new ArrayList<>();
        topGendersFromShared = new ArrayList<>();
        topTimesFromShared = new ArrayList<>();
        topCompaniesFromShared = new ArrayList<>();
        String rank;
        String team;
        String gender;
        String time;
        String company;

        try {

            JSONObject jsonObject = new JSONObject(result);

            String results = jsonObject.getString("results");

            JSONArray arr = new JSONArray(results);

            for (int i = 0; i < arr.length(); i++) {

                JSONObject jsonPart = arr.getJSONObject(i);

                try {

                    rank = jsonPart.getString("placeNoSex");
                    String rankWithZeroes = "";
                    int rankToInt = Integer.parseInt(rank);
                    if (rankToInt < 10) {
                        rankWithZeroes = "00" + rank;
                    } else if (rankToInt >= 10 && rankToInt < 100) {
                        rankWithZeroes = "0" + rank;
                    }

                    topRanksFromShared.add(rankWithZeroes);

                    team = jsonPart.getString("club");
                    topTeamsFromShared.add(team);

                    gender = jsonPart.getString("sex");
                    topGendersFromShared.add(gender);

                    time = jsonPart.getString("finishTimeNet");
                    topTimesFromShared.add(time);

                    company = jsonPart.getString("team");
                    topCompaniesFromShared.add(company);


                } catch (Exception e) {
                    topCompaniesFromShared.add("-No company data-");

                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {

            sharedPreferences.edit().putString("topranks", ObjectSerializer.serialize(topRanksFromShared)).apply();
            sharedPreferences.edit().putString("topteams", ObjectSerializer.serialize(topTeamsFromShared)).apply();
            sharedPreferences.edit().putString("topgenders", ObjectSerializer.serialize(topGendersFromShared)).apply();
            sharedPreferences.edit().putString("toptimes", ObjectSerializer.serialize(topTimesFromShared)).apply();
            sharedPreferences.edit().putString("topcompanies", ObjectSerializer.serialize(topCompaniesFromShared)).apply();


        } catch (IOException e) {

            e.printStackTrace();
        }

        addToListTeams();

    }

    public void addToListTeams() {

        listTeams = new ArrayList<>();

        for (int i = 0; i < topRanksFromShared.size(); i++) {

            ModelToplist model = new ModelToplist(topRanksFromShared.get(i), topTeamsFromShared.get(i), topTimesFromShared.get(i), topCompaniesFromShared.get(i));
            listTeams.add(model);

        }

        printTeams(listTeams);

    }

    public void printTeams(final ArrayList<ModelToplist> list) {
        CustomAdapterToplist adapter = new CustomAdapterToplist(this, list);
        listViewResults.setAdapter(adapter);
        listViewResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                ModelToplist teamModel = (ModelToplist) listViewResults.getItemAtPosition(position);

                Intent intent = new Intent(getApplicationContext(), ResultsTeam.class);
                intent.putExtra("team", teamModel.getTeam());
                intent.putExtra("company", teamModel.getCompany());
                intent.putExtra("time", teamModel.getTime());
                startActivity(intent);

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

    public class DownloadResults extends AsyncTask<String, Void, String> {

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
        protected void onPostExecute(String result) {
            processDataToShared(result);
        }
    }
}
