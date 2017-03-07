package com.parse.stafettvarvet15;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

import static android.view.View.VISIBLE;
import static android.view.View.INVISIBLE;
import static com.parse.stafettvarvet15.InfoIndex.companiesFromShared;
import static com.parse.stafettvarvet15.InfoIndex.gendersFromShared;
import static com.parse.stafettvarvet15.InfoIndex.positions;
import static com.parse.stafettvarvet15.InfoIndex.sharedPreferences;
import static com.parse.stafettvarvet15.InfoIndex.teamsFromShared;


public class TeamIndex extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    Button buttonAll;
    Button buttonMen;
    Button buttonWomen;
    ListView lv;
    EditText editTextSearch;
    ImageButton imageButton;
    ImageButton imageButtonCross;
    static ArrayList<ModelTeam> listTeams;
    static ArrayList<ModelTeam> listTeamsMen;
    static ArrayList<ModelTeam> listTeamsWomen;
    static ArrayList<ModelTeam> favorites;
    static ArrayList<ModelTeam> searchResults;
    static int sliderPos;
    static boolean menIsActive;
    static boolean womenIsActive;
    static boolean allIsActive;
    static boolean searchIsActive;
    static ArrayList<ModelTeam> listFavTeamsMen;
    static ArrayList<ModelTeam> listFavTeamsWomen;
    ArrayList<Integer> placeInCompaniesList;
    ArrayList<Integer> placeInTeamsList;
    String search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_index);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        buttonAll = (Button) findViewById(R.id.buttonAll);
        buttonMen = (Button) findViewById(R.id.buttonMen);
        buttonWomen = (Button) findViewById(R.id.buttonWomen);
        lv = (ListView) findViewById(R.id.listView1);
        imageButton = (ImageButton) findViewById(R.id.imageButton);
        imageButtonCross = (ImageButton) findViewById(R.id.imageButtonCross);
        editTextSearch = (EditText) findViewById(R.id.editTextSearch);
        sliderPos = 0;
        menIsActive = false;
        womenIsActive = false;
        allIsActive = true;
        searchIsActive = false;

        bottomNavigationView.getMenu().getItem(0).setChecked(false);
        bottomNavigationView.getMenu().getItem(1).setChecked(false);
        bottomNavigationView.getMenu().getItem(2).setChecked(false);
        bottomNavigationView.getMenu().getItem(3).setChecked(true);
        bottomNavigationView.getMenu().getItem(4).setChecked(false);

        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        setBottomMenuBar();
        printTeams();
        setSlider();
        setFocusChangeOnSearch();

    }

    public void setFocusChangeOnSearch() {

        editTextSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View view, boolean hasFocus) {

                if(hasFocus) {
                    imageButtonCross.setVisibility(VISIBLE);
                    editTextSearch.setHint("");
                    editTextSearch.setCompoundDrawables(null, null, null, null);
                } else {
                    imageButtonCross.setVisibility(INVISIBLE);
                    editTextSearch.setHint("Sök");
                    editTextSearch.setCompoundDrawablesWithIntrinsicBounds(R.drawable.magnify, 0, 0, 0);
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE); imm.hideSoftInputFromWindow(editTextSearch.getWindowToken(), 0);
                }

            }

        });

        editTextSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if  ((actionId == EditorInfo.IME_ACTION_DONE)) {

                    search = editTextSearch.getText().toString();
                    Log.i("info", search);
                    processSearch();
                }
                return false;
            }
        });
    }

    public void processSearch() {

        searchIsActive = true;
        searchResults = new ArrayList<>();
        placeInCompaniesList = new ArrayList<>();
        placeInTeamsList = new ArrayList<>();

        for (int i = 0; i < companiesFromShared.size(); i++) {

            if (search.equalsIgnoreCase(companiesFromShared.get(i))){

                placeInCompaniesList.add(i);

            }
            if (search.equalsIgnoreCase(teamsFromShared.get(i))){

                placeInTeamsList.add(i);

            }

        }

        if (!placeInCompaniesList.isEmpty()) {

            for (int i = 0; i < placeInCompaniesList.size(); i++) {

                Integer temp = placeInCompaniesList.get(i);
                searchResults.add(listTeams.get(temp));
            }


        } else {

                for (int i = 0; i < placeInTeamsList.size(); i++) {

                    Integer temp = placeInTeamsList.get(i);
                    searchResults.add(listTeams.get(temp));
                }

        }

        CustomAdapterTeam adapter = new CustomAdapterTeam(this, searchResults);
        lv.setAdapter(adapter);
    }

    public void deleteContent(View view) {

        editTextSearch.setText("");

    }

    public void setSlider() {
        sliderPos = 0;
        imageButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (sliderPos == 0) {
                    //blå
                    imageButton.setImageResource(R.drawable.slideon);

                    sliderPos = 1;

                    if (menIsActive) {
                        showMen(v);

                    } else if (womenIsActive) {
                        showWomen(v);
                    } else {

                        setListTeams();

                        favorites = new ArrayList<>();

                        for (int i = 0; i < positions.size(); i++) {

                            if (positions.get(i) == 1) {

                                favorites.add(listTeams.get(i));

                            }

                        }
                        CustomAdapterTeam adapter = new CustomAdapterTeam(bottomNavigationView.getContext(), favorites);
                        lv.setAdapter(adapter);
                    }


                } else if (sliderPos == 1) {
                    imageButton.setImageResource(R.drawable.slideoff);
                    sliderPos = 0;

                    if (menIsActive) {
                        showMen(v);

                    } else if (womenIsActive) {
                        showWomen(v);
                    } else {

                        printTeams();
                    }
                }
            }
        });

    }

    public void setListTeams() {

        listTeams = new ArrayList<>();
        String team;

        for (int i = 0; i < teamsFromShared.size(); i++) {

            team = teamsFromShared.get(i);
            ModelTeam modelTeam = new ModelTeam(team, companiesFromShared.get(i), positions.get(i));
            listTeams.add(modelTeam);

        }

    }

    public void printTeams() {
        try {

            positions = (ArrayList<Integer>) ObjectSerializer.deserialize(sharedPreferences.getString("positions", ObjectSerializer.serialize(new ArrayList<String>())));


        } catch (IOException e) {

            e.printStackTrace();

        }

        setListTeams();

        if (listTeams != null) {
            CustomAdapterTeam adapter = new CustomAdapterTeam(this, listTeams);
            lv.setAdapter(adapter);

        }

    }

    public void showAll(View view) {
        allIsActive = true;
        menIsActive = false;
        womenIsActive = false;
        searchIsActive = false;

        buttonAll.setBackgroundColor(Color.parseColor("#e10832"));
        buttonAll.setTextColor(Color.parseColor("#ffffff"));
        buttonMen.setBackgroundResource(R.drawable.button_border);
        buttonMen.setTextColor(Color.parseColor("#e10832"));
        buttonWomen.setBackgroundResource(R.drawable.button_border);
        buttonWomen.setTextColor(Color.parseColor("#e10832"));

        if (sliderPos == 0) {

            printTeams();

        } else {

            favorites = new ArrayList<>();

            for (int i = 0; i < positions.size(); i++) {

                if (positions.get(i) == 1) {

                    favorites.add(listTeams.get(i));

                }

            }

            CustomAdapterTeam adapter = new CustomAdapterTeam(this, favorites);
            lv.setAdapter(adapter);
        }

    }

    public void showMen(View view) {
        allIsActive = false;
        menIsActive = true;
        womenIsActive = false;
        searchIsActive = false;

        buttonAll.setBackgroundResource(R.drawable.button_border);
        buttonAll.setTextColor(Color.parseColor("#e10832"));
        buttonMen.setBackgroundColor(Color.parseColor("#e10832"));
        buttonMen.setTextColor(Color.parseColor("#ffffff"));
        buttonWomen.setBackgroundResource(R.drawable.button_border);
        buttonWomen.setTextColor(Color.parseColor("#e10832"));

        setListTeams();

        if (sliderPos == 0) {

            listTeamsMen = new ArrayList<>();
            for (int i = 0; i < gendersFromShared.size(); i++) {

                if (gendersFromShared.get(i).equals("M")) {

                    listTeamsMen.add(listTeams.get(i));

                }

            }

            CustomAdapterTeam adapter = new CustomAdapterTeam(this, listTeamsMen);
            lv.setAdapter(adapter);

        } else {

            listFavTeamsMen = new ArrayList<>();

            for (int i = 0; i < positions.size(); i++) {

                if (positions.get(i) == 1 && gendersFromShared.get(i).equals("M")) {

                    listFavTeamsMen.add(listTeams.get(i));
                }

            }

            CustomAdapterTeam adapter = new CustomAdapterTeam(this, listFavTeamsMen);
            lv.setAdapter(adapter);

        }

    }

    public void showWomen(View view) {

        allIsActive = false;
        menIsActive = false;
        womenIsActive = true;
        searchIsActive = false;

        buttonAll.setBackgroundResource(R.drawable.button_border);
        buttonAll.setTextColor(Color.parseColor("#e10832"));
        buttonMen.setBackgroundResource(R.drawable.button_border);
        buttonMen.setTextColor(Color.parseColor("#e10832"));
        buttonWomen.setBackgroundColor(Color.parseColor("#e10832"));
        buttonWomen.setTextColor(Color.parseColor("#ffffff"));

        setListTeams();

        if (sliderPos == 0) {

            listTeamsWomen = new ArrayList<>();
            for (int i = 0; i < gendersFromShared.size(); i++) {

                if (gendersFromShared.get(i).equals("W")) {

                    listTeamsWomen.add(listTeams.get(i));

                }

            }

            CustomAdapterTeam adapter = new CustomAdapterTeam(this, listTeamsWomen);
            lv.setAdapter(adapter);

        } else {

            listFavTeamsWomen = new ArrayList<>();

            for (int i = 0; i < positions.size(); i++) {

                if (positions.get(i) == 1 && gendersFromShared.get(i).equals("W")) {

                    listFavTeamsWomen.add(listTeams.get(i));
                }

            }

            CustomAdapterTeam adapter = new CustomAdapterTeam(this, listFavTeamsWomen);
            lv.setAdapter(adapter);

        }

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
