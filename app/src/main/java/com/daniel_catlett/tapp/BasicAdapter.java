package com.daniel_catlett.tapp;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by daniel on 12/2/2017.
 */

public class BasicAdapter extends BaseAdapter
{
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<String> dataSource;

    public BasicAdapter(Context contexter, ArrayList<String> dataSourcer)
    {
        context = contexter;
        dataSource = dataSourcer;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount()
    {
        return dataSource.size();
    }

    @Override
    public Object getItem(int position)
    {
        return dataSource.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View rowView = inflater.inflate(R.layout.list_item_basic, parent, false);
        TextView listItemText = (TextView)rowView.findViewById(R.id.listItemText);
        Typeface kelson = Typeface.createFromAsset(context.getAssets(), "Kelson-Bold.otf");
        listItemText.setTypeface(kelson);
        String data = (String)getItem(position);
        listItemText.setText(data);

        return rowView;
    }
}
