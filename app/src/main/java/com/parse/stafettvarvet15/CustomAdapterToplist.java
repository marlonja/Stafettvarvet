package com.parse.stafettvarvet15;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapterToplist extends ArrayAdapter{
    ArrayList<ModelToplist> modelItems = null;
    Context context;

    public CustomAdapterToplist(Context context, ArrayList<ModelToplist> resource) {

        super(context,R.layout.row,resource);
        this.context = context;
        this.modelItems = resource;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        convertView = inflater.inflate(R.layout.row_toplist, parent, false);
        TextView rank = (TextView) convertView.findViewById(R.id.textView1);
        TextView team = (TextView) convertView.findViewById(R.id.textView2);
        TextView time = (TextView) convertView.findViewById(R.id.textView3);
        TextView company = (TextView) convertView.findViewById(R.id.textView4);

        rank.setText(modelItems.get(position).getRank());
        team.setText(modelItems.get(position).getTeam());
        time.setText(modelItems.get(position).getTime());
        company.setText(modelItems.get(position).getCompany());

        return convertView;
    }
}