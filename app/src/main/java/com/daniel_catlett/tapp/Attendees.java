package com.daniel_catlett.tapp;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Attendees extends AppCompatActivity
{
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendees);
        Typeface kelson = Typeface.createFromAsset(getAssets(), "Kelson-Bold.otf");
        title = (TextView)findViewById(R.id.textViewAttendees);
        title.setTypeface(kelson);
    }
}
