package com.parse.stafettvarvet15;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

import static com.parse.stafettvarvet15.InfoIndex.positions;
import static com.parse.stafettvarvet15.InfoIndex.sharedPreferences;
import static com.parse.stafettvarvet15.TeamIndex.allIsActive;
import static com.parse.stafettvarvet15.TeamIndex.favorites;
import static com.parse.stafettvarvet15.TeamIndex.listFavTeamsMen;
import static com.parse.stafettvarvet15.TeamIndex.listFavTeamsWomen;
import static com.parse.stafettvarvet15.TeamIndex.listTeams;
import static com.parse.stafettvarvet15.TeamIndex.listTeamsMen;
import static com.parse.stafettvarvet15.TeamIndex.listTeamsWomen;
import static com.parse.stafettvarvet15.TeamIndex.menIsActive;
import static com.parse.stafettvarvet15.TeamIndex.searchIsActive;
import static com.parse.stafettvarvet15.TeamIndex.searchResults;
import static com.parse.stafettvarvet15.TeamIndex.sliderPos;
import static com.parse.stafettvarvet15.TeamIndex.womenIsActive;

public class CustomAdapterTeam extends ArrayAdapter{
    ArrayList<ModelTeam> modelTeamItems = null;
    Context context;

    public CustomAdapterTeam(Context context, ArrayList<ModelTeam> resource) {

        super(context,R.layout.row,resource);
        this.context = context;
        this.modelTeamItems = resource;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        convertView = inflater.inflate(R.layout.row, parent, false);
        final TextView name = (TextView) convertView.findViewById(R.id.textView1);
        TextView company = (TextView) convertView.findViewById(R.id.textView2);
        final CheckBox cb = (CheckBox) convertView.findViewById(R.id.checkBox1);
        final ListView lw = (ListView) ((Activity) context).findViewById(R.id.listView1);

        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ModelTeam teamModelTeam = (ModelTeam) lw.getItemAtPosition(position);

                for (int i = 0; i < listTeams.size(); i++) {

                    if (teamModelTeam.equals(listTeams.get(i)))
                    {

                        Intent intent = new Intent(getContext(), TeamPage.class);
                        intent.putExtra("position", i);
                        context.startActivity(intent);
                    }
                }

            }
        });

        cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sliderPos == 0){

                    for (int i = 0; i < listTeams.size(); i++) {

                        if (searchIsActive) {

                            if (searchResults.get(position) == listTeams.get(i)) {

                                if(cb.isChecked())positions.set(i, 1);
                                else positions.set(i, 0);

                            }

                        } else {

                            if (menIsActive && listTeamsMen.get(position) == listTeams.get(i)) {

                                if(cb.isChecked())positions.set(i, 1);
                                else positions.set(i, 0);


                            } else if (womenIsActive && listTeamsWomen.get(position) == listTeams.get(i)) {

                                if(cb.isChecked())positions.set(i, 1);
                                else positions.set(i, 0);

                            } else if (allIsActive && position == i) {

                                if(cb.isChecked())positions.set(i, 1);
                                else positions.set(i, 0);

                            }

                        }

                    }

                    try {

                        sharedPreferences.edit().putString("positions", ObjectSerializer.serialize(positions)).apply();

                    } catch (IOException e) {

                        e.printStackTrace();
                    }

                } else {
                    if (sliderPos == 1) {

                        for (int i = 0; i < listTeams.size(); i++) {

                            if (menIsActive && listFavTeamsMen.get(position) == listTeams.get(i)) {

                                positions.set(i,0);

                            } else if (womenIsActive && listFavTeamsWomen.get(position) == listTeams.get(i)) {

                                positions.set(i,0);

                            } else if (allIsActive && favorites.get(position) == listTeams.get(i)) {

                                positions.set(i,0);

                            }
                        }

                    }

                    try {

                        sharedPreferences.edit().putString("positions", ObjectSerializer.serialize(positions)).apply();

                    } catch (IOException e) {

                        e.printStackTrace();
                    }
                }
            }
        });

        name.setText(modelTeamItems.get(position).getName());
        company.setText(modelTeamItems.get(position).getCompany());

        if(modelTeamItems.get(position).getValue() == 1)

            cb.setChecked(true);

        else
            cb.setChecked(false);

        return convertView;
    }
}