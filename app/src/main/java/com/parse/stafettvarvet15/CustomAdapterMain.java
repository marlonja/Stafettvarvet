package com.parse.stafettvarvet15;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapterMain extends ArrayAdapter{
    ArrayList<String> modelItems = null;
    Context context;

    public CustomAdapterMain(Context context, ArrayList<String> resource) {

        super(context,R.layout.row,resource);
        this.context = context;
        this.modelItems = resource;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        convertView = inflater.inflate(R.layout.row_main, parent, false);
        TextView team = (TextView) convertView.findViewById(R.id.textView1);

        team.setText(modelItems.get(position));

        return convertView;
    }
}